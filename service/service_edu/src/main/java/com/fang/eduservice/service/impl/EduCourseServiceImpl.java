package com.fang.eduservice.service.impl;

import com.fang.commonutils.BusinessException;
import com.fang.eduservice.entity.EduCourse;
import com.fang.eduservice.entity.EduCourseDescription;
import com.fang.eduservice.entity.vo.CourseInfoVo;
import com.fang.eduservice.entity.vo.CoursePublishVo;
import com.fang.eduservice.mapper.EduCourseMapper;
import com.fang.eduservice.service.EduCourseDescriptionService;
import com.fang.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
@Service
@Slf4j
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;

//    添加课程信息
    @Override
    @Transactional(rollbackFor = Exception.class) //插入时添加事务,防止查询失败有冗余数据
    public String saveCourseInfo(CourseInfoVo courseInfoVo) throws BusinessException {
        try {
//        添加基本信息
            EduCourse eduCourse = new EduCourse();
            eduCourse.setTeacherId("0");
            eduCourse.setSubjectId("0");
            eduCourse.setSubjectParentId("0");
            eduCourse.setCover("0");
            eduCourse.setIsDeleted(0);
            BeanUtils.copyProperties(courseInfoVo,eduCourse);
            baseMapper.insert(eduCourse);

//        描述表数据组装
            EduCourseDescription description = new EduCourseDescription();
            BeanUtils.copyProperties(courseInfoVo,description);
            description.setId(eduCourse.getId());
            descriptionService.save(description);
            log.info("插入数据正常 插入对象--> {}",courseInfoVo.toString());
            return eduCourse.getId();
        }catch (Exception e){
            log.info("插入数据错误 插入对象--> {}",courseInfoVo.toString());
             throw new BusinessException("插入对象异常");//只有在抛出异常的时候才会进行事务回滚 -- 小细节
        }
    }

    //        根据课程Id查询课程基本信息 也可在mapper中直接SQL解决
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        try {
            CourseInfoVo courseInfoVo = new CourseInfoVo();
            log.info("开始查询,传入的courseId -->{}" +courseId);
            // 查询课程表
            EduCourse eduCourse = baseMapper.selectById(courseId);
            if (eduCourse != null){
                BeanUtils.copyProperties(eduCourse,courseInfoVo);
            }

//        查询描述
            EduCourseDescription courseDescription = descriptionService.getById(courseId);
            if (courseDescription != null && StringUtils.isNotBlank(courseDescription.getDescription())){
                courseInfoVo.setDescription(courseDescription.getDescription());
            }
            return courseInfoVo;
        }catch (Exception e){
            log.info("查询失败,传入的courseId -->{}" +courseId);
            e.printStackTrace();

        }
        return null;

    }

//    跟新课程信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCourseInfo(CourseInfoVo courseInfoVo) throws Exception {
        try {
            log.info("修改课程信息表开始,传入对象-->{}",courseInfoVo.toString());
 //        修改课程表
            EduCourse eduCourse = new EduCourse();
            BeanUtils.copyProperties(courseInfoVo,eduCourse);
            baseMapper.updateById(eduCourse);

//       修改描述表
            EduCourseDescription description = new EduCourseDescription();
            BeanUtils.copyProperties(courseInfoVo,description);
            descriptionService.updateById(description);
        }catch (Exception e){
            log.info("修改课程信息表失败,传入对象-->{}",courseInfoVo.toString());
            throw new Exception();//只有在抛出异常的时候才会进行事务回滚 -- 小细节
        }
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }
}
