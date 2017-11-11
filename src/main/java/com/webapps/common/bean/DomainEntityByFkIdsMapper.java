package com.webapps.common.bean;

import java.util.List;

/**
 * 
 * @author mashengwen
 * mybatis 的mapper继承DomainEntityMapper
 * 实现相关方法，传入和传出参数需要实现Entity接口
 *
 */
public interface DomainEntityByFkIdsMapper<I extends Dto> {

	/**
	 * @param fkid
	 * deleteByFkIds
	 */
    int deleteByFkIds(Long... fkid);

	/**
	 * @param fkIds
	 * @return
	 * 根据FkIds查询返回多个对象
	 */
    List<I> queryByFkIds(Long... fkId);
	
	
}
