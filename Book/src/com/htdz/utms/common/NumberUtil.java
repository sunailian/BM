package com.htdz.utms.common;

import com.htdz.utms.common.util.MyUtil;

public class NumberUtil {
	
	/**
	 * 字符转int
	 * @author tcl [Dec 20, 2012]
	 * @param num
	 * @return
	 */
 public static int fromStr2Int(String num) {
	 if(MyUtil.isStrNull(num)){
	 return 0;
	  }
	 return Integer.valueOf(num);
}
   /**
    * long转int
    * @author tcl [Dec 20, 2012]
    * @param num
    * @return
    */
 public static int fromLong2Int(long num) {
	  
	 return  (int)num;
}
 
 /**
  * Long转String
  * @author tcl [Dec 20, 2012]
  * @param num
  * @return
  */
 public static String fromLong2String(Long num) {
	 return  String.valueOf(num);
}
 
 /**
  * String转Long
  * @author tcl [Dec 20, 2012]
  * @param num
  * @return
  */
 public static Long fromString2Long(String num) {
	 return  Long.valueOf(num);
}
 
 /**
  * String转Long
  * @author tcl [Dec 20, 2012]
  * @param num
  * @return
  */
 public static String fromInt2String(Integer num) {
	 return  String.valueOf(num);
}
 
 
}
