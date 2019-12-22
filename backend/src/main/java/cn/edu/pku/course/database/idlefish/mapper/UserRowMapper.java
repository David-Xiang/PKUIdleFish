package cn.edu.pku.course.database.idlefish.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cn.edu.pku.course.database.idlefish.entity.User;

@Component
public class UserRowMapper implements RowMapper<User> {

	static Map<String, Integer> statusCodeMap = Map.of("buyer", 0, "seller", 1, "admin", 2, "deleted", 3, "forseller",
			4);

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
				rs.getString(6), rs.getString(7), statusCodeMap.get(rs.getString(8)), rs.getString(9));
	}
}
