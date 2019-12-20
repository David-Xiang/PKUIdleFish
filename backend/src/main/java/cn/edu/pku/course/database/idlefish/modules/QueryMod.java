package cn.edu.pku.course.database.idlefish.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.mapper.UserResponseRowMapper;
import cn.edu.pku.course.database.idlefish.response.UserResponse;

@Repository
public class QueryMod {

	@Autowired
	JdbcTemplate jdbcTemplate;

	UserResponseRowMapper userResponseRowMapper;

	/**
	 * fetch the buyers that spent the highest total amount of money
	 */
	public UserResponse spentHighest() {
		String sql;
		sql = "SELECT account.* FROM spent INNER JOIN account ON buyer_name = username WHERE amount = (SELECT max(amount) FROM spent)";
		return jdbcTemplate.queryForObject(sql, userResponseRowMapper);
	}

}
