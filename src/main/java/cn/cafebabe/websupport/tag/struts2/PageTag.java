package cn.cafebabe.websupport.tag.struts2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class PageTag extends ComponentTagSupport
{
	private static final long serialVersionUID = 5747800053845348766L;
	private String url = null, value = null, name = null, divId = null,
			spanClass = null, currentPageValueName = "currentPage",
			currentPagePropertyName = currentPageValueName,
			pageNumberValueName = "pageNumber",
			pageNumberPropertyName = pageNumberValueName;;
	private Boolean useSingleProperty = null;
	private Integer displayCount = null;
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res)
	{
		return new PageComponent(stack);
	}
	
	@Override
	protected void populateParams()
	{
		super.populateParams();
		PageComponent pageComponent = (PageComponent) super.getComponent();
		pageComponent.setUrl(url);
		pageComponent.setName(name);
		pageComponent.setValue(value);
		pageComponent.setDivId(divId);
		pageComponent.setSpanClass(spanClass);
		pageComponent.setDisplayCount(displayCount);
		pageComponent.setUseSingleProperty(useSingleProperty);
		pageComponent.setCurrentPagePropertyName(currentPagePropertyName);
		pageComponent.setCurrentPageValueName(currentPageValueName);
		pageComponent.setPageNumberPropertyName(pageNumberPropertyName);
		pageComponent.setPageNumberValueName(pageNumberValueName);
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setDivId(String divId)
	{
		this.divId = divId;
	}
	
	public void setSpanClass(String spanClass)
	{
		this.spanClass = spanClass;
	}
	
	public void setDisplayCount(Integer displayCount)
	{
		this.displayCount = displayCount;
	}
	
	public void setUseSingleProperty(Boolean useSingleProperty)
	{
		this.useSingleProperty = useSingleProperty;
	}
	
	public void setCurrentPageValueName(String currentPageValueName)
	{
		this.currentPageValueName = currentPageValueName;
	}
	
	public void setCurrentPagePropertyName(String currentPagePropertyName)
	{
		this.currentPagePropertyName = currentPagePropertyName;
	}
	
	public void setPageNumberValueName(String pageNumberValueName)
	{
		this.pageNumberValueName = pageNumberValueName;
	}
	
	public void setPageNumberPropertyName(String pageNumberPropertyName)
	{
		this.pageNumberPropertyName = pageNumberPropertyName;
	}
}
