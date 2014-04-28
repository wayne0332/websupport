package cn.cafebabe.websupport.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.log4j.Logger;

public final class FileReader
{
	private final static Logger logger = Logger.getLogger(FileReader.class);
	
	public static BufferedReader getReader(String path) throws IOException
	{
		URL url = FileReader.class.getClassLoader().getResource(path);
		if(url != null)
		{
			logger.debug(path + " : file find!");
			return new BufferedReader(new InputStreamReader(url.openStream()));
		}
		else
		{
			logger.debug(path + " : file not find...");
			return null;
		}
	}
}
