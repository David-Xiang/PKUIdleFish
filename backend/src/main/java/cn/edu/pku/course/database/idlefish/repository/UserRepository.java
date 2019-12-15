package cn.edu.pku.course.database.idlefish.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static String ORDER = "ORDER BY accupdatetime DESC";

	public Map<String, Object> getUserInfo(String username) {
		String sql = "SELECT * FROM account WHERE username = ?";
		return jdbcTemplate.queryForList(sql, username).get(0);
	}

	public List<Map<String, Object>> userList(String status) {
		String sql = "SELECT * FROM account WHERE accountstatus LIKE ? " + ORDER;
		return jdbcTemplate.queryForList(sql, status);
	}

	public boolean checkLogin(String username, String passwd) {
		String hash1 = jdbcTemplate.queryForObject("SELECT en_passwd FROM account WHERE username = ?", String.class,
				username);
		String hash2 = jdbcTemplate.queryForObject("SELECT myhash(?)", String.class, passwd);
		return hash1.equals(hash2);
	}

	// TODO: add constraints
	public boolean registerOrModify(String type, String username, String passwd, String birth, String sex, String email,
			String phone) {
		String sql = type.equals("register")
				? "INSERT INTO account(en_passwd, birth, sex, email, phone, username) VALUES (myhash(?), ?, ?, ?, ?, ?)"
				: "UPDATE account SET en_passwd = myhash(?), birth = ?, sex = ?, email = ?, phone = ? WHERE username = ?";
		try {
			jdbcTemplate.update(sql, passwd, birth, sex, email, phone, username);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deleteUser(String username) {
		String sql = "UPDATE account SET accountstatus = ? WHERE username = ?";
		return jdbcTemplate.update(sql, "deleted", username) > 0;
	}

	public List<Map<String, Object>> wealthiest() {
		String subsql = "(SELECT productid, buyername FROM transaction WHERE transactionstatus = ?) as sub";
		String sql = "SELECT sum(price) as totalspent, buyername FROM product INNER JOIN " + subsql
				+ " ON id = productid GROUP BY buyername ORDER BY totalspent DESC";
		List<Map<String, Object>> wealthList = jdbcTemplate.queryForList(sql, "sold");
		String max = (String) wealthList.get(0).get("totalspent");
		List<Map<String, Object>> mostWealthList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> m : wealthList) {
			if (max.equals((String) m.get("totalspent"))) {
				mostWealthList.add(getUserInfo((String) m.get("buyername")));
			}
		}
		return wealthList;
	}

	public List<Map<String, Object>> mostComment() {
		return null;
	}

}
