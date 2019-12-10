package cn.edu.pku.course.database.idlefish.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.repository.ProductRepository;
import cn.edu.pku.course.database.idlefish.repository.UserRepository;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ProductRepository productRepo;

	@GetMapping("test")
	public String test() {
		return "it works!";
	}

	private static int ITEM_PER_PAGE = 20;
	private static String[] USER_STATUS = { "admin", "forseller", "seller", "deleted", "buyer" };

	@GetMapping("user")
	public List<Map<String, Object>> user() {
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < USER_STATUS.length; ++i) {
			userList.addAll(userRepo.userList(USER_STATUS[i]));
		}
		return userList;
	}

	@GetMapping("product")
	public List<Map<String, Object>> product(@RequestParam int page, @RequestParam String sold) {
		return productRepo.viewProducts("%", sold.equals("0") ? "%" : "sold", page, ITEM_PER_PAGE);
	}

}
