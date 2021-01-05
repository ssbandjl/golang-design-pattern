package com.atguigu.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atguigu.mybatis.entity.Monster;



public interface MonsterAnnotation {
		//添加方法,将我们的sql语句直接写在@Insert注解即可
		@Insert("INSERT INTO mybatis_monster_ (age,birthday,email,gender,name,salary) "
				+ "VALUES(#{age},#{birthday},#{email},#{gender},#{name},#{salary})")
		public void addMonster(Monster monster);
		//根据id删除一个Monster
		@Delete("DELETE FROM mybatis_monster_  "
				+ "WHERE monster_id=#{monster_id}")
		public void	delMonster(Integer monster_id);
		//修改Monster
		@Update("UPDATE mybatis_monster_ SET age=#{age}, birthday=#{birthday}, "
				+ "email = #{email},gender= #{gender}, "
				+ "name=#{name}, salary=#{salary} "
				+ "WHERE monster_id=#{monster_id}")
		public void updateMonster(Monster monster);
		//查询-根据id
		@Select("SELECT * FROM mybatis_monster_ WHERE "
				+ "monster_id = #{monster_id}")
		public Monster getMonsterById(Integer monster_id);
		//查询所有的Monster
		@Select("SELECT * FROM mybatis_monster_ ")
		public List<Monster> findAllMonster();
}
