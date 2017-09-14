package com.haida.zs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
// 日期格式化工具类（为什么用static？优点：编译一次，它属于类的，可以不用new对象操作）
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String dateTimeFormat(Date date,String formatter){
		if (StringUtil.isNotBlank(formatter)){
			sdf = new SimpleDateFormat(formatter);
			
			return sdf.format(date);
		}
		
		return sdf.format(date);
	}
	
	public static String dateTimeFormat(Date date){
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	
	
}
