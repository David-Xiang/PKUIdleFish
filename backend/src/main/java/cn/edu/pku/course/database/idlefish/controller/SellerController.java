package cn.edu.pku.course.database.idlefish.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.basic.ProductBasic;
import cn.edu.pku.course.database.idlefish.basic.UserBasic;
import cn.edu.pku.course.database.idlefish.modules.SellerMod;
import cn.edu.pku.course.database.idlefish.response.ActionResponse;

@RestController
public class SellerController {

	@Autowired
	SellerMod sellerMod;

	@Autowired
	UserBasic userBasic;

	@Autowired
	ProductBasic productBasic;

	@PostMapping("manage")
	public ActionResponse manage(@RequestParam Map<String, String> form) {
		return sellerMod.PutOnOrModify(form.get("prodcut_id"), form.get("username"), form.get("title"),
				form.get("category"), form.get("price"), form.get("description"), form.get("imgsrc"));
	}

	@PostMapping("visible")
	public ActionResponse visible(@RequestParam int product_id) {
		return productBasic.changeStatus(product_id, "sale");
	}

	@PostMapping("invisible")
	public ActionResponse invisible(@RequestParam int product_id) {
		return productBasic.changeStatus(product_id, "draft");
	}

}
