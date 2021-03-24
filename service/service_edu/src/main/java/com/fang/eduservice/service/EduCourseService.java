package com.fang.eduservice.service;

import com.fang.commonutils.BusinessException;
import com.fang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.eduservice.entity.vo.CourseInfoVo;
import com.fang.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo) throws Exception;

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo) throws Exception;

    CoursePublishVo getPublishCourseInfo(String courseId);

    void deleteCourseInfo(String id) throws BusinessException;
}
