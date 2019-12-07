package cn.edu.pku.course.database.idlefish.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class User {
    public int id;
    public String name;
    public Date birthday;
    public String sex;
    public String email;
    public String phone;
    public Boolean isSeller;
    public Boolean isAdmin;
}
