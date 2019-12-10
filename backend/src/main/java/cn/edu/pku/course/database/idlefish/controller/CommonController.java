package cn.edu.pku.course.database.idlefish.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.repository.ProductRepository;
<<<<<<< Updated upstream
=======
import cn.edu.pku.course.database.idlefish.repository.UserRepository;
>>>>>>> Stashed changes

@RestController
public class CommonController {

	@Autowired
<<<<<<< Updated upstream
	ProductRepository productRepository;
=======
	ProductRepository productRepo;

	@Autowired
	UserRepository userRepo;
>>>>>>> Stashed changes

	private static int itemPerPage = 2;

	/*
	 * Gets a certain page of products of a certain category
	 */
	@GetMapping("product")
	public List<Map<String, Object>> product(@RequestParam int page, @RequestParam String category) {
<<<<<<< Updated upstream
		return productRepository.viewProducts(page, category, itemPerPage);
=======
		return productRepo.viewProducts(page, category, itemPerPage);
	}

	/*
	 * Login, returns 1 if succeeded, 0 if failed
	 */
	@GetMapping("login")
	public boolean login(@RequestParam String username, @RequestParam String password) {
		return userRepo.checkLogin(username, password);
>>>>>>> Stashed changes
	}

}
