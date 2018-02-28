package com.webapps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.webapps.common.entity.*;
import com.webapps.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.service.IRecruitmentService;

@Service
@Transactional
public class RecruitmentServiceImpl implements IRecruitmentService {
	
	private static Logger logger = Logger.getLogger(RecruitmentServiceImpl.class);
	
	@Autowired
	private IRecruitmentMapper iRecruitmentMapper;
	
	@Autowired
	private ICompanyMapper iCompanyMapper;
	
	@Autowired
	private IPictureMapper iPictureMapper;
	
	@Autowired
	private IBannerConfigMapper iBannerConfigMapper;
	
	@Autowired
	private IMessageConfigMapper iMessageConfigMapper;

	@Autowired
	private IRecruitmentTagMapper iRecruitmentTagMapper;

	@Autowired
	private ITagMapper iTagMapper;

	@Override
	public ResultDto<RecruitmentRequestForm> saveRecruitment(RecruitmentRequestForm form)throws Exception {
		ResultDto<RecruitmentRequestForm> dto = new ResultDto<>();
		int result = 0;
		String errorMsg = null;
		if(form.getId()==null){
			result = iRecruitmentMapper.insert(form);
			if(form.getId()!=null&&form.getTags()!=null&&form.getTags().length>0){
				List<RecruitmentTag> tags = new ArrayList<>();
				for(int i=0;i<form.getTags().length;i++){
					RecruitmentTag tag = new RecruitmentTag();
					tag.setRecruitmentId(form.getId());
					tag.setTagId(form.getTags()[i]);
					tag.setCreateTime(new Date());
					tag.setDataState(1);
					tags.add(tag);
				}
				if(CollectionUtils.isNotEmpty(tags)){
					//添加发布单标题
					iRecruitmentTagMapper.batchInsert(tags);
				}
			}
			dto.setData(form);
			errorMsg = "新增失败";
		}else{
			try {
				result = iRecruitmentMapper.updateById(form.getId(), form);
				//删除页面上取消勾取的与发布单相关联的标签数据
				if(form.getTags()!=null&&form.getTags().length>0){
					List<RecruitmentTag> temp = new ArrayList<>();
					for(int i=0;i<form.getTags().length;i++){
						RecruitmentTag rt = new RecruitmentTag();
						rt.setTagId(form.getTags()[i]);
						temp.add(rt);
					}
					iRecruitmentTagMapper.batchDeleteByIdNotIn(temp,form.getId());
				}else{
					iRecruitmentTagMapper.batchDeleteByRecruimentId(form.getId());
				}
				//查询发布单当前所拥有的标签信息
				List<RecruitmentTag> rList = iRecruitmentTagMapper.queryAllByRecruitmentId(form.getId());
				//排除已有的标签，将新标签保存到数据库
				List<RecruitmentTag> newList = new ArrayList<>();
				if(CollectionUtils.isNotEmpty(rList)){
					for(int i=0;i<form.getTags().length;i++){
						boolean flag = true;
						for(RecruitmentTag rt : rList){
							if(rt.getTagId().equals(form.getTags()[i])){
								flag = false;
								break;
							}
						}
						if(flag){
							setNewRecruitmentTags(form, newList, i);
						}
					}
					iRecruitmentTagMapper.batchInsert(newList);
				}else{
					if(form.getTags()!=null&&form.getTags().length>0){
						for(int i=0;i<form.getTags().length;i++){
							setNewRecruitmentTags(form, newList, i);
						}
						iRecruitmentTagMapper.batchInsert(newList);
					}
				}
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
			ResultDto<BannerConfig> dto2 = saveBannerForRecruitment(form);
			if("F".equals(dto2.getResult())){
				dto.setResult("F");
				dto.setResult(dto2.getResult());
			}
		}
		dto.setData(form);
		return dto;
	}

	private void setNewRecruitmentTags(RecruitmentRequestForm form, List<RecruitmentTag> newList, int i) {
		RecruitmentTag rt = new RecruitmentTag();
		rt.setTagId(form.getTags()[i]);
		rt.setRecruitmentId(form.getId());
		rt.setCreateTime(new Date());
		rt.setDataState(1);
		newList.add(rt);
	}

	private ResultDto<BannerConfig> saveBannerForRecruitment(RecruitmentRequestForm form){
		ResultDto<BannerConfig> dto = new ResultDto<BannerConfig>();
		dto.setResult("S");
		try {
			List<BannerConfig> list = iBannerConfigMapper.getByFkIdAndType(form.getId(), 4);
			if(CollectionUtils.isNotEmpty(list)){
				if(form.getIsBanner()==2){
					int count = iBannerConfigMapper.batchDeleteInLogic(list);
					if(count==list.size()){
						dto.setResult("S");
					}else{
						dto.setResult("F");
						dto.setErrorMsg("删除banner数据时出错");
					}
					return dto;
				}
				BannerConfig bc = list.get(0);
				bc.setPicUrl(form.getBannerConfig().getPicUrl());
				int result = iBannerConfigMapper.updateById(bc.getId(), bc);
				if(result==1){
					dto.setResult("S");
					dto.setData(bc);
				}else{
					dto.setErrorMsg("更新发布单banner信息出错");
					dto.setResult("F");
				}
				return dto;
			}
			if(form.getIsBanner()==1){
				BannerConfig bc = new BannerConfig();
				bc.setUpdateTime(null);
				bc.setDataState(1);
				bc.setPicUrl(form.getBannerConfig().getPicUrl());
				bc.setFkId(form.getId());
				bc.setTitle(form.getTitle());
				bc.setType(4);
				int result = iBannerConfigMapper.insert(bc);
				if(result==1){
					dto.setResult("S");
					dto.setData(bc);
				}else{
					dto.setErrorMsg("保存发布单banner信息出错");
					dto.setResult("F");
				}
			}
			return dto;
		} catch (Exception e) {
			logger.error("保存或更新发布单banner信息异常："+e.getMessage());
			dto.setResult("F");
			dto.setErrorMsg("保存或更新发布单banner信息异常");
			return dto;
		}
	}
	
	@SuppressWarnings("unused")
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
		p.setPicUrl(form.getBannerConfig().getPicUrl());
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
		int rows = page.getRows();
		int count = iRecruitmentMapper.queryCount(form);
		List<Recruitment> list = iRecruitmentMapper.queryPage(startRow, rows, form);
		//查询发布单对应公司信息，并取公司信息中的图片作为发布单列表图片
		if(CollectionUtils.isNotEmpty(list)){
			for(Recruitment recruitment:list){
				List<Picture> pictures = iPictureMapper.queryListByFkIdAndType(recruitment.getCompany().getId(),1);
				if(CollectionUtils.isNotEmpty(pictures)){
					recruitment.setPicUrl(pictures.get(0).getPicUrl());
					List<RecruitmentTag> rTags = iRecruitmentTagMapper.queryAllByRecruitmentId(recruitment.getId());
					if(CollectionUtils.isNotEmpty(rTags)){
						Integer[] ids = new Integer[rTags.size()];
						for(int i=0;i<rTags.size();i++){
							ids[i]=rTags.get(i).getTagId();
						}
						List<Tag> tags = iTagMapper.queryByIds(ids);
						recruitment.setTagList(tags);
					}
				}
			}
		}
		page.setResultList(list);
		page.setRecords(count);
		page.countRecords(count);
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

	@Override
	public List<Recruitment> queryListByType(int type) throws Exception {
		List<Recruitment> list = iRecruitmentMapper.queryListByType(type);
		if(CollectionUtils.isNotEmpty(list)){
			for(Recruitment r:list){
				List<Picture> pictures = iPictureMapper.queryListByFkIdAndType(r.getCompany().getId(),1);
				if(CollectionUtils.isNotEmpty(pictures)){
					r.setPicUrl(pictures.get(0).getPicUrl());
				}
			}
		}
		return list;
	}


}
