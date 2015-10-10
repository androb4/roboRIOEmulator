package com.androb4.roborioemulator.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RobotClassReader {
	InputStream inputStream;
 
	public static String getRobotClass() {
		String result = null;
		FileInputStream input = null;
		Properties prop = new Properties();
		try {
			input = new FileInputStream("build.properties");
			prop.load(input);
			String robotPackage = prop.getProperty("package");
			result = prop.getProperty("robot.class").replace("${package}", robotPackage).toString();

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
