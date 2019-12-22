package cn.edu.pku.course.database.idlefish.basic;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.mapper.ProductResponseRowMapper;
import cn.edu.pku.course.database.idlefish.response.ActionResponse;
import cn.edu.pku.course.database.idlefish.response.ProductResponse;

@Repository
public class ProductBasic {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ProductResponseRowMapper productResponseRowMapper;

	/**
	 * fetch product information according to category, titleKeyword, buyer_name,
	 * seller_name, product_status, bargain_status <br>
	 * when all these arguments equal to '%', it means to fetch all products <br>
	 * additionally, fetch only those within 5 years when timeLimit == true <br>
	 * result will be ordered using *order* and divided by page using pageNum and
	 * itemPerPage if pageNum > 0 <br>
	 */
	public ProductResponse fetch(String category, String titleKeyword, String buyer_name, String seller_name,
			String product_status, String bargain_status, boolean timeLimit, String order, int pageNum,
			int itemPerPage) {
		String selectFrom = "SELECT product.*, category_name FROM product INNER JOIN category USING (category) LEFT JOIN bargain USING (product_id)";
		String where = "WHERE category LIKE ? AND title LIKE ? AND IFNULL(buyer_name, '') LIKE ? AND seller_name LIKE ? AND product_status LIKE ? AND IFNULL(bargain_status, '') LIKE ?";
		if (timeLimit) {
			where += " AND TIMESTAMPDIFF(YEAR, product.update_time, CURDATE()) < 5";
		}
		String group = buyer_name.contentEquals("%") ? "GROUP BY product_id" : "";
		String limit = pageNum > 0 ? "LIMIT " + (pageNum - 1) * itemPerPage + ", " + itemPerPage : "";
		String sql = selectFrom + " " + where + " " + group + " " + order + " " + limit;
		try {
			return jdbcTemplate.queryForObject(sql, productResponseRowMapper, category, titleKeyword, buyer_name,
					seller_name, product_status, bargain_status);
		} catch (EmptyResultDataAccessException e) {
			return new ProductResponse(Collections.emptyList());
		}
	}

	/**
	 * change product_status according to product_id and new_status <br>
	 * product_status must be like old_status <br>
	 * when old_status == '%', it doesn't matter <br>
	 */
	public ActionResponse changeStatus(int product_id, String old_status, String new_status) {
		String sql = "UPDATE product SET product_status = ? WHERE product_id = ? AND product_status LIKE ?";
		return new ActionResponse(jdbcTemplate.update(sql, new_status, product_id, old_status) > 0);
	}

}
