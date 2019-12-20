package cn.edu.pku.course.database.idlefish.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Comment {
	public String buyer_name;
	public int product_id;
	public String content;
	public String time;
}
