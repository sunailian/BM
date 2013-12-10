package com.htdz.utms.service.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.htdz.utms.dao.LoginDao;
import com.htdz.utms.model.Student;
import com.htdz.utms.service.LoginService;

/**
 * 登录业务处理类
 * 
 * @author LiShuheng
 * @date 2013-12-07
 * 
 */
@Scope("prototype")
@Service(value = "indexService")
public class LoginServiceImpl implements LoginService {

	@Resource
	private LoginDao loginDao;

	/**
	 * 根据用户名密码获取学生信息
	 * 
	 * @param stu
	 * @return
	 * @throws Exception
	 */
	public Student findStuInfo(Student stu) throws Exception {
		return loginDao.findStuInfo(stu);
	}
}
