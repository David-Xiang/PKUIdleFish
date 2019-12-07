package cn.edu.pku.course.database.idlefish.entity;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Product {
    public int productID;
    public int category;
    public String title;
    public String imgsrc;
    public Double price;
    public int sellerID;
    public String description;
    public Date sellTime;
    public int status;
    public List<Comment> comments;
}
