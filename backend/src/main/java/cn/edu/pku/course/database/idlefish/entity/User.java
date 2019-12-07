package cn.edu.pku.course.database.idlefish.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@AllArgsConstructor
@Data
public class User {
    public int id;
    public String name;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date birthday;
    public String sex;
    public String email;
    public String phone;
    public int status;
}
