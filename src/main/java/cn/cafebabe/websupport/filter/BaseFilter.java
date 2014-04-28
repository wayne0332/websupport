package cn.cafebabe.websupport.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseFilter implements Filter
{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		execute((HttpServletRequest) request, (HttpServletResponse) response,
				chain);
	}
	
	public abstract void execute(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws IOException, ServletException;
}
