package cn.cafebabe.websupport.util;

import cn.cafebabe.autodao.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author linjiangsheng
 * @created 14-4-8
 */
public class CollectionUtil
{
	public static String collectionToQuery(Collection<?> collection)
	{
		Assert.isTrue(collection != null && !collection.isEmpty(), "collectionToQuery的参数不能为空(null或empty)");
		StringBuilder queryString = new StringBuilder();
		queryString.append("(");
		int index = 0;
		String split = null;
		for (Object o : collection)
		{
			if (split == null)
			{
				if (o instanceof Integer)
				{
					split = "";
				}
				else
				{
					split = "'";
				}
			}
			queryString.append(split).append(o.toString()).append(split);
			if (++index < collection.size())
			{
				queryString.append(", ");
			}
		}
		queryString.append(")");
		return queryString.toString();
	}

	/**
	 * 按照key将map里的list取出来,若list不存在,则在map里创建对应的ArrayList,并返回
	 *
	 * @param map
	 * @param key
	 * @param <T>
	 * @param <O>
	 * @return
	 * @author linjiangsheng
	 * @created 2013-11-7
	 */
	public static <T, O> List<T> getOrCreatListOfMap(Map<O, List<T>> map, O key) {
		List<T> list = map.get(key);
		if (list == null) {
			map.put(key, (list = new ArrayList<T>()));
		}
		return list;
	}
}
