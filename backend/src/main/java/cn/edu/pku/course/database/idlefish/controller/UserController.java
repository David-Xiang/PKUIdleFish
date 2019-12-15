package cn.edu.pku.course.database.idlefish.controller;

import java.util.ArrayList;
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
public class UserController {

	@Autowired
	ProductRepository productRepo;

	@Autowired
	UserRepository userRepo;

	// unfinished, do not use
	@GetMapping("info")
	public Map<String, Object> info(@RequestParam String username) {
		return userRepo.getUserInfo(username);
	}

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

	// unfinished, do not use
	@PostMapping("addcart")
	public boolean addcart(@RequestParam String username, @RequestParam String productid) {
		return productRepo.addCart(username, productid);
	}

	// unfinished, do not use
	@PostMapping("deletecart")
	public boolean deletecart(@RequestParam String username, @RequestParam String productid) {
		return productRepo.deleteCart(username, productid);
	}

	// unfinished, do not use
	@PostMapping("buy")
	public boolean buy(@RequestParam String username, @RequestParam String productid) {
		return productRepo.buy(username, productid);
	}

	// unfinished, do not use
	@PostMapping("return")
	public boolean returnproduct(@RequestParam String productid) {
		return productRepo.returnProduct(productid);
	}

	// unfinished, do not use
	@PostMapping("comment")
	public boolean comment(@RequestParam Map<String, String> form) {
		return productRepo.addComment(form.get("username"), form.get("productid"), form.get("content"));
	}

	// unfinished, do not use
	@PostMapping("manage")
	public boolean manage(@RequestParam Map<String, String> form) {
		return productRepo.addOrModify(form.get("prodcutid"), form.get("username"), form.get("productname"),
				form.get("categoryid"), form.get("price"), form.get("descript"), form.get("imgsrc"));
	}

	// unfinished, do not use
	@PostMapping("visible")
	public boolean visible(@RequestParam String productid) {
		return productRepo.visible(productid);
	}

	// unfinished, do not use
	@PostMapping("invisible")
	public boolean invisible(@RequestParam String productid) {
		return productRepo.invisible(productid);
	}

}
