package cn.edu.pku.course.database.idlefish.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProductInfo {
    public int category;
    public String title;
    public String imgsrc;
    public Double price;
    public int sellerID;
    public String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date sellTime;
    public int status;
}
