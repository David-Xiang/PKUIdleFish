package cn.edu.pku.course.database.idlefish.controller;

import cn.edu.pku.course.database.idlefish.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
public class MockController {
    @GetMapping("/mock")
    @ResponseBody
    public XML getMethod() {
        XML xml = new XML();
        Comment comment = new Comment();
        comment.buyerName = "如花";
        comment.content = "衣服很好看，穿上显瘦";
        Product product = new Product();
        product.productID = 0;
        product.title = "衬衫";
        product.description = "爆款女士衬衫，网红最爱";
        product.category = 0;
        product.price = 100.0;
        product.sellerID = 111;
        product.comments = Arrays.asList(comment);
        xml.products = Arrays.asList(product);
        return xml;
    }

}
