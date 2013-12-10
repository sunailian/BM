<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
日历控件
<input type="text" name="xxx" class="date" [pattern="yyyy-MM-dd"] [yearstart="-80"] [yearend="5"]/>

日期格式：
 * Field        | Full Form          | Short Form
 * -------------+--------------------+-----------------------
 * Year         | yyyy (4 digits)    | yy (2 digits), y (2 or 4 digits)
 * Month        | MMM (name or abbr.)| MM (2 digits), M (1 or 2 digits)
 *              | NNN (abbr.)        |
 * Day of Month | dd (2 digits)      | d (1 or 2 digits)
 * Day of Week  | EE (name)          | E (abbr)
 * Hour (1-12)  | hh (2 digits)      | h (1 or 2 digits)
 * Hour (0-23)  | HH (2 digits)      | H (1 or 2 digits)
 * Hour (0-11)  | KK (2 digits)      | K (1 or 2 digits)
 * Hour (1-24)  | kk (2 digits)      | k (1 or 2 digits)
 * Minute       | mm (2 digits)      | m (1 or 2 digits)
 * Second       | ss (2 digits)      | s (1 or 2 digits)
 * AM/PM        | a                  |

示例：
<p>
      <label>默认格式：</label>
      <input type="text" name="date1" class="date" />
      <a class="inputDateButton" href="#">选择</a>
</p>
<p>
      <label>定义年份：</label>
      <input type="text" name="date2" class="date" yearstart="-80" yearend="5"/>
      <a class="inputDateButton" href="#">选择</a>
</p>
<p>
      <label>自定义日期格式：</label>
      <input type="text" name="date3" class="date" pattern="yyyy/MM/dd" />
      <a class="inputDateButton" href="#">选择</a>
</p>
<p>
      <label>自定义日期格式：</label>
      <input type="text" name="date5" class="date" pattern="dd/MM/yy" />
      <a class="inputDateButton" href="#">选择</a>
</p>
<p>
      <label>自定义日期+时间：</label>
      <input type="text" name="date7" class="date" pattern="yyyy年MM月dd日 HH:mm:ss" />
      <a class="inputDateButton" href="#">选择</a>
</p>
<p>
      <label>自定义日期+时间：</label>
      <input type="text" name="date8" class="date" pattern="yyyy年MM月dd日 HH:mm" />
      <a class="inputDateButton" href="#">选择</a>
</p>
<p>
      <label>自定义日期+时间：</label>
      <input type="text" name="date9" class="date" pattern="y年M月d日 HH点" />
      <a class="inputDateButton" href="#">选择</a>
</p>
<%--
    定义日期范围属性minDate,maxDate静态格式y-M-d或y-M或y，支持以下几种写法:
	minDate="2000-01-15" maxDate="2012-12-15"
	minDate="2000-01" maxDate="2012-12"
	minDate="2000" maxDate="2012"
	
	定义日期范围属性minDate,maxDate动态态格式%y-%M-%d或%y-%M或%y，支持以下几种写法:
	minDate="{%y-10}-%M-%d" maxDate="{%y}-%M-{%d+1}"
	minDate="{%y-10}-%M" maxDate="{%y+10}-%M"
	minDate="{%y-10}" maxDate="{%y+10}"
--%>