/**
 * 定义系统常量
 * @author 王美蓉
 * @since 1.00
 * @version 1.00 
 * 
 * 修订列表： v1.00 2012-12-4
 * 			修订人：王美蓉
 * 			修订内容：初始版本
 */
package com.htdz.utms.common.util;

import java.util.Calendar;

public class MyConstant {
	
	public static final String TRUE = "1";// 是
	public static final String FALSE = "0";// 否
	
	public static final String SMS_UP = "提升";
	public static final String SMS_DOWN = "下降";
	
	// 排序方式
	public static final String ORDER_ASC = "asc"; // 升序
	public static final String ORDER_DESC = "desc"; // 降序

	public static final int FIRSTDAY_WEEK = Calendar.SUNDAY;// 每周首日(缺省值为周日)
	
	public static final int INT_NULL = -1;// 当字符串等对象转化为整型数字时，出现异常时的整型数值
	public static final double DOUBLE_NULL = 0.0;// 当字符串等对象转化为double数字时，出现异常时的double数值
	public static final String SELECT_FIRST_VALUE = "-1";// 下拉框控件首个option的缺省value为-1
	public static final String SELECT_FIRST_OPTION = "全部";// 下拉框控件首个option
	public static final String SELECT_FIRST_OPTION_NO = "-999";// 下拉框控件无默认首个option(即没有"全部"或"请选择")
	public static final String SELECT_FIRST_OPTION_YES_OR_NO_ALL = "-998";// 下拉框控件首个option：当map值只有一个时，则不显示"全部"或"请选择"；如果map值为空或超过1个时，则显示"全部"
	public static final String SELECT_FIRST_OPTION_YES_OR_NO_SELECT = "-997";// 下拉框控件首个option：当map值只有一个时，则不显示"全部"或"请选择"；如果map值为空或超过1个时，则显示"请选择"

	public static final String DWZ_SELECT_FIRST_OPTION_NO = "no";//DWZ下拉框控件首个option不指定
	public static final String DWZ_SELECT_FIRST_OPTION_ALL = "all";//DWZ下拉框控件首个option显示"全部"
	public static final String DWZ_SELECT_FIRST_OPTION_SELECT = "select";//DWZ下拉框控件首个option显示"请选择" 这时候该下拉框自动加上required样式
	public static final String DWZ_SELECT_FIRST_VALUE = "";// 下拉框控件首个option的缺省value为""
	
}
