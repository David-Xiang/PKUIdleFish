package cn.edu.pku.course.database.idlefish.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@AllArgsConstructor
@Data
public class Comment {
    public int productID;
    @JsonFormat(pattern="yy.MM.dd")
    public Date time;
    public int buyerID;
    public String buyerName;
    public String content;
}
