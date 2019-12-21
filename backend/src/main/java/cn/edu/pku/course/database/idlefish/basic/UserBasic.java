package cn.edu.pku.course.database.idlefish.basic;

import java.util.Collections;

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
		String sql = "SELECT * FROM account WHERE username = ?";
		try {
			return jdbcTemplate.queryForObject(sql, userRowMapper, username);
		} catch (EmptyResultDataAccessException e) {
			throw e;
		}
	}

	/**
	 * fetch user information according to account_status <br>
	 * when account_status == '%', it means to fetch all users <br>
	 * result will be ordered using *order* and divided by page using *pageNum* and
	 * *itemPerPage* if pageNum > 0 <br>
	 */
	public UserResponse fetch(String account_status, String order, int pageNum, int itemPerPage) {
		String limit = (pageNum > 0) ? " LIMIT " + (pageNum - 1) * itemPerPage + ", " + itemPerPage : "";
		String sql = "SELECT * FROM account WHERE account_status LIKE ?" + order + limit;
		try {
			return jdbcTemplate.queryForObject(sql, userResponseRowMapper, account_status);
		} catch (EmptyResultDataAccessException e) {
			return new UserResponse(Collections.emptyList());
		}
	}

	/**
	 * change account_status according to username and new_status <br>
	 * account_status must be like old_status <br>
	 * when old_status == '%', it doesn't matter <br>
	 */
	public ActionResponse changeStatus(String username, String old_status, String new_status) {
		String sql = "UPDATE account SET account_status = ? WHERE username = ? AND account_status LIKE ?";
		return new ActionResponse(jdbcTemplate.update(sql, new_status, username, old_status) > 0);
	}

}
