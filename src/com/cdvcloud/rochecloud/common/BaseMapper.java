package com.cdvcloud.rochecloud.common;

import java.util.List;


public interface BaseMapper<T,ID> {
	
	/**
	 * 添加实体对象
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	
	/**
	 * 根据id删除实体对象
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(ID id);

	
	/**
	 * 根据对象修改实体
	 * @param entity
	 * @return
	 */
	public int updateByPrimaryKey(T entity);

	/**
	 * 根据对象的一些属性修改实体
	 * @param entity
	 * @return
	 */
	public int updateByPrimaryKeySelective(T entity);
	
	
	/**
	 * 根据条件查询对象的集合
	 * @return
	 */
	public List<T> selectFindAll(Pages<T> page);
	
	/**
	 * 根据id查询实体对象
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(ID id);
	
	/**
	 * 根据条件查询一共有多少条数
	 * @param page
	 * @return
	 */
	public int countFindAll(Pages<T> page);
	
}
