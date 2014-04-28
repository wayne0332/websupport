package cn.cafebabe.websupport.service;

import cn.cafebabe.autodao.dao.BaseDao;
import cn.cafebabe.autodao.pojo.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Transactional(readOnly = true)
@Validated
public abstract class BaseService
{
	protected final static String COUNT_HQL = "select count(*) ";
	protected final static Page ONE_RECORD = new Page().setPageSize(1).setTotalCount(1);
	public final static Page DEFAULT_PAGE = new Page().setPageSize(Page.getDefaultPageSize()).setTotalCount(Page.getDefaultPageSize());
	public static final String UNCHECKED = "unchecked";
	@Resource(name = "dao")
	protected BaseDao dao = null;

	protected BaseService()
	{
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean save(Object object)
	{
		try
		{
			dao.persist(object);
			dao.refresh(object);
			return true;
		} catch (RuntimeException e)
		{
			return false;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean update(Object object)
	{
		try
		{
			dao.merge(object);
			dao.refresh(object);
			return true;
		} catch (RuntimeException e)
		{
			return false;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(Object object)
	{
		try
		{
			dao.delete(object);
			dao.refresh(object);
			return true;
		} catch (RuntimeException e)
		{
			return false;
		}
	}

	/**
	 * 只有数据库存在唯一一样的对象才能成功 不然返回null
	 */
	protected <T> T login(T user)
	{
		try
		{
			List<T> list = null;
			if ((list = dao.findByExample(user)).size() == 1)
			{
				return list.get(0);
			}
			return null;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据dao.findByExample判断
	 */
	public boolean exist(Object object)
	{
		try
		{
			if (dao.findByExample(object).size() > 0)
			{
				return true;
			}
			return false;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @see cn.cafebabe.autodao.dao.BaseDao#update(Object, Collection)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer update(Object object, String... conditions)
	{
		return dao.update(object, conditions);
	}

	/**
	 * 若没有则返回null
	 */
	protected <T> T getFist(List<T> result)
	{
		if (result.size() > 0)
		{
			return result.get(0);
		} else
		{
			return null;
		}
	}

	public void setDao(BaseDao dao)
	{
		this.dao = dao;
	}
}
