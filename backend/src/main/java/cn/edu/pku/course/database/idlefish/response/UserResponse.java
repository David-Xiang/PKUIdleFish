package cn.edu.pku.course.database.idlefish.response;

import java.util.List;

import cn.edu.pku.course.database.idlefish.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserResponse {

	public List<User> users;

}
