package com.htdz.utms.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.htdz.utms.model.Student;
import com.htdz.utms.model.User;
import com.htdz.utms.service.LoginService;

/**
 * 登录
 * 
 * @author LiShuheng
 */
@Namespace("")
public class LoginAction extends BaseAction {

	@Resource
	private LoginService loginService;

	static Logger log = Logger.getLogger(LoginAction.class);

	private Student student;

	@Action(value = "loginPage", results = { @Result(name = "success", location = "/DWZ/login.jsp") })
	public String loginPage() {
		return SUCCESS;
	}

	/**
	 * 学生登录系统
	 * @return
	 * @throws Exception
	 */
	@Action(value = "supportLogin", results = {
			@Result(name = "success", location = "/DWZ/admin.jsp"),
			@Result(name = "failure", location = "/DWZ/login.jsp") })
	public String login() throws Exception {

		if (student == null) {
			message = "用户名或密码不正确";
			return FAILURE;
		} else if (student.getStudentno() == null
				|| student.getPassword1() == null) {
			message = "用户名或密码不能为空";
			return FAILURE;
		} else {
			student = loginService.findStuInfo(student);
			if (student == null) {
				message = "用户名或密码不正确";
				return FAILURE;
			}
		}
		return SUCCESS;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
