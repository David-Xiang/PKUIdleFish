package cn.edu.pku.course.database.idlefish.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static String viewInfo = "title, imgsrc, price, sellerid, description, saletime";

	public String getCategoryName(int categoryid) {
		return jdbcTemplate.queryForObject("SELECT name FROM category WHERE categoryid = '" + categoryid + "'",
				String.class);
	}

	public List<Map<String, Object>> getProducts(boolean sold, int page, int itemPerPage) {
		return null;
	}

	public List<Map<String, Object>> viewProducts(int page, String category, int itemPerPage) {
		String sql = "SELECT " + viewInfo + " FROM product INNER JOIN category USING (categoryid)";
		sql += " WHERE name = '" + category + "' AND status = 'sale'";
		sql += " ORDER BY saletime DESC";
		sql += " LIMIT " + (page - 1) * itemPerPage + ", " + itemPerPage;
		return jdbcTemplate.queryForList(sql);
	}

}
