package com.webapps.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Picture;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.mapper.ICompanyMapper;
import com.webapps.mapper.IPictureMapper;
import com.webapps.mapper.IRecruitmentMapper;
import com.webapps.service.IRecruitmentService;

@Service
@Transactional
public class RecruitmentServiceImpl implements IRecruitmentService {
	
	@Autowired
	private IRecruitmentMapper iRecruitmentMapper;
	
	@Autowired
	private ICompanyMapper iCompanyMapper;
	
	@Autowired
	private IPictureMapper iPictureMapper;

	@Override
	public ResultDto<RecruitmentRequestForm> saveRecruitment(RecruitmentRequestForm form) {
		ResultDto<RecruitmentRequestForm> dto = new ResultDto<RecruitmentRequestForm>();
		int result = 0;
		String errorMsg = null;
		if(form.getId()==null){
			result = iRecruitmentMapper.insert(form);
			errorMsg = "新增失败";
		}else{
			try {
				result = iRecruitmentMapper.updateById(form.getId(), form);
			} catch (Exception e) {
				e.printStackTrace();
			}
			errorMsg = "更新失败";
		}
		if(result != 1){
			dto.setErrorMsg(errorMsg);
			dto.setResult("F");
		}else{
			dto.setResult("S");
			ResultDto<Picture> dto1 = saveBannerPicture(form);
			if("F".equals(dto1.getResult())){
				dto.setResult("F");
				dto.setResult(dto1.getResult());
			}
		}
		dto.setData(form);
		return dto;
	}
	
	private ResultDto<Picture> saveBannerPicture(RecruitmentRequestForm form){
		ResultDto<Picture> dto = new ResultDto<Picture>();
		try {
			//找到老的banner图片，置为删除状态
			List<Picture> list = iPictureMapper.queryListByFkIdAndType(form.getId(), 7);
			if(CollectionUtils.isNotEmpty(list)){
				iPictureMapper.batchDeleteInLogic(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("删除发布单老banner图片时异常");
			return dto;
		}
		//保存新的图片
		Picture p = new Picture();
		p.setDataState(1);
		p.setFkId(form.getId());
		p.setPicUrl(form.getPicture().getPicUrl());
		p.setType(7);
		p.setTitle(form.getTitle());
		p.setRemark("发布单banner图片");
		int result = iPictureMapper.insert(p);
		if(result == 1 ){
			dto.setData(p);
			dto.setResult("S");
		}else{
			dto.setErrorMsg("保存发布单banner图片失败");
			dto.setResult("F");
		}
		return dto;
	}
	
	@Override
	public List<Recruitment> queryListByCompanyId(Integer companyId) throws Exception {
		List<Recruitment> list = iRecruitmentMapper.queryByCompanyId(companyId);
		return list;
	}

	@Override
	public int deleteRecruitmentById(Integer id) throws Exception {
		int result = iRecruitmentMapper.deleteByIdInLogic(id);
		return result;
	}

	@Override
	public Page loadRecruitmentList(Page page, RecruitmentRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iRecruitmentMapper.queryCount(form);
		List<Recruitment> list = iRecruitmentMapper.queryPage(startRow, endRow, form);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public Recruitment getById(Integer id) throws Exception {
		Recruitment r = iRecruitmentMapper.getById(id);
		if(r!=null&&r.getCompany()!=null&&r.getCompany().getId()!=null){
			Company c = iCompanyMapper.getById(r.getCompany().getId());
			r.setCompany(c);
		}
		return r;
	}
	
	

}
