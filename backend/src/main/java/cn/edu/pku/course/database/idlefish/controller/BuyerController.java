package cn.edu.pku.course.database.idlefish.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.repository.ProductRepository;

@RestController
public class BuyerController {

	@Autowired
	ProductRepository productRepo;

	/*
	 * view cart
	 */
	@GetMapping("cart")
	public List<Map<String, Object>> cart(@RequestParam String username) {
		return productRepo.viewCart(username);
	}

}
