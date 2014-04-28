package cn.cafebabe.websupport.util;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import cn.cafebabe.autodao.autoQuery.Bean;

/**
 * ʵ��װ�������
 * 
 * @author Sato_Team_4_Devil
 * @param <T>
 *            bean������
 */
public class BeanUtil
{
	/**
	 * �˷������Խ�һ�����������������Զ���䵽��һ�����ͬ��������(������<jsp:userBean>,
	 * ����servlet�����ñ�ǩ)
	 * 
	 * @author Sato_Team_4_Devil
	 * @param source
	 *            �����ԵĶ���,��Ҫ��һ�����Ը������ȡ������ķ���(��HttpServletRequest
	 *            HttpServletResponse��)
	 * @param getObjectMethod
	 *            source����ȡ�����Եķ���,��Ҫһ����Ϊ�������ֵ�String���͵Ĳ���(��
	 *            request.getAttribute
	 *            requset.getParameter��)
	 * @return �����Ѿ��Զ��������Ե�Ŀ�����
	 */
	public static <T> T autoObject(Class<T> clazz, Object source,
			String getObjectMethod)
	{
		T o = null;
		try
		{
			o = clazz.newInstance();
		}
		catch(Exception e)
		{
			throw new RuntimeException(clazz + "对象建立失败!");
		}
		Bean bean = new Bean(o);
		ArrayList<Method> methods = bean.getSetMethods();
		if(methods != null)
		{
			ArrayList<String> propertyNames = bean.getPropertyNames();
			for(int i = 0; i != methods.size(); ++ i)
			{
				String propertyName = propertyNames.get(i);
				Object oBuffer = null;
				try
				{
					// �ж�source�Ƿ��ж�Ӧ���ԵĶ���
					if((oBuffer = source.getClass()
							.getMethod(getObjectMethod, String.class)
							.invoke(source, propertyName)) != null
							&& !"".equals(oBuffer))
					{
						try
						{
							methods.get(i).invoke(o, oBuffer);
						}
						catch(IllegalArgumentException e)
						{
							// ������Զ�������ֵ,���ж��Ƿ�Ϊ�����Ͳ����ԶԻ����ͽ��и�ֵ(��˸�������ʵ��̫������!!!)
							Class<?> parameterType = methods.get(i)
									.getParameterTypes()[0];
							if(methods.get(i).getParameterTypes()[0].isArray()
									&& oBuffer.getClass().isArray())
							{
								String[] stringBuffer = (String[]) oBuffer;
								// �������Ϊ����Ĵ��?��
								if(parameterType.toString().contains("[I"))
								{
									int[] intBuffer = new int[stringBuffer.length];
									for(int j = 0; j != stringBuffer.length; ++ j)
									{
										intBuffer[j] = Integer
												.valueOf(stringBuffer[j]);
									}
									methods.get(i).invoke(o, intBuffer);
								}
								else if(parameterType.toString().contains("[D"))
								{
									double[] intBuffer = new double[stringBuffer.length];
									for(int j = 0; j != stringBuffer.length; ++ j)
									{
										intBuffer[j] = Double
												.valueOf(stringBuffer[j]);
									}
									methods.get(i).invoke(o, intBuffer);
								}
								else if(parameterType.toString().contains("[C"))
								{
									char[] intBuffer = new char[stringBuffer.length];
									for(int j = 0; j != stringBuffer.length; ++ j)
									{
										intBuffer[j] = stringBuffer[j]
												.toCharArray()[0];
									}
									methods.get(i).invoke(o, intBuffer);
								}
								else if(parameterType.toString().contains("[F"))
								{
									float[] intBuffer = new float[stringBuffer.length];
									for(int j = 0; j != stringBuffer.length; ++ j)
									{
										intBuffer[j] = Float
												.valueOf(stringBuffer[j]);
									}
									methods.get(i).invoke(o, intBuffer);
								}
								else if(parameterType.toString().contains("[Z"))
								{
									boolean[] intBuffer = new boolean[stringBuffer.length];
									for(int j = 0; j != stringBuffer.length; ++ j)
									{
										intBuffer[j] = Boolean
												.valueOf(stringBuffer[j]);
									}
									methods.get(i).invoke(o, intBuffer);
								}
								else if(parameterType.toString().contains("[S"))
								{
									short[] intBuffer = new short[stringBuffer.length];
									for(int j = 0; j != stringBuffer.length; ++ j)
									{
										intBuffer[j] = Short
												.valueOf(stringBuffer[j]);
									}
									methods.get(i).invoke(o, intBuffer);
								}
								else if(parameterType.toString().contains("[J"))
								{
									long[] intBuffer = new long[stringBuffer.length];
									for(int j = 0; j != stringBuffer.length; ++ j)
									{
										intBuffer[j] = Long
												.valueOf(stringBuffer[j]);
									}
									methods.get(i).invoke(o, intBuffer);
								}
								else if(parameterType.toString().contains("[B"))
								{
									byte[] intBuffer = new byte[stringBuffer.length];
									for(int j = 0; j != stringBuffer.length; ++ j)
									{
										intBuffer[j] = Byte
												.valueOf(stringBuffer[j]);
									}
									methods.get(i).invoke(o, intBuffer);
								}
							}
							else
							{
								// �������Ϊ����������
								if(parameterType.toString().toLowerCase()
										.contains("int"))
								{
									methods.get(i).invoke(o,
											Integer.valueOf((String) oBuffer));
								}
								else if(parameterType.toString().toLowerCase()
										.contains("date"))
								{
									methods.get(i).invoke(o,
											Date.valueOf((String) oBuffer));
								}
								else if(parameterType.toString().toLowerCase()
										.contains("double"))
								{
									methods.get(i).invoke(o,
											Double.valueOf((String) oBuffer));
								}
								else if(parameterType.toString().toLowerCase()
										.contains("char"))
								{
									methods.get(i)
											.invoke(o,
													((String) oBuffer)
															.toCharArray()[0]);
								}
								else if(parameterType.toString().toLowerCase()
										.contains("float"))
								{
									methods.get(i).invoke(o,
											Float.valueOf((String) oBuffer));
								}
								else if(parameterType.toString().toLowerCase()
										.contains("boolean"))
								{
									methods.get(i).invoke(o,
											Boolean.valueOf((String) oBuffer));
								}
								else if(parameterType.toString().toLowerCase()
										.contains("short"))
								{
									methods.get(i).invoke(o,
											Short.valueOf((String) oBuffer));
								}
								else if(parameterType.toString().toLowerCase()
										.contains("long"))
								{
									methods.get(i).invoke(o,
											Long.valueOf((String) oBuffer));
								}
								else if(parameterType.toString().toLowerCase()
										.contains("byte"))
								{
									methods.get(i).invoke(o,
											Byte.valueOf((String) oBuffer));
								}
							}
						}
					}
					else
					{
						continue;
					}
				}
				catch(Exception e)
				{
					// e.printStackTrace(); //�����ת��,֤�����Ͳ���Ӧ,����Ҫ����
					continue;
				}
			}
		}
		return o;
	}
	
	/**
	 * �˷���ΪautoObject(Object source, String getObjectMethod, Object
	 * aim)��װ�ΰ�,�Զ��󶨵���HttpServletRequest���ȡ����
	 * 
	 * @author Sato_Team_4_Devil
	 */
	public static <T> T getObjectFrom(Class<T> clazz, HttpServletRequest request)
	{
		return autoObject(clazz, request, "getParameter");
	}
}
