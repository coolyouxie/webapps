package com.webapps.mapper;

import java.util.List;

import com.webapps.common.entity.Picture;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IPictureMapper extends IBaseMapper<Picture>{
	
	public List<Picture> queryListByFkId(Integer fkId)throws Exception;
	
}
