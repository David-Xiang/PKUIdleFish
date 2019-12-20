package cn.edu.pku.course.database.idlefish.entity;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {
	public int product_id;
	public ProductInfo productInfo;
	public List<Map<String, Object>> comments;
}
