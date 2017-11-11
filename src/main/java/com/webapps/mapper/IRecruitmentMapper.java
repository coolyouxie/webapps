package com.webapps.mapper;

import java.util.List;

import com.webapps.common.entity.Province;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.RecruitmentRequestForm;
import org.springframework.stereotype.Repository;

/**
 * Created by xieshuai on 2017-6-28.
 */
@Repository
public interface IRecruitmentMapper extends IBaseMapper<Recruitment>,IPageMapper<Recruitment,RecruitmentRequestForm>{
	
	Province getByCode(String code);
	
	List<Recruitment> queryByCompanyId(Integer companyId);

	List<Recruitment> queryListByType(int type)throws Exception;

}
