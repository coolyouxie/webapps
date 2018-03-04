package com.webapps.service.impl;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.dto.UserApproveCountDto;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.TalkerTask;
import com.webapps.common.entity.User;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.common.utils.DateUtil;
import com.webapps.mapper.IEnrollmentMapper;
import com.webapps.mapper.ITalkerTaskMapper;
import com.webapps.mapper.IUserApproveCountMapper;
import com.webapps.mapper.IUserMapper;
import com.webapps.service.IEnrollmentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EnrollmentServiceImpl implements IEnrollmentService {

    private static Logger logger = Logger.getLogger(EnrollmentServiceImpl.class);

    @Autowired
    private IEnrollmentMapper iEnrollmentMapper;

    @Autowired
    private IUserMapper iUserMapper;

    @Autowired
    private IUserApproveCountMapper iUserApproveCountMapper;

    @Autowired
    private ITalkerTaskMapper iTalkerTaskMapper;

    @Override
    public Page loadEnrollmentList(Page page, EnrollmentRequestForm enrollment) throws Exception {
        int startRow = page.getStartRow();
        int rows = page.getRows();
        int count = iEnrollmentMapper.queryCount(enrollment);
        List<Enrollment> list = iEnrollmentMapper.queryPage(startRow, rows, enrollment);
        page.setResultList(list);
        page.setRecords(count);
        page.countRecords(count);
        return page;
    }

    @Override
    public Enrollment getById(Integer id) throws Exception {
        Enrollment em = iEnrollmentMapper.getById(id);
        return em;
    }

    /**
     * 报名，在APP端也要限制，如果发现某用户已经报了某个发布单，则报名按钮置灰，显示“已报名”
     */
    @Override
    public ResultDto<Enrollment> saveEnrollment(Enrollment enrollment) throws Exception {
        ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
        int count = iEnrollmentMapper.countByFkIds(enrollment);
        if (count >= 1) {
            dto.setResult("F");
            dto.setErrorMsg("不能重复报名");
            return dto;
        }
        int result = iEnrollmentMapper.insert(enrollment);
        if (result == 1) {
            dto.setResult("S");
            return dto;
        } else {
            dto.setResult("F");
            dto.setErrorMsg("报名失败，请稍后再试");
            return dto;
        }
    }

    @Override
    public ResultDto<Enrollment> deleteEnrollmentById(Integer id) throws Exception {
        ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
        int result = iEnrollmentMapper.deleteByIdInLogic(id);
        if (result == 1) {
            dto.setResult("S");
            return dto;
        } else {
            dto.setResult("F");
            dto.setErrorMsg("取消失败");
            return dto;
        }
    }

    @Override
    public List<Enrollment> queryEnrollmentListByUserId(Integer id) throws Exception {
        EnrollmentRequestForm form = new EnrollmentRequestForm();
        User user = new User();
        user.setId(id);
        form.setUser(user);
        List<Enrollment> list = iEnrollmentMapper.queryListByFkId(form);
        return list;
    }

    @Override
    public int saveTalkInfoById(EnrollmentRequestForm em) throws Exception {
        Enrollment temp = iEnrollmentMapper.getById(em.getId());
        temp.setTalkResult(em.getTalkResult());
        temp.setIsTalked(em.getIsTalked());
        temp.setInterviewIntention(em.getInterviewIntention());
        temp.setInterviewTime(DateUtil.parseDateByStr(em.getInterviewTimeStr(), "yyyy-MM-dd"));
        temp.setIntentionCityId(em.getIntentionCityId());
        temp.setUpdateTime(new Date());
        int count = iEnrollmentMapper.saveTalkInfoById(temp, temp.getId());
        return count;
    }

    /**
     * 用户报名后要保证报名列表只显示该用户的一条最新数据
     */
    @Override
    public ResultDto<Enrollment> userEnroll(Enrollment em) throws Exception {
        ResultDto<Enrollment> dto = null;
        em.setIsTalked(0);
        em.setState(1);
        em.setDataState(1);
        int count = iEnrollmentMapper.countByFkIds(em);
        if (count >= 1) {
            dto = new ResultDto<Enrollment>();
            dto.setErrorMsg("已经报名了本次招工，请不要重复报名");
            dto.setResult("F");
            return dto;
        }
        EnrollmentRequestForm form = new EnrollmentRequestForm();
        form.setUser(em.getUser());
        List<Enrollment> list = iEnrollmentMapper.queryListByFkId(form);
        User user = null;
        user = iUserMapper.getById(em.getUser().getId());
        if (CollectionUtils.isNotEmpty(list)) {
            Enrollment em1 = list.get(0);
            if (em1.getState() == 4 || user.getCurrentState() == 0) {
                user.setUpdateTime(new Date());
                user.setCurrentState(1);
            }
        }
        //更新最新记录为新插入的数据
        List<Enrollment> latestList = iEnrollmentMapper.findLatestByUserId(em.getUser().getId());
        if (CollectionUtils.isNotEmpty(latestList)) {
            //如果当前报名记录之前还存在未沟通的报名记录，则取出之前的最新的报名记录中分配好的招聘专员信息赋值给该报名记录
            Enrollment latest = latestList.get(0);
            if (latest.getTalkerId() != null && StringUtils.isNotBlank(latest.getTalkerName())) {
                em.setTalkerId(latest.getTalkerId());
                em.setTalkerName(latest.getTalkerName());
            } else {
                setApproverInfo(em);
            }
            em.setIsLatest(1);
            //将之前的数据更新为不是最新记录
            iEnrollmentMapper.updateToNotLatest(em.getUser().getId());
        } else {
            //如果该记录之前的报名记录均已沟通完成则随机分配客服专员
            em.setIsLatest(1);
            setApproverInfo(em);
        }
        int result = iEnrollmentMapper.insert(em);
        iUserMapper.updateById(user.getId(), user);
        if (result == 1) {
            dto = new ResultDto<Enrollment>();
            dto.setData(em);
            dto.setResult("S");
            if(getLotteryChance(em.getUser().getId())){
                //如果有抽奖机会则返回S1
                dto.setResult("S1");
            }
        } else {
            dto = new ResultDto<Enrollment>();
            dto.setErrorMsg("保存报名信息异常");
            dto.setResult("F");
        }
        return dto;
    }

    /**
     * 获取抽奖机会
     *
     * @return
     */
    private boolean getLotteryChance(Integer userId) {
        try {
            List<Enrollment> enrollments = iEnrollmentMapper.findLatestByUserId(userId);
            if (CollectionUtils.isNotEmpty(enrollments)) {
                Enrollment enrollment = enrollments.get(0);
                if (enrollment.getState().equals(31)) {
                    return true;
                }else{
                    return false;
                }
            }else{
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setApproverInfo(Enrollment em) {
        try {
            /*
             * 招聘员查询规则：
             * 根据招聘员拥有的待沟通会员数量进行排序，从低到高，取到待沟通人数最少的招聘员分配
             * 如果拥有待沟通人数相等，则取到更新时间最远的招聘员进行分配
             */
            List<TalkerTask> talkerTasks = iTalkerTaskMapper.queryAllForUserEnroll();
            List<User> talkerList = iUserMapper.queryUsersByUserType(4);
            List<UserApproveCountDto> list = iUserApproveCountMapper.queryTalkCount();
            //初始化招聘员数据进入t_talker_task表
            if (CollectionUtils.isEmpty(talkerTasks)) {
                List<TalkerTask> newTalkerList = new ArrayList<>();
                for (int i = 0; i < talkerList.size(); i++) {
                    TalkerTask tt = new TalkerTask();
                    tt.setCreateTime(new Date());
                    tt.setTalkerId(talkerList.get(i).getId());
                    tt.setTalkerName(talkerList.get(i).getName());
                    tt.setDataState(1);
                    tt.setJobsCount(0);
                    if (i == 0) {
                        //如果是初始化数据则
                        tt.setJobsCount(1);
                        em.setUpdateTime(new Date());
                        em.setTalkerId(tt.getTalkerId());
                        em.setTalkerName(tt.getTalkerName());
                    }
                    newTalkerList.add(tt);
                }
                iTalkerTaskMapper.batchInsert(newTalkerList);
                return;
            }
            //如果有新注册的招聘员若未生成待沟通数据，则生成一条
            if (CollectionUtils.isNotEmpty(talkerList) && CollectionUtils.isNotEmpty(talkerTasks)) {
                List<TalkerTask> newList = new ArrayList<TalkerTask>();
                for (User talker : talkerList) {
                    for (TalkerTask tt : talkerTasks) {
                        if (!talker.getId().equals(tt.getTalkerId())) {
                            TalkerTask temp = new TalkerTask();
                            temp.setJobsCount(0);
                            temp.setDataState(1);
                            temp.setCreateTime(new Date());
                            temp.setTalkerName(talker.getName());
                            temp.setTalkerId(talker.getId());
                            newList.add(temp);
                        }
                    }
                }
                iTalkerTaskMapper.batchInsert(newList);
            }
            TalkerTask tt = talkerTasks.get(0);
            int jobsCount = tt.getJobsCount();
            jobsCount++;
            tt.setJobsCount(jobsCount);
            tt.setUpdateTime(new Date());
            iTalkerTaskMapper.updateById(tt.getId(), tt);
            em.setTalkerId(tt.getTalkerId());
            em.setTalkerName(tt.getTalkerName());
            em.setUpdateTime(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ResultDto<Enrollment> updateEnrollment(EnrollmentRequestForm form) {
        ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
        int result;
        try {
            result = iEnrollmentMapper.updateById(form.getId(), form);
            if (result != 1) {
                dto.setErrorMsg("报名信息更新失败，请稍后重试");
                dto.setResult("F");
            } else {
                dto.setResult("S");
            }
        } catch (Exception e) {
            dto.setErrorMsg("报名信息更新异常，请稍后重试");
            dto.setResult("F");
            e.printStackTrace();
        }
        dto.setData(form);
        return dto;
    }

    @Override
    public ResultDto<Enrollment> cancelEnroll(EnrollmentRequestForm form) {
        ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
        try {
            Enrollment enrollment = iEnrollmentMapper.getById(form.getId());
            if (enrollment.getState() != 1) {
                dto.setErrorMsg("报名信息状态不符合可取消条件");
                dto.setResult("F");
                return dto;
            }
            enrollment.setDataState(0);
            enrollment.setUpdateTime(new Date());
            int result = iEnrollmentMapper.cancelEnroll(enrollment, enrollment.getId());
            if (result != 1) {
                dto.setErrorMsg("报名信息更新失败，请稍后重试");
                dto.setResult("F");
            } else {
                dto.setResult("S");
            }
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            dto.setErrorMsg("报名信息更新异常，请稍后重试");
            dto.setResult("F");
            return dto;
        }
    }

    @Override
    public Enrollment getCurrentStateEnrollmentByUserId(Integer userId) {

        return null;
    }

    @Override
    public ResultDto<String> updateInterviewTime(EnrollmentRequestForm form) {
        ResultDto<String> dto = new ResultDto<String>();
        try {
            iEnrollmentMapper.updateInterviewTime(form.getId(), DateUtil.parseDateByStr(form.getInterviewTimeStr(), "yyyy-MM-dd"));
            dto.setResult("S");
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            dto.setResult("F");
            dto.setErrorMsg("更新面试时间异常，请稍后再试");
        }
        return dto;
    }

    @Override
    public Page loadRecruitProcess(Page page, EnrollmentRequestForm form) {
        int startRow = page.getStartRow();
        int rows = page.getRows();
        int count = iEnrollmentMapper.queryCountForRecruitProcess(form);
        List<Enrollment> list = null;
        try {
            list = iEnrollmentMapper.queryPageForRecruitProcess(startRow, rows, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        page.setResultList(list);
        page.countRecords(count);
        return page;
    }

    @Override
    public ResultDto<String> updateTalkerInfo(EnrollmentRequestForm form) {
        ResultDto<String> dto = new ResultDto<>();
        try {
            Enrollment temp = iEnrollmentMapper.getById(form.getId());
            temp.setTalkerName(form.getTalkerName());
            temp.setTalkerId(form.getTalkerId());
            temp.setUpdateTime(new Date());
            iEnrollmentMapper.updateTalkerInfo(temp);
            dto.setResult("S");
        } catch (Exception e) {
            e.printStackTrace();
            dto.setResult("F");
            dto.setErrorMsg("更新招聘员信息时异常，请稍后再试");
        }
        return dto;
    }

}
