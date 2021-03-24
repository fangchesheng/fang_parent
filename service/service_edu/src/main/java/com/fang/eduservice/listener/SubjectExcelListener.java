package com.fang.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fang.eduservice.entity.EduSubject;
import com.fang.eduservice.entity.excel.SubjectData;
import com.fang.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

//  因为SubjectExcelListener 不能交给spring进行管理，需要自己new ，不能注入其他对象
//    不能实现数据库操作
    public EduSubjectService eduSubjectService;
//  添加空参构造
    public SubjectExcelListener() {}
// 创建list集合封装最终的数据
    List<SubjectData> list = new ArrayList<>();
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }
// 读取Excel内容，一行一行读取
    @SneakyThrows
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext context) {
        if (subjectData == null){
            throw new Exception("文件数据为空");
        }

//      一行一行读取，第一个值是一级分类，第二个值是二级分类
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }

        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(),pid);
        if (existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }

    }
// 判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = eduSubjectService.getOne(wrapper);
        return one;
    }

//    判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String parentId){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",parentId);
        EduSubject two = eduSubjectService.getOne(wrapper);
        return two;
    }

//  读取完后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
