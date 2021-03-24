package com.fang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fang.commonutils.BusinessException;
import com.fang.commonutils.ResultCode;
import com.fang.eduservice.entity.EduChapter;
import com.fang.eduservice.entity.EduVideo;
import com.fang.eduservice.entity.chapter.ChapterVo;
import com.fang.eduservice.entity.chapter.VideoVo;
import com.fang.eduservice.mapper.EduChapterMapper;
import com.fang.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.eduservice.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {
//        根据课程ID查询课程大纲列表
//        1 查询章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(eduChapterList)){
            log.info("根据courseId -->{},查询的章节数据为空,eduChapterList -- >{}",courseId,eduChapterList.toString());
            return new ArrayList<ChapterVo>();
        }
//        2 查询小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> videoist = videoService.list(wrapperVideo);


//        3数据组装
        List<ChapterVo> result = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
//            章节数据录入
            ChapterVo chapterVo = new ChapterVo();
            EduChapter eduChapter = eduChapterList.get(i);
            //注意拷贝的字段顺序 已踩坑
            BeanUtils.copyProperties(eduChapter,chapterVo);
            result.add(chapterVo);
//          小节数据录入 注意属性判空--应为数据库数据不可靠
//            String courseId1 = eduChapter.getCourseId();
            List<VideoVo> children = new ArrayList<>();

            if (StringUtils.isNotBlank(chapterVo.getId())){
                for (int j = 0; j < videoist.size(); j++) {
                    EduVideo eduVideo = videoist.get(j);
                    String chapterId = eduVideo.getChapterId();
                    if (chapterVo.getId().equals(chapterId)){
                        VideoVo videoVo = new VideoVo();
                        BeanUtils.copyProperties(eduVideo,videoVo);
                        children.add(videoVo);
                    }
                }
                chapterVo.setChildren(children);
            }

        }

        log.info("章节数据返回 -->{}",result.toString());
//        4放回数据
        return result;
    }

//    删除章节 如果有小节的时候不让删,如果没有小节就删除
    @Override
    public boolean deleteChapter(String chapterId) throws BusinessException {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count > 0){//表示有小节,不删
            throw new BusinessException("不能进行删除", ResultCode.ERROR);
        }else{//表示无小节,删除
            int chapterinfo = baseMapper.deleteById(chapterId);
            return chapterinfo>0;
        }
    }
}
