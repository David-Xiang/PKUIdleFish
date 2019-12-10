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

	private static String viewInfo = "id, name, birth, sex, email, phone, type";

	public List<Map<String, Object>> getUsers(String type, String orderBy, String order) {
		String sql = "SELECT " + viewInfo;
		if (type == "forseller") {
			sql += ", requesttime FROM account INNER JOIN forseller ON id = forsellerid";
		} else if (type == "seller") {
			sql += ", approvetime FROM account INNER JOIN seller ON id = sellerid";
		} else {
			sql += " FROM account";
		}
		sql += " WHERE type = '" + type + "' ORDER BY " + orderBy + " " + order;
		return jdbcTemplate.queryForList(sql);
	}

	public boolean checkLogin(String name, String passwd) {
		String hash1 = jdbcTemplate.queryForObject("SELECT en_passwd FROM account WHERE name = '" + name + "'",
				String.class);
		String hash2 = jdbcTemplate.queryForObject("SELECT myhash('" + passwd + "')", String.class);
		return hash1.equals(hash2);
	}

	public boolean register(String name, String passwd, String birth, String sex, String email, String phone) {
		String sql = "INSERT INTO account(name, en_passwd, birth, sex, email, phone) VALUES (?, myhash(?), ?, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, name, passwd, birth, sex, email, phone);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean modify(String id, String passwd, String birth, String sex, String email, String phone) {
		String sql = "UPDATE account SET en_passwd = myhash(?), birth = ?, sex = ?, email = ?, phone = ? WHERE id = ?";
		try {
			jdbcTemplate.update(sql, passwd, birth, sex, email, phone, id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
