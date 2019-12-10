package cn.edu.pku.course.database.idlefish.repository;

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

}
