package cn.edu.pku.course.database.idlefish.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cn.edu.pku.course.database.idlefish.entity.Comment;

@Component
public class CommentListRowMapper implements RowMapper<List<Comment>> {

	@Override
	public List<Comment> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<Comment> comments = new ArrayList<Comment>();
		while (rs.next()) {
			comments.add(new Comment(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
		}
		return comments;
	}
}
