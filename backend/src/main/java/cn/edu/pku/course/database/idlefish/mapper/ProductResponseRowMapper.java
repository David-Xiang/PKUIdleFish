package cn.edu.pku.course.database.idlefish.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.entity.Product;
import cn.edu.pku.course.database.idlefish.entity.ProductInfo;
import cn.edu.pku.course.database.idlefish.response.ProductResponse;

@Repository
@Component
public class ProductResponseRowMapper implements RowMapper<ProductResponse> {

	@Autowired
	JdbcTemplate jdbcTemplate;

	static Map<String, Integer> statusCodeMap = Map.of("draft", 0, "sale", 1, "sold", 2, "returned", 3, "deleted", 4);

	@Override
	public ProductResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<Product> products = new ArrayList<Product>();
		do {
			int product_id = rs.getInt(1);
			String sql = "SELECT * FROM comment WHERE product_id = ?";
			List<Map<String, Object>> comments = jdbcTemplate.queryForList(sql, product_id);
			products.add(new Product(product_id,
					new ProductInfo(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getString(6),
							rs.getString(7), statusCodeMap.get(rs.getString(8)), rs.getString(9), rs.getInt(10),
							rs.getString(11)),
					comments));
		} while (rs.next());
		return new ProductResponse(products);
	}
}
