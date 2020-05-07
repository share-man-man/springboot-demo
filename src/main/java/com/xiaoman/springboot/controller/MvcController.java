package com.xiaoman.springboot.controller;

import com.xiaoman.springboot.bean.LoginBean;
import com.xiaoman.springboot.config.GrainFullProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @description: mvc返回thymeleaf
 * @author: shuxiaoman
 * @time: 2020/5/6 5:07 下午
 */
@Controller
@RequestMapping("/mvc")
public class MvcController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    GrainFullProperties grainFullProperties;

    /**
     * @description: 首页
     * @author: shuxiaoman
     * @time: 2020/5/6 5:30 下午
     */
    @RequestMapping("")
    public String index(Model model) {
        System.out.println();
        model.addAttribute("info", "thymeleafdemo!!!");
        return "index";
    }

    /**
     * @description: 登录界面
     * @author: shuxiaoman
     * @time: 2020/5/7 11:32 上午
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * @description: //TODO 登录
     * @author: shuxiaoman
     * @time: 2020/5/7 4:52 下午
     */
    @RequestMapping("/userLogin")
    @ResponseBody
    public LoginBean userLogin() {
        String token = UUID.randomUUID().toString();
        LoginBean loginBean = new LoginBean();
        loginBean.setToken(token);
        loginBean.setExpires(grainFullProperties.getExpires());
        redisTemplate.opsForSet().add("tokenMap", token);
        return loginBean;
    }


    /**
     * @description: 上传
     * @author: shuxiaoman
     * @time: 2020/5/6 6:05 下午
     */
    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/mvc/uploadStatus";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get("/Users/shuxiaoman/Desktop/" + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/mvc/uploadStatus";
    }

    /**
     * @description: 上传界面
     * @author: shuxiaoman
     * @time: 2020/5/6 6:05 下午
     */
    @RequestMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    /**
     * @description: 下载
     * @author: shuxiaoman
     * @time: 2020/5/6 6:05 下午
     */
    @RequestMapping("/download")
    public String download(HttpServletResponse response) throws UnsupportedEncodingException {
        //设置文件路径
        String realPath = "/Users/shuxiaoman/Desktop/运维服务平台/index.html";
        File file = new File(realPath);

        // 如果文件名存在，则进行下载
        if (file.exists()) {

            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));

            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download the song successfully!");
            } catch (Exception e) {
                System.out.println("Download the song failed!");
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }


}
