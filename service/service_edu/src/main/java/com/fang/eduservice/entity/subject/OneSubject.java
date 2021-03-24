package com.fang.eduservice.entity.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类
@Data
public class OneSubject {
    @ApiModelProperty("主键ID")
    private String id;
    @ApiModelProperty("课程名称")
    private String title;

//    一个一级分类中有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
