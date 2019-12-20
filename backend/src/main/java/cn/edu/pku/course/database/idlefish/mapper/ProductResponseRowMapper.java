package cn.edu.pku.course.database.idlefish.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.entity.Comment;
import cn.edu.pku.course.database.idlefish.entity.Product;
import cn.edu.pku.course.database.idlefish.entity.ProductInfo;
import cn.edu.pku.course.database.idlefish.response.ProductResponse;

@Repository
@Component
public class ProductResponseRowMapper implements RowMapper<ProductResponse> {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	CommentListRowMapper commentListRowMapper;

	@Override
	public ProductResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<Product> products = new ArrayList<Product>();
		while (rs.next()) {
			String sql;
			int product_id = rs.getInt(1);
			sql = "SELECT * FROM comment WHERE product_id = ?";
			List<Comment> comments = jdbcTemplate.queryForObject(sql, commentListRowMapper, product_id);
			products.add(new Product(product_id,
					new ProductInfo(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getString(6),
							rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getString(11)),
					comments));
		}
		return new ProductResponse(products);
	}
}