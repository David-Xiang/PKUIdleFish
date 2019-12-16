package cn.edu.pku.course.database.idlefish.response;

import cn.edu.pku.course.database.idlefish.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    Boolean success;
    User user;
}
