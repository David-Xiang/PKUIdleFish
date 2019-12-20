package cn.edu.pku.course.database.idlefish.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.edu.pku.course.database.idlefish.response.ActionResponse;

public class SellerMod {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * put on a product or modify product information <br>
	 * when product_id = 0, it means to put on, otherwise to modify <br>
	 */
	public ActionResponse PutOnOrModify(String product_id, String seller_name, String title, String category,
			String price, String description, String imgsrc) {
		String sql;
		if (product_id.contentEquals("0")) {
			sql = "INSERT INTO product(title, seller_name, category, price, description, imgsrc) VALUES (?, ?, ?, ?, ?, ?, ?)";
			return new ActionResponse(
					jdbcTemplate.update(sql, title, seller_name, category, price, description, imgsrc) > 0);
		} else {
			sql = "UPDATE product SET title = ?, category = ?, price = ?, description = ?, imgsrc = ? WHERE product_id = ?";
			return new ActionResponse(
					jdbcTemplate.update(sql, title, category, price, description, imgsrc, product_id) > 0);
		}
	}

}
