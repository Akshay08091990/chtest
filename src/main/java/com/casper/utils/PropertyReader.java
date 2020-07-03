package com.casper.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertyReader {
	Properties properties;
	@SuppressWarnings("rawtypes")
	static HashMap thisPR;

	public PropertyReader(String fileName) {
		properties = new Properties();
		try {
			properties.load(PropertyReader.class.getResourceAsStream(fileName));
		} catch (Exception e) {
			System.out.println("Exception in loading Property Finder =>" + fileName + "===>" + e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PropertyReader getPropertyReader(String fileName) {
		PropertyReader pr;
		if (thisPR == null) {
			thisPR = new HashMap();
			pr = new PropertyReader(fileName);
			thisPR.put(fileName, pr);
			return pr;
		} else {
			if ((pr = (PropertyReader) thisPR.get(fileName)) == null) {
				pr = new PropertyReader(fileName);
				thisPR.put(fileName, pr);
				return pr;
			} else
				return pr;
		}
	}

	public String getProperty(String key) {
		String retString;
		retString = properties.getProperty(key);
		if (retString != null)
			return retString;
		else
			return "";
	}

	public void setProperty(String key, String value, String fileName) throws IOException {

		// FileOutputStream out = new
		// FileOutputStream("src/main/resources/"+fileName+"");
		properties.setProperty(key, value);
//		properties.store(out,null);
//		out.close();

	}



}
