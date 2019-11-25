package cn.edu.pku.course.database.idlefish.entity;

import lombok.Data;

import java.util.List;

@Data
public class GoodInfo {
    public GoodBasicInfo basicInfo;
    public List<Comment> comments;
}
