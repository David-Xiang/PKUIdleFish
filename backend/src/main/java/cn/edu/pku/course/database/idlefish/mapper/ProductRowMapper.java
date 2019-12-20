package cn.edu.pku.course.database.idlefish.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.entity.Comment;
import cn.edu.pku.course.database.idlefish.entity.Product;
import cn.edu.pku.course.database.idlefish.entity.ProductInfo;

@Repository
@Component
public class ProductRowMapper implements RowMapper<Product> {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	CommentListRowMapper commentListRowMapper;

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		int product_id = rs.getInt(1);
		String sql;
		sql = "SELECT * FROM comment WHERE product_id = ?";
		List<Comment> comments = jdbcTemplate.queryForObject(sql, commentListRowMapper, product_id);
		return new Product(product_id, new ProductInfo(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5),
				rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getString(11)),
				comments);
	}
}
