package com.webapps.common.bean;

/**
 * 
 * @author mashengwen
 * mybatis 的mapper继承DomainEntityMapper
 * 实现相关方法，传入和传出参数需要实现Entity接口
 *
 */
public interface DomainEntityRMapper<I extends Dto> {

	/**
	 * @param fkid
	 * deleteByFkIds
	 */
	public int insertByFkIds(Long... fkid);

	/**
	 * @param fkid
	 * deleteByFkIds
	 */
	public int deleteByFkIds(Long... fkid);
	
}
