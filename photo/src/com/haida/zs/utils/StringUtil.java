package com.haida.zs.utils;

public class StringUtil {
//  判断字符串否为空
	public static boolean isNotBlank(String str){
		if (null != str && !"".equals(str)){
			return true;
		}
		
		return false;
	}
	
}
