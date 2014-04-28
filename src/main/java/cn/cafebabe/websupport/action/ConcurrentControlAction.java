package cn.cafebabe.websupport.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public final class ConcurrentControlAction
{
	private final static Logger logger = Logger.getLogger(ConcurrentControlAction.class);
	private static Map<String, String> application = new HashMap<String, String>();
	private final static String KEY_NAME = "keyName", KEY_VALUE = "keyValue";
	private final static long FIVE_MINUTE = 1000 * 60 * 4;
	private static long weitTime = FIVE_MINUTE;
	private static String keyName = null;
	private static long flushTime = 0;
	
	private ConcurrentControlAction()
	{}
	
	public synchronized static void execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		flushTime();
		boolean isRedirect = false;
		if(getKey(keyName = request.getParameter(KEY_NAME)) == null)
		{
			logger.debug("new permission");
			setKey(keyName, request.getParameter(KEY_VALUE));
			new Permission().start();
		}
		else if(!getKey(keyName).equals(
				request.getParameter(KEY_VALUE)))
		{
			logger.info("can not enter");
			isRedirect = true;
		}
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<?xml version='1.0' encoding='utf-8'?>");
		out.print("<isRedirect>");
		out.print(isRedirect);
		out.print("</isRedirect>");
	}
	
	private static class Permission extends Thread
	{
		@Override
		public void run()
		{
			boolean isContinue = true;
			while(isContinue)
			{
				try
				{
					sleep(weitTime / 2);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				if(flushTime < System.currentTimeMillis() - weitTime)
				{
					logger.debug("permission out of time");
					setKey(keyName, null);
					isContinue = false;
				}
			}
		}
	}
	
	private static void flushTime()
	{
		logger.info("flush time!");
		flushTime = System.currentTimeMillis();
	}
	
	private static String getKey(String keyName)
	{
		return application.get(keyName);
	}
	
	private static void setKey(String keyName, String keyValue)
	{
		application.put(keyName, keyValue);
	}
}
