package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "上传文件")
@Controller
@RequestMapping("/file")
public class FileUploadController {
    Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("文件上传")
    public BaseResponseWrapper<List<String>> fileUpload(MultipartHttpServletRequest request) {
        try {
            List<MultipartFile> files = request.getFiles("file");
            List<String> filePathList = new ArrayList<String>();
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String filePath = FileUtils.saveFiles(file);
                filePathList.add(filePath);
            }

            return BaseResponseWrapper.success(filePathList);
        } catch (Exception e) {
            logger.error("文件上传失败，失败原因:{}", e);
            return BaseResponseWrapper.fail(null, "文件上传失败");
        }
    }

}
