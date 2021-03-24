package com.fang.eduservice.controller;


import com.fang.commonutils.R;
import com.fang.eduservice.entity.subject.OneSubject;
import com.fang.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
@Api(tags = "课程前端控制器")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

//    课程分类列表（树结构）
    @GetMapping("getAllSubject")
    public R getAllSubject(){
//        返回树结构数据
        List<OneSubject> list =  subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

