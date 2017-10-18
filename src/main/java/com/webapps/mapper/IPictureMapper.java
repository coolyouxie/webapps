package com.webapps.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapps.common.entity.Picture;
import com.webapps.common.form.PictureRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IPictureMapper extends IBaseMapper<Picture>,IPageMapper<Picture,PictureRequestForm>{
	
	public List<Picture> queryListByFkId(Integer fkId)throws Exception;
	
	public List<Picture> queryListByFkIdAndType(@Param("fkId")Integer fkId,@Param("type")Integer type)throws Exception;
	
	public int batchDeleteInLogic(List<Picture> list)throws Exception;
	
	public int deleteInLogicByPicture(@Param("obj")Picture obj)throws Exception;
	
	public List<Picture> queryByFkIdType(@Param("fkId")Integer fkId,@Param("type")Integer type)throws Exception;

	public List<Picture> queryUserPictures(@Param("userId")Integer userId)throws Exception;
	
	public List<Picture> queryByFkIdTypes(@Param("fkId")Integer fkId,@Param("types")Integer[] types)throws Exception;
}
