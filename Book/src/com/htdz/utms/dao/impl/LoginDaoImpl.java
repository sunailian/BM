package com.htdz.utms.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.htdz.utms.dao.BaseDao;
import com.htdz.utms.dao.LoginDao;
import com.htdz.utms.model.Student;

@Repository
public class LoginDaoImpl extends BaseDao implements LoginDao {

    /**
     * 根据用户名密码获取学生信息
     * @author LiShuheng
     * @date 2013-12-07
     */
	public Student findStuInfo(Student stu) throws SQLException {
		return (Student) this.queryForObject("common.findStuInfo", stu);
	}
	
}
