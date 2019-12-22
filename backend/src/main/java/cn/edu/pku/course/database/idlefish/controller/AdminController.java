package cn.edu.pku.course.database.idlefish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.basic.ProductBasic;
import cn.edu.pku.course.database.idlefish.basic.TransactionBasic;
import cn.edu.pku.course.database.idlefish.basic.UserBasic;
import cn.edu.pku.course.database.idlefish.modules.BuyerMod;
import cn.edu.pku.course.database.idlefish.response.ActionResponse;
import cn.edu.pku.course.database.idlefish.response.ProductResponse;
import cn.edu.pku.course.database.idlefish.response.TransactionResponse;
import cn.edu.pku.course.database.idlefish.response.UserResponse;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	BuyerMod buyerMod;

	@Autowired
	UserBasic userBasic;

	@Autowired
	ProductBasic productBasic;

	@Autowired
	TransactionBasic transactionBasic;

	@GetMapping("test")
	public String test() {
		return "it works!";
	}

	@GetMapping("user")
	public UserResponse user() {
		return userBasic.fetch("%", " ORDER BY account_status DESC, update_time DESC", 0, 20);
	}

	@GetMapping("product")
	public ProductResponse product(@RequestParam int page) {
		return productBasic.fetch("%", "%", "%", "%", "%", "%", false,
				"ORDER BY product_status ASC, product.update_time DESC", page, 20);
	}

	@GetMapping("transaction")
	public TransactionResponse transaction(@RequestParam int page) {
		return transactionBasic.fetch("ORDER BY transaction_id DESC", page, 20);
	}

	@PostMapping("seller")
	public ActionResponse seller(@RequestParam String username) {
		return userBasic.changeStatus(username, "forseller", "seller");
	}

	@PostMapping("offline/product")
	public ActionResponse offlineProduct(@RequestParam int product_id) {
		return productBasic.changeStatus(product_id, "%", "deleted");
	}

	@PostMapping("offline/user")
	public ActionResponse offlineUser(@RequestParam String username) {
		return userBasic.changeStatus(username, "%e%", "deleted");
	}

}
