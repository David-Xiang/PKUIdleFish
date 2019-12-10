package cn.edu.pku.course.database.idlefish.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.pku.course.database.idlefish.repository.UserRepository;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	UserRepository userRepository;

	/*
	 * test
	 */
	@GetMapping("test")
	public String test() {
		return "it works!";
	}

	private static String[] TYPE = { "admin", "forseller", "seller", "deleted", "buyer" };
	private static String[] ORDER_BY = { "id", "requesttime", "approvetime", "id", "id" };

	/*
	 * Gets all users and their personal information, ordered by their type.
	 * ('admin' > 'forseller' > 'seller' > 'deleted' > 'buyer')
	 */
	@GetMapping("user")
	public List<Map<String, Object>> user() {
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < TYPE.length; ++i) {
			userList.addAll(userRepository.getUsers(TYPE[i], ORDER_BY[i], "DESC"));
		}
		return userList;
	}

}
