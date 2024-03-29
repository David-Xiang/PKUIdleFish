package cn.edu.pku.course.database.idlefish.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.basic.LoginService;
import cn.edu.pku.course.database.idlefish.basic.ProductBasic;
import cn.edu.pku.course.database.idlefish.modules.BuyerMod;
import cn.edu.pku.course.database.idlefish.modules.QueryMod;
import cn.edu.pku.course.database.idlefish.response.ActionResponse;
import cn.edu.pku.course.database.idlefish.response.LoginResponse;
import cn.edu.pku.course.database.idlefish.response.ProductResponse;

@RestController
public class CommonController {

	@Autowired
	ProductBasic productBasic;

	@Autowired
	BuyerMod buyerMod;

	@Autowired
	QueryMod queryMod;

	@Autowired
	LoginService loginService;

	@GetMapping("product")
	public ProductResponse product(@RequestParam int category, @RequestParam String keyword, @RequestParam int page) {
		return productBasic.fetch(category == 0 ? "%" : category + "", keyword.isEmpty() ? "%" : "%" + keyword + "%",
				"%", "%", "sale", "%", true, "ORDER BY product.update_time DESC", page, 20);
	}

	@PostMapping("login")
	public LoginResponse login(@RequestParam String username, @RequestParam String password) {
		return loginService.login(username, password);
	}

	@PostMapping("register")
	public ActionResponse registerOrModify(@RequestParam String type, @RequestParam String username,
			@RequestParam String password, @RequestParam String birth, @RequestParam String sex,
			@RequestParam String email, @RequestParam String phone) {
		return loginService.registerOrModify(type, username, password, birth, sex, email, phone);
	}

	@GetMapping("spenthighest")
	public List<Map<String, Object>> spentHighest() {
		return queryMod.spentHighest();
	}

	@GetMapping("morecommented")
	public List<Map<String, Object>> moreCommented() {
		return queryMod.moreCommented();
	}

	@GetMapping("allproduct")
	public ProductResponse allProduct() {
		return productBasic.fetch("%", "%", "%", "%", "%", "%", true,
				"ORDER BY category_name ASC, product.update_time DESC", 0, 20);
	}
	@GetMapping("mostagegroup")
	public List<Map<String, Object>> mostAgeGroup() {
		return queryMod.mostAgeGroup();
	}

	@GetMapping("similarbuyer")
	public List<Map<String, Object>> similarBuyer(@RequestParam String username) {
		return queryMod.similarBuyer(username);
	}

}
