package com.webapps.mapper;

import com.webapps.common.entity.EnrollmentExtra;
import com.webapps.common.form.EnrollmentExtraRequestForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xieshuai on 2017/9/24.
 */
public interface IEnrollmentExtraMapper extends IBaseMapper<EnrollmentExtra>,IPageMapper<EnrollmentExtra,EnrollmentExtraRequestForm>{

    public List<EnrollmentExtra> queryListByEnrollmentId(@Param("enrollmentId") Integer enrollmentId) throws Exception;

    public List<EnrollmentExtra> queryListByEnrollmentIdAndState(@Param("enrollmentId") Integer enrollmentId,
                                                         @Param("state") Integer state) throws Exception;
}
