package cn.edu.pku.course.database.idlefish.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cn.edu.pku.course.database.idlefish.entity.User;
import cn.edu.pku.course.database.idlefish.response.UserResponse;

@Component
public class UserResponseRowMapper implements RowMapper<UserResponse> {

	static Map<String, Integer> statusCodeMap = Map.of("buyer", 0, "seller", 1, "admin", 2, "deleted", 3, "forseller",
			4);

	@Override
	public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<User> users = new ArrayList<User>();
		do {
			users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), statusCodeMap.get(rs.getString(8)), rs.getString(9)));
		} while (rs.next());
		return new UserResponse(users);
	}
}
