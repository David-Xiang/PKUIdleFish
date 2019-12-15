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

	private static String ORDER = "ORDER BY categoryname ASC, produpdatetime DESC";

	private String userId(String username) {
		String sql = "SELECT id FROM account WHERE username = ?";
		return jdbcTemplate.queryForObject(sql, String.class, username);
	}

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

	public boolean addCart(String username, String productid) {
		String sql = "SELECT productstatus FROM product WHERE id = ?";
		if (!jdbcTemplate.queryForObject(sql, String.class, productid).equals("sale")) {
			return false;
		}
		sql = "INSERT INTO transaction(productid, buyerid, buyername, transactionstatus) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql, productid, userId(username), username, "cart") > 0;
	}

	public boolean deleteCart(String username, String productid) {
		String sql = "DELETE FROM transaction where productid = ? AND buyername = ? AND transactionstatus = ?";
		return jdbcTemplate.update(sql, productid, username, "cart") > 0;
	}

	public boolean buy(String username, String productid) {
		String sql = "SELECT productstatus FROM product WHERE id = ?";
		if (!jdbcTemplate.queryForObject(sql, String.class, productid).equals("sale")) {
			return false;
		}
		sql = "SELECT transactionstatus FROM transaction where productid = ? AND buyername = ?";
		if (!jdbcTemplate.queryForObject(sql, String.class, productid, username).equals("cart")) {
			return false;
		}
		sql = "UPDATE transaction SET transactionstatus = ? WHERE productid = ? AND buyername = ?";
		if (jdbcTemplate.update(sql, "bought", productid, username) == 0) {
			return false;
		}
		sql = "UPDATE product SET prodcutstatus = ? WHERE id = ?";
		return jdbcTemplate.update(sql, "sold", productid) > 0;
	}

	public boolean returnProduct(String productid) {
		String sql = "UPDATE product SET productstatus = ? WHERE id = ?";
		jdbcTemplate.update(sql, "returned", productid);
		sql = "UPDATE transaction SET transactionstatus = ? WHERE productid = ? AND transactionstatus = ?";
		return jdbcTemplate.update(sql, "returned", productid, "bought") > 0;
	}

	public boolean addComment(String username, String productid, String content) {
		String sql = "INSERT INTO comment(userid, username, productid, content) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql, userId(username), username, productid, content) > 0;
	}

	public boolean addOrModify(String productid, String username, String title, String categoryid, String price,
			String description, String imgsrc) {
		String sql;
		if (productid.equals("0")) {
			sql = "INSERT INTO product(title, sellerid, sellername, categoryid, price, description, imgsrc) VALUES (?, ?, ?, ?, ?, ?, ?)";
			try {
				return jdbcTemplate.update(sql, title, userId(username), username, categoryid, price, description,
						imgsrc) > 0;
			} catch (Exception e) {
				return false;
			}
		} else {
			sql = "UPDATE product SET title = ?, categoryid = ?, price = ?, descript = ?, imgsrc = ? WHERE id = ? AND sellername = ?";
			try {
				return jdbcTemplate.update(sql, title, categoryid, price, description, imgsrc, productid, username) > 0;
			} catch (Exception e) {
				return false;
			}
		}
	}

	public boolean visible(String productid) {
		String sql = "UPDATE prodcut SET productstatus = ? WHERE id = ?";
		return jdbcTemplate.update(sql, "sale", productid) > 0;
	}

	public boolean invisible(String productid) {
		String sql = "SELECT productstatus FROM product WHERE id = ?";
		if (!jdbcTemplate.queryForObject(sql, String.class, productid).equals("sale")) {
			return false;
		}
		sql = "UPDATE product SET productstatus = ? WHERE id = ?";
		return jdbcTemplate.update(sql, "draft", productid) > 0;
	}

	public boolean deleteProduct(String productid) {
		String sql = "UPDATE product SET productstatus = ? WHERE id = ?";
		return jdbcTemplate.update(sql, "deleted", productid) > 0;
	}

}
