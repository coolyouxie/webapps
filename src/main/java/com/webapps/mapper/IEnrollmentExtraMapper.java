package com.webapps.mapper;

import com.webapps.common.entity.EnrollmentExtra;
import com.webapps.common.form.EnrollmentExtraRequestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieshuai on 2017/9/24.
 */
@Repository
public interface IEnrollmentExtraMapper extends IBaseMapper<EnrollmentExtra>,IPageMapper<EnrollmentExtra,EnrollmentExtraRequestForm>{

    List<EnrollmentExtra> queryListByEnrollmentId(@Param("enrollmentId") Integer enrollmentId) throws Exception;

    List<EnrollmentExtra> queryListByEnrollmentIdAndState(@Param("enrollmentId") Integer enrollmentId,
                                                          @Param("state") Integer state) throws Exception;

    List<EnrollmentExtra> queryListByEnrollmentIdStateAndCashbackDays(@Param("enrollmentId") Integer enrollmentId,
                                                                      @Param("state") Integer state,
                                                                      @Param("cashbackDays") Integer cashbackDays)throws Exception;

}
