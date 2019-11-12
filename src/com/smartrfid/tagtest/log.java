package com.smartrfid.tagtest;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;


public class log {
    static String jarPath = log.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    public static void WriteLogString(String args) {
    	OutputStreamWriter os;
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.MONTH, 1);
		String timeStr = String.format("%d.%d.%d %d:%d:%d",calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),
				calendar.get(Calendar.YEAR),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));
		try {
			os = new OutputStreamWriter(new FileOutputStream(jarPath + "/log.txt",true),Charset.forName("UTF-8").newEncoder());
			os.append(timeStr);
			os.append(" ");
			os.append(args);
			os.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
    public static void CreateLogFile() {
    	try {
		FileOutputStream fos = new FileOutputStream(jarPath + "/log.txt",false);
		fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
