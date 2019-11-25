package cn.edu.pku.course.database.idlefish.entity;

import lombok.Data;

@Data
public class GoodBasicInfo {
    public String name;
    public String describe;
    public String label;
    public Double price;
    public int sellerID;
}
