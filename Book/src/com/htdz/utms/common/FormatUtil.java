package com.htdz.utms.common;

import java.text.DecimalFormat;

public class FormatUtil {

	/**
	 * 规格化金额，返回带两位小数金额
	 * 
	 * @param d
	 *            金额
	 * @param containComma
	 *            返回的金额是否包含逗号
	 * @return 10000.00/10,000.00
	 */
	public final static String getCustomNum(double d, int decimalSize,
			boolean containComma) {
		String zeroStr = "";
		for (int i = 0; i < decimalSize; i++) {
			zeroStr = zeroStr + "0";
		}
		if (containComma) {
			return new DecimalFormat("#,##0." + zeroStr + ";-#,##0." + zeroStr)
					.format(d);
		} else {
			return new DecimalFormat("0." + zeroStr + ";-0." + zeroStr)
					.format(d);
		}

	}

	/**
	 * 输入数字，输出长度为n的字符串型数字（高位补0）
	 * 
	 * @param x
	 *            数字
	 * @return 高位带0的字符串型数字
	 */
	public final static String getFixedNumber(long x, int n) {
		String xx = String.valueOf(x);
		int length = xx.length();
		StringBuilder s = new StringBuilder();
		for (int i = length; i < n; i++) {
			s.append("0");
		}
		s.append(xx);
		return s.toString();
	}

	/**
	 * 得到大写人民币
	 */
	public final static String getSuperRMB(String money) {
		//ChangeRMB rmb = new ChangeRMB();
		//return rmb.cleanZero(rmb.splitNum(rmb.roundString(money)));
		return money;
	}
	/**
	 * 转换成指定字符串长度，前补"0"
	 *
	 * @author wzx
	 * @Date Oct 9, 2011
	 * @param strin
	 * @param nLength
	 * @return
	 */
	public final static String SupplyLength(String str,int Length)
    {
	    String strReturnValue="";
	    int num;
	
	    num = Length - str.length ();
	    strReturnValue = str;
	
	    for (int i = 1; i <= num; i++){
	      strReturnValue = "0" + strReturnValue;
	    }

    return strReturnValue;
   } 
}
