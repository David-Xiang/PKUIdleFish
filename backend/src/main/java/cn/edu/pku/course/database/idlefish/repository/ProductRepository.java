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

	private static String ORDER = "ORDER BY produpdatetime DESC";

	public List<Map<String, Object>> viewProducts(String categoryid, String status, int page, int itemPerPage) {
		String sql = "SELECT *, count(*) AS hot FROM product INNER JOIN category USING (categoryid) LEFT JOIN transaction ON id = productid";
		sql += " WHERE categoryid LIKE ? AND productstatus LIKE ? GROUP BY id " + ORDER + " LIMIT ?, ?";
		return jdbcTemplate.queryForList(sql, categoryid, status, (page - 1) * itemPerPage, itemPerPage);
	}

	public List<Map<String, Object>> buyList(String username, String status) {
		String subsql = "(SELECT * FROM transaction WHERE buyername LIKE ? AND transactionstatus LIKE ?) AS sub";
		String sql = "SELECT * FROM product INNER JOIN " + subsql + " ON id = productid " + ORDER;
		return jdbcTemplate.queryForList(sql, username, status);
	}

	public List<Map<String, Object>> sellList(String username, String status) {
		String sql = "SELECT * FROM product WHERE sellername LIKE ? AND productstatus LIKE ? " + ORDER;
		return jdbcTemplate.queryForList(sql, username, status);
	}

}
