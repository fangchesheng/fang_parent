package com.fang.eduservice.controller;


import com.fang.commonutils.R;
import com.fang.eduservice.entity.EduTeacher;
import com.fang.eduservice.entity.vo.EduTeacherQuery;
import com.fang.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-23
 */
@Api(tags = "讲师控制器")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin//解决跨域
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;
//    http://localhost:8001/eduservice/teacher/findAll
    // 查询所有数据
    //    rest 风格
    @GetMapping("findAll")
    @ApiOperation("查询所有讲师")
    public R findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("{id}") // ID需要通过路径瑾萱传递
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
//    讲师分页查询
    @GetMapping("eduTeacherPage/{current}/{limit}") // 该写法表示路径传值
    public R pageTeacher(@PathVariable long current, @PathVariable long limit){
        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);
        teacherService.page(eduTeacherPage,null);

        long total = eduTeacherPage.getTotal();//总记录数
        List<EduTeacher> records = eduTeacherPage.getRecords();//数据list集合


        return R.ok().data("total",total).data("rows",records);
    }

//    讲师分页组合查询
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) EduTeacherQuery teacherQuery){
        Page<EduTeacher> page = new Page<>(current,limit);

//        穿件查询条件包装类
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        String career = teacherQuery.getCareer();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(career)){
            wrapper.like("career",career);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        // 排序
        wrapper.orderByDesc("gmt_create");
        teacherService.page(page,wrapper);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

//    根据讲师ID进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    
//    讲师修改逻辑
    @PostMapping("updateteacher")
    public R updateteacher(@RequestBody EduTeacher eduTeacher ){
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else{
            return R.error();
        }

    }
}

