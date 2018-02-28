package com.webapps.mapper;

import com.webapps.common.entity.RecruitmentTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2018-2-28.
 */
@Repository
public interface IRecruitmentTagMapper extends IBaseMapper<RecruitmentTag>{

    List<RecruitmentTag> queryAllByRecruitmentId(@Param("recruitmentId")Integer recrutimentId)throws Exception;

    int batchInsert(@Param("list")List<RecruitmentTag> list)throws Exception;

    int batchDeleteByIdNotIn(@Param("list")List<RecruitmentTag> list,@Param("recruitmentId")Integer recruitmentId)throws Exception;

    int batchDeleteByRecruimentId(@Param("recruitmentId")Integer id)throws Exception;

}
