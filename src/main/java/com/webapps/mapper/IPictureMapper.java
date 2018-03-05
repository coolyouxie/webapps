package com.webapps.mapper;

import com.webapps.common.entity.Picture;
import com.webapps.common.form.PictureRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IPictureMapper extends IBaseMapper<Picture>,IPageMapper<Picture,PictureRequestForm>{
	
	List<Picture> queryListByFkId(Integer fkId)throws Exception;
	
	List<Picture> queryListByFkIdAndType(@Param("fkId") Integer fkId, @Param("type") Integer type)throws Exception;
	
	int batchDeleteInLogic(List<Picture> list)throws Exception;
	
	int deleteInLogicByPicture(@Param("obj") Picture obj)throws Exception;
	
	List<Picture> queryByFkIdType(@Param("fkId") Integer fkId, @Param("type") Integer type)throws Exception;

	List<Picture> queryUserPictures(@Param("userId") Integer userId)throws Exception;
	
	List<Picture> queryByFkIdTypes(@Param("fkId") Integer fkId, @Param("types") Integer[] types)throws Exception;

	int queryCountByFkIdTypes(@Param("fkId")Integer fkId,@Param("types")Integer[] types)throws Exception;

	List<Picture> queryPageByFkIdTypes(@Param("startRow") int startRow, @Param("endRow")int endRow,
									   @Param("fkId") Integer fkId, @Param("types") Integer[] types)throws Exception;


}
