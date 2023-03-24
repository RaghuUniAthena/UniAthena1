package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropFile {

	Properties properties;
	FileInputStream fStream;

	public Properties getPropData() {
		String propFilePath = System.getProperty("user.dir");
		try{fStream = new FileInputStream(propFilePath + "\\resources\\data.properties");
		properties = new Properties();
		properties.load(fStream);}catch(Exception e) {}
		return properties;
	}
	public Properties getDBData() {
		String propFilePath = System.getProperty("user.dir");
		try{fStream = new FileInputStream(propFilePath + "\\resources\\db.properties");
		properties = new Properties();
		properties.load(fStream);}catch(Exception e) {}
		return properties;
	}
	public Properties getQuery() {
		String propFilePath = System.getProperty("user.dir");
		try{fStream = new FileInputStream(propFilePath + "\\resources\\queries.properties");
		properties = new Properties();
		properties.load(fStream);}catch(Exception e) {}
		return properties;
	}

}
