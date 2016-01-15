package com.fantasyworks.util;

import java.io.IOException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class DownloadUtil {
	
	public static String downloadPage(String urlStr){
		try{
			URL url = new URL(urlStr);
			return downloadPage(url);
		}
		catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public static String downloadPage(URL url){
		try{
			String content = Resources.asByteSource(url).asCharSource(Charsets.UTF_8).read();
			return content;
		}
		catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}
	
}
