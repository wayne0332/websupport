package cn.cafebabe.websupport.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.cafebabe.websupport.util.FileReader;



public final class DevilFilter extends BaseFilter
{
	private final static Logger logger = Logger.getLogger(DevilFilter.class);
	
	@Override
	public void destroy()
	{}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{}
	
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		if(isFileRequest(request))
		{
			logger.debug("execute file!");
			returnFile(request, response);
		}
		else
		{
			logger.debug("execute action!");
			executeAction(request, response);
		}
	}
	
	private void executeAction(HttpServletRequest request,
			HttpServletResponse response)
	{
		try
		{
			Class<?> actionClass = Class.forName(getClassName(getPath(request
					.getServletPath())));
			actionClass.getMethod("execute", HttpServletRequest.class,
					HttpServletResponse.class).invoke(null, request, response);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch(InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchMethodException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean isFileRequest(HttpServletRequest request)
	{
		return request.getServletPath().split("\\.").length > 1 ? true : false;
	}
	
	private void returnFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		BufferedReader reader = FileReader.getReader(getPath(request
				.getServletPath()));
		PrintWriter writer = response.getWriter();
		response.setContentType("text/javascript; charset=UTF-8");
		String line = null;
		while((line = reader.readLine()) != null)
		{
			writer.println(line);
		}
		reader.close();
		writer.close();
	}
	
	private String getPath(String servletPath)
	{
		return servletPath.split("/", 2)[1];
	}
	
	private String getClassName(String path)
	{
		return path.replaceAll("/", "\\.");
	}
}
