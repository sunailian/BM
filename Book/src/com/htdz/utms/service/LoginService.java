package com.htdz.utms.service;

import com.htdz.utms.model.Student;
import com.htdz.utms.model.User;

/**
 * 用户登录
 * 
 * @author LiShuheng
 * @date 2013-12-07
 */
public interface LoginService {

	/**
	 * 根据用户名密码获取学生信息
	 * 
	 * @param stu
	 * @return
	 * @throws Exception
	 */
	public Student findStuInfo(Student stu) throws Exception;

}
