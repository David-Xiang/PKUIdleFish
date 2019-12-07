package cn.edu.pku.course.database.idlefish.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Comment {
    public int productID;
    public Date time;
    public int buyerID;
    public String buyerName;
    public String content;
}
