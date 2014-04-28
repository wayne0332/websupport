package cn.cafebabe.websupport.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;

public final class LoginInterceptor extends BaseInterceptor
{
	private static final long serialVersionUID = 3490468086319440933L;
	private String userKeyName = "user", resultName = ActionSupport.ERROR;
	
	@Override
	public String intercept(ActionInvocation ai) throws Exception
	{
		if(super.getSessionMap().get(userKeyName) != null)
		{
			return ai.invoke();
		}
		else
		{
			return resultName;
		}
	}
	
	public void setUserKeyName(String userKeyName)
	{
		this.userKeyName = userKeyName;
	}
	
	public void setResultName(String resultName)
	{
		this.resultName = resultName;
	}
}
