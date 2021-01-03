package com.atguigu.mybatis.entity;

public class Monster2 {

	private Integer monster_id;
	private String username;
	private String useremail;
	public Monster2() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Monster2(Integer monster_id, String username, String useremail) {
		super();
		this.monster_id = monster_id;
		this.username = username;
		this.useremail = useremail;
	}
	public Integer getMonster_id() {
		return monster_id;
	}
	public void setMonster_id(Integer monster_id) {
		this.monster_id = monster_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	@Override
	public String toString() {
		return "Monster2 [monster_id=" + monster_id + ", username=" + username
				+ ", useremail=" + useremail + "]";
	}
	
	
}
