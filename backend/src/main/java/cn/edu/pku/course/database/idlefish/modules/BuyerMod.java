package cn.edu.pku.course.database.idlefish.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.basic.ProductBasic;
import cn.edu.pku.course.database.idlefish.response.ActionResponse;

@Repository
public class BuyerMod {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ProductBasic productBasic;

	/**
	 * add a bargain according to buyer_name and product_id <br>
	 * can only do this when product_status <> 'deleted' <br>
	 */
	public ActionResponse addBargain(String buyer_name, int product_id) {
		String sql = "INSERT INTO bargain(buyer_name, product_id) VALUES (?, ?)";
		try {
			return new ActionResponse(!productBasic.changeStatus(product_id, "deleted", "deleted").success
					&& jdbcTemplate.update(sql, buyer_name, product_id) > 0);
		} catch (DuplicateKeyException e) {
			return new ActionResponse(false);
		}
	}

	/**
	 * remove a bargain according to buyer_name and product_id <br>
	 */
	public ActionResponse removeBargain(String buyer_name, int product_id) {
		String sql = "DELETE FROM bargain where buyer_name = ? AND product_id = ?";
		return new ActionResponse(jdbcTemplate.update(sql, buyer_name, product_id) > 0);
	}

	/**
	 * buy a product according to buyer_name and product_id <br>
	 * can only do this when product_status == 'sale' <br>
	 */
	public ActionResponse buyProduct(String buyer_name, int product_id) {
		String sql = "UPDATE bargain SET bargain_status = 'done' WHERE buyer_name = ? AND product_id = ?";
		return new ActionResponse((productBasic.changeStatus(product_id, "sale", "sale").success
				&& jdbcTemplate.update(sql, buyer_name, product_id) > 0));
	}

	/**
	 * leave a comment according to buyer_name, product_id and content <br>
	 * can only do this when product_status <> 'deleted' <br>
	 */
	public ActionResponse leaveComment(String buyer_name, int product_id, String content) {
		String sql = "INSERT INTO comment(buyer_name, product_id, content) VALUES (?, ?, ?)";
		return new ActionResponse(!productBasic.changeStatus(product_id, "deleted", "deleted").success
				&& jdbcTemplate.update(sql, buyer_name, product_id, content) > 0);
	}

}
