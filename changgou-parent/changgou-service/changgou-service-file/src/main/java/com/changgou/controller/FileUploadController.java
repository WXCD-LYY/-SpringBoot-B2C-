package com.changgou.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Company: CUG
 * @Description: TODO
 * @Author: LiYangyong
 * @Date: 2021/2/17/017 15:04
 **/
@RestController
@RequestMapping(value = "/upload")
@CrossOrigin
public class FileUploadController {

    /**
     * 文件上传
     */

    @PostMapping
    public Result upload(@RequestParam(value = "file")MultipartFile file) throws Exception{
        // 封装文件信息
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(), // 文件名字 1.jpg
                file.getBytes(), // 文件字节数组
                StringUtils.getFilenameExtension(file.getOriginalFilename()) // 获取文件扩展名
        );

        // 调用FastDFS工具类将文件传入FastDFS中
        String[] uploads = FastDFSUtil.upload(fastDFSFile);

        // 拼接访问地址 url = http://192.168.211.132:8080/wKjThF0DBzaAP23MAAXz2mMp9oM26.jpeg
        String url = FastDFSUtil.getTrackerInfo() + "/" + uploads[0] + "/" + uploads[1];

        return new Result(true, StatusCode.OK, "上传成功",url);

    }
}
