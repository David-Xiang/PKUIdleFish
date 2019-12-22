package cn.edu.pku.course.database.idlefish.modules;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.mapper.UserResponseRowMapper;

@Repository
public class QueryMod {

	@Autowired
	JdbcTemplate jdbcTemplate;

	UserResponseRowMapper userResponseRowMapper;

	public List<Map<String, Object>> simpleQuery(String sql) {
		try {
			return jdbcTemplate.queryForList(sql);
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}

	public List<Map<String, Object>> query(String sql, Object[] args) {
		try {
			return jdbcTemplate.queryForList(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}

	public List<Map<String, Object>> spentHighest() {
		return simpleQuery(
				"SELECT username, total_spent FROM account NATURAL JOIN buyer_related WHERE total_spent = (SELECT max(total_spent) FROM buyer_related)");
	}

	public List<Map<String, Object>> moreCommented() {
		return simpleQuery(
				"SELECT account.*, times FROM account INNER JOIN commented ON username = seller_name	WHERE times > (SELECT AVG(times) FROM commented) ORDER BY times DESC");
	}

	public List<Map<String, Object>> mostAgeGroup() {
		return simpleQuery(
				"SELECT seller_name, age_group AS most_age_group FROM audience AS a1 WHERE num = (SELECT max(num) FROM audience AS a2 WHERE a2.seller_name = a1.seller_name) GROUP BY seller_name");
	}

	public List<Map<String, Object>> similarBuyer(String username) {
		String sql = "SELECT account.*, num_of_items FROM account NATURAL JOIN buyer_related WHERE username <> ? AND num_of_sellers = (SELECT num_of_sellers FROM buyer_related WHERE username = ?) AND NOT EXISTS (SELECT 1 FROM transaction as t WHERE EXISTS (SELECT 1 FROM transaction WHERE buyer_name = username AND seller_name = t.seller_name) AND NOT EXISTS (SELECT 1 FROM transaction WHERE buyer_name = ? AND seller_name = t.seller_name)) ORDER BY num_of_items";
		try {
			return jdbcTemplate.queryForList(sql, username, username, username);
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}

}