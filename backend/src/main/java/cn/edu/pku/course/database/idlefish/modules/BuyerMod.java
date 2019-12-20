package cn.edu.pku.course.database.idlefish.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.response.ActionResponse;

@Repository
public class BuyerMod {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * add a bargain according to buyer_name and product_id <br>
	 */
	public ActionResponse addBargain(String buyername, String product_id) {
		String sql;
		sql = "INSERT INTO bargain(buyername, product_id) VALUES (?, ?)";
		return new ActionResponse(jdbcTemplate.update(sql, buyername, product_id) > 0);
	}

	/**
	 * delete a bargain according to buyer_name and product_id <br>
	 */
	public ActionResponse deleteBargain(String buyer_name, String product_id) {
		String sql;
		sql = "DELETE FROM bargain where buyername = ? AND product_id = ?";
		return new ActionResponse(jdbcTemplate.update(sql, buyer_name, product_id) > 0);
	}

	/**
	 * buy a product according to buyer_name and product_id <br>
	 */
	public ActionResponse buyProduct(String buyer_name, String product_id) {
		String sql;
		sql = "UPDATE bargain SET bargain_status = 'done', update_time = NOW() WHERE buyername = ? AND product_id = ?";
		return new ActionResponse(jdbcTemplate.update(sql, buyer_name, product_id) > 0);
	}

	/**
	 * leave a comment according to buyer_name, product_id and content
	 */
	public ActionResponse leaveComment(String buyer_name, String product_id, String content) {
		String sql = "INSERT INTO comment(buyer_name, product_id, content) VALUES (?, ?, ?)";
		return new ActionResponse(jdbcTemplate.update(sql, buyer_name, product_id, content) > 0);
	}

}
