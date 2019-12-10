package cn.edu.pku.course.database.idlefish.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.repository.ProductRepository;

@RestController
public class UserController {

	@Autowired
	ProductRepository productRepo;

	@GetMapping("cart")
	public List<Map<String, Object>> cart(@RequestParam String username) {
		return productRepo.buyList(username, "cart");
	}

	@GetMapping("related")
	public List<List<Map<String, Object>>> related(@RequestParam String username) {
		List<List<Map<String, Object>>> relatedList = new ArrayList<List<Map<String, Object>>>();
		relatedList.add(productRepo.buyList(username, "bought"));
		relatedList.add(productRepo.sellList(username, "%"));
		return relatedList;
	}

}
