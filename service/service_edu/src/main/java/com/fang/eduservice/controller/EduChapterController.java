package com.fang.eduservice.controller;


import com.fang.commonutils.BusinessException;
import com.fang.commonutils.R;
import com.fang.eduservice.entity.EduChapter;
import com.fang.eduservice.entity.chapter.ChapterVo;
import com.fang.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@Api(tags = "章节控制器")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    private static final Log log = LogFactory.getLog(EduChapterController.class);

//    根据ID查询课程大纲
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideo(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    // 添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

//    根据章节ID查询章节
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    // 修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }
//    删除方法
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) throws BusinessException {
       boolean flag =  chapterService.deleteChapter(chapterId);
       if (flag){
           return R.ok();
       }else{
           return R.error();
       }
    }
}

