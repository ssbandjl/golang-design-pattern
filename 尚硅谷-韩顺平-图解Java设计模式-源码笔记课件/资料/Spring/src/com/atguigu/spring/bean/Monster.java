package com.atguigu.spring.bean;

/**
 * 注释
 * @author Administrator
 *
 */
public class Monster {

	private Integer id = 10 ;
	private String nickname = "牛魔王";
	private String skill = "芭蕉扇";
	public Monster() {
		
		System.out.println("monster 创建..");
	}
	public Monster(Integer id, String nickname, String skill) {
		//System.out.println("Integer id, String nickname, String skill被调用");
		this.id = id;
		this.nickname = nickname;
		this.skill = skill;
	}
	
	public Monster( String nickname, String skill,Integer id) {
		
		this.id = id;
		this.nickname = nickname;
		this.skill = skill;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	@Override
	public String toString() {
		return "Monster [id=" + id + ", nickname=" + nickname + ", skill="
				+ skill + "]";
	}
	
	
}
