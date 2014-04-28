package cn.cafebabe.websupport.tag;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public final class ParameterTag extends TagSupport
{
	private static final long serialVersionUID = 3893078675967439873L;
	private String value = null;
	
	@Override
	public int doStartTag() throws JspException
	{
		String oldString = pageContext.getRequest().getParameter(value);
		if(oldString != null)
		{
			try
			{
				pageContext.getOut().print(
						new String(oldString.getBytes("ISO8859-1"), "UTF-8")
								.toString());
			}
			catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return super.doStartTag();
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
}
