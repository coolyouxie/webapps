package com.webapps.mapper;

import java.util.List;

import com.webapps.common.entity.Picture;
import com.webapps.common.form.PictureRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IPictureMapper extends IBaseMapper<Picture>,IPageMapper<Picture,PictureRequestForm>{
	
	public List<Picture> queryListByFkId(Integer fkId)throws Exception;
	
}
