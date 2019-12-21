package cn.edu.pku.course.database.idlefish.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
	public int id;
	public String username;
	public String en_password;
	public String birth;
	public String sex;
	public String email;
	public String phone;
	public int account_status;
	public String update_time;
}
