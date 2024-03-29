package cn.edu.pku.course.database.idlefish.response;

import java.util.List;

import cn.edu.pku.course.database.idlefish.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductResponse {

	public List<Product> products;

}
