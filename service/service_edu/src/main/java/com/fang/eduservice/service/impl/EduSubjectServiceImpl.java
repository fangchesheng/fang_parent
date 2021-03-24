package com.fang.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.fang.eduservice.entity.EduSubject;
import com.fang.eduservice.entity.excel.SubjectData;
import com.fang.eduservice.entity.subject.OneSubject;
import com.fang.eduservice.entity.subject.TwoSubject;
import com.fang.eduservice.listener.SubjectExcelListener;
import com.fang.eduservice.mapper.EduSubjectMapper;
import com.fang.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        // 添加课程分类
        try {
//            文件输入流
            InputStream in = file.getInputStream();
//            调用方法进行读取
            EasyExcel.read(in,SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<OneSubject> getAllOneTwoSubject() {
//        树形结构数据组装 eq等于 ne不等于
        ArrayList<OneSubject> result = new ArrayList<>();
//        1级分类数据
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
//        2级分类数据
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        log.info("查询数据 oneSubjectList -> {}, twoSubjectList -> {} ",oneSubjectList,twoSubjectList);

//        判空操作
        if (CollectionUtils.isEmpty(oneSubjectList) || CollectionUtils.isEmpty(twoSubjectList)){
            return result;
        }

//        封装1，2级数据
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            result.add(oneSubject);
//          组装2级数据
            List<TwoSubject> twoResult = new ArrayList<>();
            for (int i1 = 0; i1 < twoSubjectList.size(); i1++) {
                EduSubject eduSubject1 = twoSubjectList.get(i1);
                if (eduSubject.getId().equals(eduSubject1.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject1,twoSubject);
                    twoResult.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoResult);
        }
        return result;
    }
}
