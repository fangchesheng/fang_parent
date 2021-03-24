package com.fang.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fang.commonutils.R;
import com.fang.eduservice.common.Contants;
import com.fang.eduservice.entity.EduCourse;
import com.fang.eduservice.entity.vo.CourseInfoVo;
import com.fang.eduservice.entity.vo.CoursePublishVo;
import com.fang.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
@Slf4j
@Api(tags = "课程控制前")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
//    添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) throws Exception {
        log.info("添加课程基本信息开始 -->{}",courseInfoVo.toString());
//        插入数据，返回插入成功的课程ID
        String id = courseService.saveCourseInfo(courseInfoVo);
        log.info("添加课程基本信息结束 -->{}",courseInfoVo.toString());
        return R.ok().data("courseId",id);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return  R.ok().data("courseInfoVo",courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) throws Exception {
        courseService.updateCourseInfo(courseInfoVo);

        return R.ok();
    }

//    根据课程ID查询课程确认信息
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo vo = courseService.getPublishCourseInfo(courseId);
        if (vo != null){
            return R.ok().data("coursePublishVo",vo);
        }else{
            return R.ok().data("coursePublishVo",new CoursePublishVo());
        }
    }

//    课程发布 -- 修改数据表中的status
    @PostMapping("publicCourse/{id}")
    public R publicCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus(Contants.NORMAL);//设置课程发布状态
        boolean b = courseService.updateById(eduCourse);
        if (b){//跟新成功
            return R.ok();
        }else{//跟新失败
            return R.error();
        }
    }

    @GetMapping("getCoursePage/{current}/{limit}")
    public R getCourseList(@PathVariable long current ,@PathVariable long limit){
        Page<EduCourse> page = new Page<EduCourse>(current,limit);
        courseService.page(page, null);

        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.ok().data("list",records).data("total",total);
    }
}

