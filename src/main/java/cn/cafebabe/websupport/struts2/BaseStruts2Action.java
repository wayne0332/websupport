package cn.cafebabe.websupport.struts2;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/** 有空将三个对象变成属性 */
public abstract class BaseStruts2Action extends ActionSupport
{
	private static final long serialVersionUID = 6894946736439435422L;
	protected final static String SECRET_PAGE_PATH = "/WEB-INF/web/",
			REDIRECT_ACTION = "redirectAction", CHAIN = "chain",
			REDIRECT = "redirect", INVALID_TOKEN = "invalid.token",
			JSP = ".jsp";
	public final static String SUCCESS = ActionSupport.SUCCESS,
			ERROR = ActionSupport.ERROR;
	
	protected BaseStruts2Action()
	{
		super();
	}
	
	protected String successOrInput(boolean expression)
	{
		if(expression)
		{
			return SUCCESS;
		}
		else
		{
			return INPUT;
		}
	}
	
	protected String successOrError(boolean expression)
	{
		if(expression)
		{
			return SUCCESS;
		}
		else
		{
			return ERROR;
		}
	}
	
	protected static Map<String, Object> getSessionMap()
	{
		return ActionContext.getContext().getSession();
	}
	
	protected static Map<String, Object> getApplicationMap()
	{
		return ActionContext.getContext().getApplication();
	}
	
	@SuppressWarnings("unchecked")
	protected static Map<String, Object> getRequestMap()
	{
		return (Map<String, Object>) ActionContext.getContext().get("request");
	}
}
