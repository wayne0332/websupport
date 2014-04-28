package cn.cafebabe.websupport.hibernate;

import org.hibernate.usertype.UserType;

/**
 * TODO
 *
 * @author linjiangsheng
 * @created 14-4-14
 */
public interface CustomType extends UserType
{
	public String[] getMapTypes();
}
