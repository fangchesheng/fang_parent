package com.fang.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    private String id;
    private String title;


//   小节数据注入
    private List<VideoVo> children = new ArrayList<>();
}
