package cn.cafebabe.websupport.tag.struts2;

import java.io.IOException;
import java.io.Writer;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class PageComponent extends Component
{
	private String url = null, divId = null, spanClass = null, value = "page",
			name = value, currentPageValueName = "currentPage",
			currentPagePropertyName = currentPageValueName,
			pageNumberValueName = "pageNumber",
			pageNumberPropertyName = pageNumberValueName;
	private boolean useSingleProperty = true;
	private Integer displayCount = 5;
	
	public PageComponent(ValueStack stack)
	{
		super(stack);
	}
	
	@Override
	public boolean start(Writer writer)
	{
		if(!super.start(writer))
		{
			return false;
		}
		Integer currentPage = (Integer) stack.findValue(value + "."
				+ currentPageValueName), pageNumber = (Integer) stack
				.findValue(value + "." + pageNumberValueName);
		if(currentPage == null || pageNumber == null)
		{
			throw new RuntimeException("currentPage或pageNumber的值不能获取,估计page对象获取失败,请检查代码");
		}
		if(pageNumber == 0)
		{
			return true;
		}
		try
		{
			StringBuilder middlePage = new StringBuilder();
			String frontPage = when(!currentPage.equals(1),
					span(a("上一页", currentPage - 1, pageNumber)));
			String behindPage = when(!currentPage.equals(pageNumber),
					span(a("下一页", currentPage + 1, pageNumber)));
			if(pageNumber > displayCount)
			{
				int frontCount = 0, behindCount = 0;
				if(currentPage > displayCount / 2)
				{
					frontCount = displayCount / 2;
				}
				else
				{
					frontCount = currentPage - 1;
					behindCount += displayCount / 2 - frontCount;
				}
				if(currentPage + displayCount / 2 <= pageNumber)
				{
					behindCount += displayCount / 2;
				}
				else
				{
					behindCount = pageNumber - currentPage;
					frontCount += displayCount / 2 - behindCount;
				}
				for(int i = frontCount; i != 0; i --)
				{
					middlePage.append(span(a(String.valueOf(currentPage - i),
							currentPage - i, pageNumber)));
				}
				middlePage.append(span(String.valueOf(currentPage)));
				for(int i = 1; i != behindCount + 1; i ++)
				{
					middlePage.append(span(a(String.valueOf(currentPage + i),
							currentPage + i, pageNumber)));
				}
				String firstPage = when((currentPage - frontCount) > 1,
						span(a("1...", 1, pageNumber)));
				String lastPage = when(
						(currentPage + behindCount) < pageNumber,
						span(a("..." + pageNumber, pageNumber, pageNumber)));
				writer.write(div(frontPage + firstPage + middlePage.toString()
						+ lastPage + behindPage));
				return true;
			}
			else
			{
				for(int i = 1; i < pageNumber + 1; i ++)
				{
					middlePage.append(span(currentPage.equals(i) ? String
							.valueOf(i) : a(String.valueOf(i), i, pageNumber)));
				}
				writer.write(div(frontPage + middlePage.toString() + behindPage));
				return true;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	private String when(boolean expression, String text)
	{
		if(expression)
		{
			return text;
		}
		else
		{
			return "";
		}
	}
	
	private String div(String text)
	{
		StringBuilder div = new StringBuilder("<div");
		if(isNotEmpty(divId))
		{
			div.append(" id='" + divId + "'");
		}
		div.append(">");
		if(text != null)
		{
			div.append(text);
		}
		return div.append("</div>").toString();
	}
	
	private String a(String text, int currentPage, int pageNumber)
	{
		StringBuilder a = new StringBuilder("<a href='");
		a.append(findString(url));
		if(url.indexOf('?') == -1)
		{
			a.append("?");
		}
		else
		{
			a.append("&");
		}
		a.append(propertyName()).append(currentPagePropertyName + "=")
				.append(currentPage).append("&").append(propertyName())
				.append(pageNumberPropertyName + "=").append(pageNumber)
				.append("'>");
		if(text != null)
		{
			a.append(text);
		}
		return a.append("</a>").toString();
	}
	
	private StringBuilder propertyName()
	{
		StringBuilder text = new StringBuilder();
		if(useSingleProperty)
		{
			text.append(name).append(".");
		}
		return text;
	}
	
	private String span(String text)
	{
		StringBuilder span = new StringBuilder("&nbsp;<span");
		if(isNotEmpty(spanClass))
		{
			span.append(" class='").append(spanClass).append("'");
		}
		span.append(">");
		if(text != null)
		{
			span.append(text);
		}
		return span.append("</span>&nbsp;").toString();
	}
	
	private boolean isNotEmpty(String property)
	{
		return property != null && !property.isEmpty();
	}
	
	public void setDivId(String divId)
	{
		this.divId = trim(divId);
	}
	
	private String trim(String text)
	{
		return text != null ? text.trim() : null;
	}
	
	public void setUrl(String url)
	{
		this.url = trim(url);
	}
	
	public void setSpanClass(String spanClass)
	{
		this.spanClass = trim(spanClass);
	}
	
	public void setValue(String value)
	{
		if(isNotEmpty(value))
		{
			this.value = value.trim();
		}
	}
	
	public void setName(String name)
	{
		if(isNotEmpty(name))
		{
			this.name = name.trim();
		}
	}
	
	public void setDisplayCount(Integer displayCount)
	{
		if(displayCount != null && displayCount > 0)
		{
			this.displayCount = displayCount;
		}
	}
	
	public void setUseSingleProperty(Boolean useSingleProperty)
	{
		if(useSingleProperty != null)
		{
			this.useSingleProperty = useSingleProperty;
		}
	}
	
	public void setCurrentPageValueName(String currentPageValueName)
	{
		if(isNotEmpty(currentPageValueName))
		{
			this.currentPageValueName = currentPageValueName;
		}
	}
	
	public void setPageNumberValueName(String pageNumberValueName)
	{
		if(isNotEmpty(pageNumberValueName))
		{
			this.pageNumberValueName = pageNumberValueName;
		}
	}
	
	public void setCurrentPagePropertyName(String currentPagePropertyName)
	{
		if(isNotEmpty(currentPagePropertyName))
		{
			this.currentPagePropertyName = currentPagePropertyName;
		}
	}
	
	public void setPageNumberPropertyName(String pageNumberPropertyName)
	{
		if(isNotEmpty(pageNumberPropertyName))
		{
			this.pageNumberPropertyName = pageNumberPropertyName;
		}
	}
}
