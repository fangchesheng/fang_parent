package com.fang.oss.controller;

import com.fang.commonutils.R;
import com.fang.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@Api(tags = "对象存储控制器")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;
    //    上传头像的方法
    @PostMapping
    @CrossOrigin // 不加该注释的话文件上传不会成功，因为跨域了
    public R uploadOssFile(MultipartFile file){
//        获取上传文件 MultipartFile
//        返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url );
    }
}
