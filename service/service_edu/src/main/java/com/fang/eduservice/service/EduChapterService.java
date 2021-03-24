package com.fang.eduservice.service;

import com.fang.commonutils.BusinessException;
import com.fang.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据ID查询课程大纲
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideo(String courseId);

    /**
     * 根据章节Id删除数据
     * @param chapterId
     * @return
     * @throws BusinessException
     */
    boolean deleteChapter(String chapterId) throws BusinessException;
}
