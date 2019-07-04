package com.yuanhy.appframework.text;


import android.text.format.DateUtils;

import com.yuanhy.library_tools.util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Textjava {

	public static void main(String[] strings) {
		String strTime = "2019-06-16 18:22";
		SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
		Date	date = new Date();
			System.out.println("字符串3：" + TimeUtil.isDifferNumberDays(strTime,simpleDateFormat.format(date),3));
if (1<2){
	System.out.println("222222222"  );
}else if (1<3){
	System.out.println("33333333333333"  );
}
	}

}
