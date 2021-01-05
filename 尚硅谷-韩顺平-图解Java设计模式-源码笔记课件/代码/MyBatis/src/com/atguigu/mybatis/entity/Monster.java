package com.atguigu.mybatis.entity;

import java.util.Date;

//就是一个普通的Pojo类
//因为 使用原生态的sql语句查询结果还是要封装成对象
//所以我们要求大家这里的实体类属性名和表名字段保持一致。
public class Monster {
	
	private Integer monster_id;
	private Integer age;
	private String name;
	private String email;
	private Date birthday;
	private double salary;
	private Integer gender;
	public Monster() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Monster(Integer monster_id, Integer age, String name, String email,
			Date birthday, double salary, Integer gender) {
		super();
		this.monster_id = monster_id;
		this.age = age;
		this.name = name;
		this.email = email;
		this.birthday = birthday;
		this.salary = salary;
		this.gender = gender;
	}
	public Integer getMonster_id() {
		return monster_id;
	}
	public void setMonster_id(Integer monster_id) {
		this.monster_id = monster_id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Monster [monster_id=" + monster_id + ", age=" + age + ", name="
				+ name + ", email=" + email + ", birthday=" + birthday
				+ ", salary=" + salary + ", gender=" + gender + "]";
	}
	
	
	
}
