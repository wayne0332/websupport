package cn.cafebabe.websupport.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public final class ConcurrentControlTag extends TagSupport
{
	private static final long serialVersionUID = 4182798238623912617L;
	private String keyName = null, keyValue = null, errorPage = null;
	
	@Override
	public int doStartTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		if(keyName.equals("") || keyValue.equals("") || errorPage.equals(""))
		{
			try
			{
				out.print("<script type='text/javascript'>alert('concurrentControl标签有参数没填!');</script>");
			}
			catch(IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				out.print(new StringBuilder()
						.append("<script type='text/javascript' src='")
						.append(((HttpServletRequest) this.pageContext
								.getRequest()).getContextPath())
						.append("/devil/js/ConcurrentControl.js'></script><script type='text/javascript'>")
						.append("start('").append(keyName).append("', '")
						.append(keyValue).append("', '").append(errorPage).append("', '").append(super.pageContext.getServletContext())
						.append("');</script>"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return super.doStartTag();
	}
	
	public void setKeyName(String keyName)
	{
		this.keyName = keyName;
	}
	
	public void setKeyValue(String keyValue)
	{
		this.keyValue = keyValue;
	}
	
	public void setErrorPage(String errorPage)
	{
		this.errorPage = errorPage;
	}
}
