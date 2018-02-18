package com.webapps.mapper;

import com.webapps.common.entity.TalkerTask;
import com.webapps.common.form.TalkerTaskRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface ITalkerTaskMapper extends IBaseMapper<TalkerTask> ,IPageMapper<TalkerTask,TalkerTaskRequestForm>{

	public int batchInsert(List<TalkerTask> list)throws Exception;

	public List<TalkerTask> queryAllForUserEnroll()throws Exception;

	public int updateByTalkerId(@Param("obj")TalkerTask talkerTask)throws Exception;

}
