package com.fantasyworks.util;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class IOUtil {
	
	public static void writeToFile(String fileName, String content){
		writeToFile(new File(fileName), content);
	}
	
	public static void writeToFile(File file, String content){
		try{
			Files.write(content, file, Charsets.UTF_8);
		}
		catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public static String readFileToString(String fileName){
		return readFileToString(new File(fileName));
	}
	
	public static String readFileToString(File file){
		try{
			String result = Files.toString(file, Charsets.UTF_8);
			return result;
		}
		catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public static boolean isExistingFile(String fileName){
		File file = new File(fileName);
		return file.exists();
	}
}
