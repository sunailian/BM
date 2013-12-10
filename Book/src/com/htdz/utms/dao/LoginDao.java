package com.htdz.utms.dao;

import com.htdz.utms.model.Student;


/**
 * 用户登录
 * @author LiShuheng 
 */
public interface LoginDao {
	
	/**
	 * 根据用户名密码获取学生信息
	 * @param stu
	 * @return
	 * @throws Exception
	 */
	public Student findStuInfo(Student stu) throws Exception;
	
	
}
