package cn.edu.pku.course.database.idlefish.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.repository.ProductRepository;
import cn.edu.pku.course.database.idlefish.repository.UserRepository;

@RestController
public class CommonController {

	@Autowired
	ProductRepository productRepo;

	@Autowired
	UserRepository userRepo;

	private static int ITEM_PER_PAGE = 20;

	@GetMapping("product")
	public List<Map<String, Object>> product(@RequestParam int page, @RequestParam String category) {
		return productRepo.viewProducts(category.equals("0") ? "%" : category, "sale", page, ITEM_PER_PAGE);
	}

	@GetMapping("login")
	public boolean login(@RequestParam String username, @RequestParam String password) {
		return userRepo.checkLogin(username, password);
	}

	@PostMapping("register")
	public boolean registerOrModify(@RequestParam Map<String, String> form) {
		return userRepo.registerOrModify(form.get("type"), form.get("username"), form.get("password"),
				form.get("birth"), form.get("sex"), form.get("email"), form.get("phone"));
	}

}
