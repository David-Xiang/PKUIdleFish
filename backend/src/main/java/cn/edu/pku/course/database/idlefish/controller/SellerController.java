package cn.edu.pku.course.database.idlefish.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.basic.ProductBasic;
import cn.edu.pku.course.database.idlefish.basic.UserBasic;
import cn.edu.pku.course.database.idlefish.modules.SellerMod;
import cn.edu.pku.course.database.idlefish.response.ActionResponse;
import cn.edu.pku.course.database.idlefish.response.ProductResponse;

@RestController
public class SellerController {

	@Autowired
	SellerMod sellerMod;

	@Autowired
	UserBasic userBasic;

	@Autowired
	ProductBasic productBasic;

	@GetMapping("myproduct")
	public ProductResponse myProduct(@RequestParam String username) {
		return productBasic.fetch("%", "%", "%", username, "%", "%", false,
				"ORDER BY product_status ASC, product.update_time DESC", 0, 20);
	}

	@PostMapping("manage")
	public ActionResponse manage(@RequestParam Map<String, String> form) {
		return sellerMod.PutOnOrModify(Integer.parseInt(form.get("product_id")), form.get("username"),
				form.get("title"), form.get("category"), form.get("price"), form.get("description"),
				form.get("imgsrc"));
	}

	@PostMapping("visible")
	public ActionResponse visible(@RequestParam int product_id) {
		return productBasic.changeStatus(product_id, "%r%", "sale");
	}

	@PostMapping("invisible")
	public ActionResponse invisible(@RequestParam int product_id) {
		return productBasic.changeStatus(product_id, "sale", "draft");
	}

}
