package cn.edu.pku.course.database.idlefish.response;

import cn.edu.pku.course.database.idlefish.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UsersResponse {
    public List<User> users;
}
