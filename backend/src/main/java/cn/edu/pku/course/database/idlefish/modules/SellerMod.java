package cn.edu.pku.course.database.idlefish.modules;

import cn.edu.pku.course.database.idlefish.response.ActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import cn.edu.pku.course.database.idlefish.response.ActionResponse;

=======
>>>>>>> 1ed4b2b1f7b53af852054669ea975fcdcb467b4d
@Repository
public class SellerMod {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * put on a new product or modify product information <br>
	 * when product_id = 0, it means to put on, otherwise to modify <br>
	 * can modify only when product_status == 'draft' or 'returned' <br>
	 */
	public ActionResponse PutOnOrModify(int product_id, String seller_name, String title, String category, String price,
			String description, String imgsrc) {
		String sql;
		if (product_id == 0) {
			sql = "INSERT INTO product(title, seller_name, category, price, description, imgsrc) VALUES (?, ?, ?, ?, ?, ?, ?)";
			return new ActionResponse(
					jdbcTemplate.update(sql, title, seller_name, category, price, description, imgsrc) > 0);
		} else {
			sql = "UPDATE product SET title = ?, category = ?, price = ?, description = ?, imgsrc = ? WHERE product_id = ? AND product_status LIKE '%r%'";
			return new ActionResponse(
					jdbcTemplate.update(sql, title, category, price, description, imgsrc, product_id) > 0);
		}
	}

}
