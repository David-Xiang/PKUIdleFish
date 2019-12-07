package cn.edu.pku.course.database.idlefish.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class ProductInfo {
    public int category;
    public String title;
    public String imgsrc;
    public Double price;
    public int sellerID;
    public String description;
    public Date sellTime;
    public int status;
}
