/*
 * Copyright (c) 2005, 2022, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.huang.controller;

import com.huang.utils.JsonResult;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/file")
public class UploadController {

    @PostMapping("/upload")
    public JsonResult upload(MultipartFile file, HttpServletRequest req) {
        String filePath = null;
        try {
            String path = (new ClassPathResource("/static/img/")).getFile().getAbsolutePath();
            String oldName = file.getOriginalFilename();
            String newName = UUID.randomUUID() + oldName.substring(oldName.lastIndexOf("."));
            try {
                file.transferTo(new File(path, newName));
                filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                        req.getServerPort() + "/img/" + newName;
            } catch (IOException e) {
                e.printStackTrace();
                return JsonResult.error("上传失败！");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.error("上传失败！");
        }
        return JsonResult.success(filePath,"上传成功！");
    }
}
