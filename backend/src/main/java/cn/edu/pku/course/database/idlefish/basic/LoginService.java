package cn.edu.pku.course.database.idlefish.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.response.ActionResponse;
import cn.edu.pku.course.database.idlefish.response.LoginResponse;

@Repository
public class LoginService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	UserBasic userBasic;

	/**
	 * login by username and password <br>
	 * if success, also return user information <br>
	 */
	public LoginResponse login(String username, String password) {
		String sql, input_password_hash, real_password_hash;
		sql = "SELECT myhash(?)";
		input_password_hash = jdbcTemplate.queryForObject(sql, String.class, password);
		sql = "SELECT en_password FROM account WHERE username = ? AND account_status <> 'deleted'";
		try {
			real_password_hash = jdbcTemplate.queryForObject(sql, String.class, username);
		} catch (EmptyResultDataAccessException e) {
			return new LoginResponse(false, null);
		}
		if (input_password_hash.contentEquals(real_password_hash)) {
			return new LoginResponse(true, userBasic.get(username));
		} else {
			return new LoginResponse(false, null);
		}
	}

	/**
	 * register an new account or modify user information <br>
	 * when type = 'register', it means to register, otherwise to modify <br>
	 * username cannot duplicate <br>
	 */
	public ActionResponse registerOrModify(String type, String username, String password, String birth, String sex,
			String email, String phone) {
		String sql;
		if (type.contentEquals("register")) {
			sql = "INSERT INTO account(username, en_password, birth, sex, email, phone) VALUES (?, myhash(?), ?, ?, ?, ?)";
			try {
				return new ActionResponse(jdbcTemplate.update(sql, username, password, birth, sex, email, phone) > 0);
			} catch (DuplicateKeyException e) {
				return new ActionResponse(false);
			}
		} else {
			sql = "UPDATE account SET en_password = myhash(?), birth = ?, sex = ?, email = ?, phone = ? WHERE username = ?";
			return new ActionResponse(jdbcTemplate.update(sql, password, birth, sex, email, phone, username) > 0);
		}
	}

}
