package cn.edu.pku.course.database.idlefish.response;

import cn.edu.pku.course.database.idlefish.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ProductResponse {
    public List<Product> products;
}
