package cn.edu.pku.course.database.idlefish.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
	public int transaction_id;
	public String buyer_name;
	public String seller_name;
	public int product_id;
	public double price;
	public String time;
}