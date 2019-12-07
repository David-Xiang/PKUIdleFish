package cn.edu.pku.course.database.idlefish.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Product {
    public int productID;
    public ProductInfo productInfo;
    public List<Comment> comments;
}
