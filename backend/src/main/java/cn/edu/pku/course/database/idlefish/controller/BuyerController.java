package cn.edu.pku.course.database.idlefish.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.basic.ProductBasic;
import cn.edu.pku.course.database.idlefish.basic.UserBasic;
import cn.edu.pku.course.database.idlefish.modules.BuyerMod;
import cn.edu.pku.course.database.idlefish.response.ActionResponse;
import cn.edu.pku.course.database.idlefish.response.ProductResponse;

@RestController
public class BuyerController {

	@Autowired
	ProductBasic productBasic;

	@Autowired
	UserBasic userBasic;

	@Autowired
	BuyerMod buyerMod;

	@GetMapping("cart")
	public ProductResponse cart(@RequestParam String username) {
		return productBasic.fetch("%", "%", username, "%", "%", "cart", false, "ORDER BY bargain.update_time DESC", 0,
				20);
	}

	@GetMapping("bought")
	public ProductResponse bought(@RequestParam String username) {
		return productBasic.fetch("%", "%", username, "%", "%", "done", false, "ORDER BY bargain.update_time DESC", 0,
				20);
	}

	@PostMapping("forseller")
	public ActionResponse forSeller(@RequestParam String username) {
		return userBasic.changeStatus(username, "buyer", "forseller");
	}

	@PostMapping("addcart")
	public ActionResponse addCart(@RequestParam String username, @RequestParam int product_id) {
		return buyerMod.addBargain(username, product_id);
	}

	@PostMapping("removecart")
	public ActionResponse removeCart(@RequestParam String username, @RequestParam int product_id) {
		return buyerMod.removeBargain(username, product_id);
	}

	@PostMapping("buy")
	public ActionResponse buy(@RequestParam String username, @RequestParam int product_id) {
		return buyerMod.buyProduct(username, product_id);
	}

	@PostMapping("comment")
	public ActionResponse comment(@RequestParam Map<String, String> form) {
		return buyerMod.leaveComment(form.get("username"), Integer.parseInt(form.get("product_id")),
				form.get("content"));
	}

}
