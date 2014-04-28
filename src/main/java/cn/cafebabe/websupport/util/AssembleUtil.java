package cn.cafebabe.websupport.util;

import sun.tools.asm.Assembler;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * TODO
 *
 * @author linjiangsheng
 * @created 14-3-5
 */
public class AssembleUtil
{

	public static <T> T assemble(Object fromObj,
	                             T toObj,
	                             String... excludeFields)
	{
		if (fromObj == null)
		{
			return toObj;
		}

		Method[] toMethods = toObj.getClass().getMethods();
		Method[] fromMethods = fromObj.getClass().getMethods();
		Map<String, Method> fromMethodMap = new HashMap<String, Method>();
		for (int i = 0; i < fromMethods.length; i++)
			fromMethodMap.put(fromMethods[i].getName(), fromMethods[i]);

		String regex = "";
		for (int i = 0; i < excludeFields.length; i++)
		{
			regex = regex + excludeFields[i];
			if (i < excludeFields.length - 1)
				regex = regex + "|";
		}
		for (int i = 0; i < toMethods.length; i++)
		{
			Method m = toMethods[i];
			String mName = m.getName();
			try
			{
				if (mName.startsWith("set") && !mName.equals("set"))
				{
					Type paramType = m.getGenericParameterTypes()[0];
					String fName = mName.replaceFirst("set", "");
					fName = fName.replaceFirst(fName.substring(0, 1), fName.substring(0, 1)
							.toLowerCase());
					if (!fName.matches(regex))
					{
						Method fm = fromMethodMap.get(mName.replaceFirst("set", "get"));
						if (fm != null)
						{
							if (fm.getReturnType().equals(paramType))
							{
								m.invoke(toObj, fm.invoke(fromObj));
							}
						}
					}
				} else if (mName.equals("assembleTrigger"))
				{
					m.invoke(toObj);
				}
			} catch (Exception e)
			{
				System.out.println(Assembler.class + " error!");
				e.printStackTrace();
			}
		}
		return toObj;
	}

	/**
	 * 动态组装对象,源对象为空(null)的值忽略,可以触发toObject的名称为
	 * assembleTrigger的方法
	 *
	 * @param fromObj       源对象
	 * @param toObj         目标对象
	 * @param excludeFields toObj中不组装的属性.不跟属性直接打交道.只和get,set方法的名称直接关联
	 * @return
	 * @author zhaolei
	 * @created 2011-4-28
	 */
	public static <T> T assembleNotNull(Object fromObj,
	                                    T toObj,
	                                    String... excludeFields)
	{
		if (fromObj == null)
		{
			return toObj;
		}

		Method[] toMethods = toObj.getClass().getMethods();
		Method[] fromMethods = fromObj.getClass().getMethods();
		Map<String, Method> fromMethodMap = new HashMap<String, Method>();
		for (int i = 0; i < fromMethods.length; i++)
			fromMethodMap.put(fromMethods[i].getName(), fromMethods[i]);
		String regex = "";
		for (int i = 0; i < excludeFields.length; i++)
		{
			regex = regex + excludeFields[i];
			if (i < excludeFields.length - 1)
				regex = regex + "|";
		}
		for (int i = 0; i < toMethods.length; i++)
		{
			Method m = toMethods[i];
			String mName = m.getName();

			try
			{
				if (mName.startsWith("set") && !mName.equals("set"))
				{
					Type paramType = m.getGenericParameterTypes()[0];
					String fName = mName.replaceFirst("set", "");
					fName = fName.replaceFirst(fName.substring(0, 1), fName.substring(0, 1)
							.toLowerCase());
					if (!fName.matches(regex))
					{
						Method fm = fromMethodMap.get(mName.replaceFirst("set", "get"));
						if (fm != null)
						{
							if (fm.getReturnType().equals(paramType))
							{
								Object value = fm.invoke(fromObj);
								if (value != null)
									m.invoke(toObj, value);
							}
						}
					}
				} else if (mName.equals("assembleTrigger"))
				{
					m.invoke(toObj);
				}
			} catch (Exception e)
			{
				System.out.println(Assembler.class + " error!");
				e.printStackTrace();
			}
		}
		return toObj;
	}

	/**
	 * 从源List装配一个符合目标class类型的List.可以触发toObject的名称为
	 * assembleTrigger的方法
	 *
	 * @param fromList      源list
	 * @param toClass       目标class
	 * @param excludeFields 不装配的属性名称
	 * @return
	 * @author zhaolei
	 * @created 2011-4-28
	 */
	public static <T> List<T> assembleList2NewList(List<?> fromList,
	                                               Class<T> toClass,
	                                               String... excludeFields)
	{
		List<T> toList = new ArrayList<T>(fromList.size());
		try
		{
			for (Iterator<?> iterator = fromList.iterator(); iterator.hasNext(); )
			{
				Object fromObject = iterator.next();
				T toObject = toClass.newInstance();
				if (excludeFields.length > 0)
					toObject = assemble(fromObject, toObject, excludeFields);
				else
					toObject = assemble(fromObject, toObject);
				toList.add(toObject);
			}
		} catch (Exception e)
		{
			System.out.println(Assembler.class + " error!");
			e.printStackTrace();
		}
		return toList;
	}

	/**
	 * 从源List装配一个符合目标class类型的List,可以触发toObject的名称为
	 * assembleTrigger的方法
	 *
	 * @param fromList      源List
	 * @param toList        目标List
	 * @param excludeFields 不需要装配的属性
	 * @return
	 * @author zhaolei
	 * @created 2011-4-28
	 */
	public static <T> List<T> assembleList2List(List<?> fromList,
	                                            List<T> toList,
	                                            Class<T> toClass,
	                                            String... excludeFields)
	{
		try
		{
			for (int i = 0; i < fromList.size(); i++)
			{
				Object fromObject = fromList.get(i);
				T toObject = i >= toList.size() ? toClass.newInstance() : toList.get(i);
				if (excludeFields.length > 0)
					toObject = assemble(fromObject, toObject, excludeFields);
				else
					toObject = assemble(fromObject, toObject);
				toList.add(toObject);
			}
		} catch (Exception e)
		{
			System.out.println(Assembler.class + " error!");
			e.printStackTrace();
		}
		return toList;
	}
}
