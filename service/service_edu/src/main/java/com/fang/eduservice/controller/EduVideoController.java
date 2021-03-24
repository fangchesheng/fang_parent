package com.fang.eduservice.controller;


import com.fang.commonutils.BusinessException;
import com.fang.commonutils.R;
import com.fang.eduservice.entity.EduVideo;
import com.fang.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
@Api(tags = "课程小节(视频控制器)")
@RestController
@RequestMapping("/eduservice/eduVideo")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
//    也可使用@Slf4注解
    private static final Log log = LogFactory.getLog(EduVideoController.class);

//    添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) throws BusinessException {
        try {
            eduVideoService.save(eduVideo);
            return R.ok();
        }catch (Exception e){
            throw new BusinessException("添加小节成功");
        }
    }


//    删除小节Get
    @GetMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id) throws BusinessException {
        try {
            eduVideoService.removeById(id);
            return R.ok();
        }catch (Exception e){
            throw new BusinessException("删除小节失败");
        }
    }


//    修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) throws BusinessException {
        try {
            eduVideoService.updateById(eduVideo);
            return R.ok();
        }catch (Exception e){
            throw new BusinessException("修改小节失败");
        }
    }

    // 查询小节
    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id) throws BusinessException {
        try {
            EduVideo byId = eduVideoService.getById(id);
            if (byId != null){
                return R.ok().data("video",byId);
            }else{
                return R.error();
            }
        }catch (Exception e){
            throw new BusinessException("获取小节失败");
        }
    }

}

