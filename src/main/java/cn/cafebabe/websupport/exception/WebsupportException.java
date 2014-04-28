package cn.cafebabe.websupport.exception;

public class WebsupportException extends RuntimeException
{
	private static final long serialVersionUID = -2850486917406057246L;
	
	public WebsupportException()
	{
		super();
	}
	
	public WebsupportException(String message)
	{
		super(message);
	}
	
	public WebsupportException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
