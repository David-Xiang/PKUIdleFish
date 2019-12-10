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

	private static String basicInfo = "id, name, birth, sex, email, phone, type";

	public List<Map<String, Object>> getUsers(String type, String orderBy, String order) {
		String sql = "SELECT " + basicInfo;
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

	public String getUserName(int id) {
		return jdbcTemplate.queryForObject("SELECT name FROM account WHERE id = '" + id + "'", String.class);
	}

}
