package cn.cafebabe.websupport.config;

import org.apache.commons.lang.xwork.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.hibernate.internal.util.xml.ErrorLogger;
import org.hibernate.internal.util.xml.XMLHelper;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.util.Iterator;

/**
 * TODO
 *
 * @author linjiangsheng
 * @created 14-3-4
 */
public class HibernateDataSource extends DriverManagerDataSource
{
	public void setConfigLocation(Resource resource)
	{
		XMLHelper xmlHelper = new XMLHelper();
		try
		{
			Document document = xmlHelper.createSAXReader(new ErrorLogger(resource.getURL().toString()), XMLHelper.DEFAULT_DTD_RESOLVER).read(new InputSource(resource.getURL().openStream()));
			Element element = document.getRootElement().element("session-factory");

			for (java.util.Iterator iterator = element.elementIterator(); iterator.hasNext(); )
			{
				Element next = (Element) iterator.next();
				String name = next.attributeValue("name");
				if(name == null)
				{
					continue;
				}
				switch (name)
				{
					case "connection.url":
						setUrl(StringUtils.trim(next.getData().toString()));
						break;
					case "connection.driver_class":
						setDriverClassName(StringUtils.trim(next.getData().toString()));
						break;
					case "connection.username":
						setUsername(StringUtils.trim(next.getData().toString()));
						break;
					case "connection.password":
						setPassword(StringUtils.trim(next.getData().toString()));
						break;
					default:
						continue;
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}
}
