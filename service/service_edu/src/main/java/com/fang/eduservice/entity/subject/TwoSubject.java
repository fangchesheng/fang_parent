package com.fang.eduservice.entity.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

// 二级分类
@Data
public class TwoSubject {
    @ApiModelProperty("主键ID")
    private String id;
    @ApiModelProperty("课程名称")
    private String title;
}
