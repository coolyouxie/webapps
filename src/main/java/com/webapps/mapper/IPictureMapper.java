package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.Picture;
import com.webapps.common.form.PictureRequestForm;

/**
 * Created by xieshuai on 2017-6-28.
 */
public interface IPictureMapper extends IBaseMapper<Picture>,IPageMapper<Picture,PictureRequestForm>{
	
	public List<Picture> queryListByFkId(Integer fkId)throws Exception;
	
	public List<Picture> queryListByFkIdAndType(@Param("fkId")Integer fkId,@Param("type")Integer type)throws Exception;
	
	public int batchDeleteInLogic(List<Picture> list)throws Exception;
	
	public int deleteInLogicByPicture(@Param("obj")Picture obj)throws Exception;
}
