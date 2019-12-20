package cn.edu.pku.course.database.idlefish.basic;

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
		String sql;
		String selectFrom = "SELECT product.*, category.category_name FROM product INNER JOIN category USING (category) LEFT JOIN bargain USING (product_id)";
		String where = "WHERE category LIKE ? AND title LIKE ? AND buyer_name LIKE ? AND seller_name LIKE ? AND product_status LIKE ? AND bargain_status LIKE ?";
		if (timeLimit) {
			where += " AND DATEDIFF(NOW(), product.update_time) < 1800";
		}
		String group = "GROUP BY product_id";
		String limit = (pageNum > 0) ? "LIMIT " + (pageNum - 1) * itemPerPage + ", " + itemPerPage : "";
		sql = selectFrom + " " + where + " " + group + " " + order + " " + limit;
		try {
			return jdbcTemplate.queryForObject(sql, productResponseRowMapper, category, titleKeyword, seller_name,
					product_status);
		} catch (EmptyResultDataAccessException e) {
			return new ProductResponse(null);
		}
	}

	/**
	 * change product_status according to product_id <br>
	 */
	public ActionResponse changeStatus(int product_id, String product_status) {
		String sql;
		sql = "UPDATE product SET product_status = ?, update_time = NOW() WHERE product_id = ?";
		return new ActionResponse(jdbcTemplate.update(sql, product_id, product_status) > 0);
	}

}
