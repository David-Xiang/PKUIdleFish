package cn.edu.pku.course.database.idlefish.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductInfo {
	public int category;
	public String title;
	public String imgsrc;
	public Double price;
	public String seller_name;
	public String description;
	public String product_status;
	public String update_time;
	public int hot;
	public String category_name;
}
