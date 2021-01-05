package com.atguigu.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.mybatis.entity.Monster;
import com.atguigu.mybatis.entity.Monster2;



public interface MonsterMapper {

	//添加方法
	public void addMonster(Monster monster);
	
	//根据id删除一个Monster
	public void	delMonster(Integer monster_id);
	
	//修改Monster
	public void updateMonster(Monster monster);
	//查询-根据id
	public Monster getMonsterById(Integer monster_id);
	//查询所有的Monster
	public List<Monster> findAllMonster();
	
	//通过id 或者名字查询
	public List<Monster> findMonsterByNameORId(Monster monster);
	
	//查询名字中含义'牛魔王'妖怪
	public List<Monster> findMonsterByName(String name);
	
	//查询 id > 10 并且 salary 大于 40, 要求传入的参数是HashMap
	public List<Monster> 
		findMonsterByIdAndSalary_PrameterHashMap(Map<String,Object> map);
	
	//查询 id > 10 并且 salary 大于 40, 要求传入的参数是HashMap
	public List<Map<String,Object>> 
		findMonsterByIdAndSalary_PrameterHashMap_ReturnHashMap(Map<String,Object> map);
	
	
	//根据age查询结果
	public List<Monster> 
		findMonsterByAge(@Param("age") Integer age);
	
	//根据id和名字来查询结果
	public List<Monster> findMonsterByIdAndName(Monster monster);
	
	
}
