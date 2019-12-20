package cn.edu.pku.course.database.idlefish.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.entity.User;
import cn.edu.pku.course.database.idlefish.mapper.UserResponseRowMapper;
import cn.edu.pku.course.database.idlefish.mapper.UserRowMapper;
import cn.edu.pku.course.database.idlefish.response.ActionResponse;
import cn.edu.pku.course.database.idlefish.response.UserResponse;

@Repository
public class UserBasic {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	UserRowMapper userRowMapper;
	@Autowired
	UserResponseRowMapper userResponseRowMapper;

	/**
	 * get user information according to username <br>
	 */
	public User get(String username) {
		String sql;
		sql = "SELECT * FROM account WHERE username = ?";
		try {
			return jdbcTemplate.queryForObject(sql, userRowMapper, username);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * fetch user information according to account_status <br>
	 * when account_status == '%', it means to fetch all users <br>
	 * result will be ordered using *order* and divided by page using *pageNum* and
	 * *itemPerPage* if pageNum > 0 <br>
	 */
	public UserResponse fetch(String account_status, String order, int pageNum, int itemPerPage) {
		String sql;
		String limit = (pageNum > 0) ? " LIMIT " + (pageNum - 1) * itemPerPage + ", " + itemPerPage : "";
		sql = "SELECT * FROM account WHERE account_status LIKE ?" + order + limit;
		try {
			return jdbcTemplate.queryForObject(sql, userResponseRowMapper, account_status);
		} catch (EmptyResultDataAccessException e) {
			return new UserResponse(null);
		}
	}

	/**
	 * change account_status according to username <br>
	 */
	public ActionResponse changeStatus(String username, String account_status) {
		String sql;
		sql = "UPDATE account SET account_status = ?, update_time = NOW() WHERE username = ?";
		return new ActionResponse(jdbcTemplate.update(sql, account_status, username) > 0);
	}

}
