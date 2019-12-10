package cn.edu.pku.course.database.idlefish.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.repository.ProductRepository;

@RestController
public class CommonController {

	@Autowired
	ProductRepository productRepository;

	private static int itemPerPage = 2;

	/*
	 * Gets a certain page of products of a certain category
	 */
	@GetMapping("product")
	public List<Map<String, Object>> product(@RequestParam int page, @RequestParam String category) {
		return productRepository.viewProducts(page, category, itemPerPage);
	}

}
