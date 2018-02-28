package com.webapps.service.impl;

import com.webapps.common.entity.RecruitmentTag;
import com.webapps.common.entity.Tag;
import com.webapps.mapper.IRecruitmentTagMapper;
import com.webapps.mapper.ITagMapper;
import com.webapps.service.ITagService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements ITagService {
	
	private static Logger logger = Logger.getLogger(TagServiceImpl.class);

	@Autowired
	private ITagMapper iTagMapper;

	@Autowired
	private IRecruitmentTagMapper iRecruitmentTagMapper;

	@Override
	public List<Tag> queryAllTags() throws Exception {
		List<Tag> list = iTagMapper.queryAll();
		return list;
	}

	@Override
	public List<RecruitmentTag> queryAllRecruitmentTags(Integer recruitmentId) throws Exception {
		List<RecruitmentTag> rList = iRecruitmentTagMapper.queryAllByRecruitmentId(recruitmentId);
		return rList;
	}

	@Override
	public List<Tag> queryAllTagsWithCheckStatus(Integer recruitmentId) throws Exception {
		List<Tag> list = iTagMapper.queryAll();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		List<RecruitmentTag> rList = iRecruitmentTagMapper.queryAllByRecruitmentId(recruitmentId);
		if(CollectionUtils.isEmpty(rList)){
			if(CollectionUtils.isNotEmpty(list)){
				for(Tag tag :list){
					tag.setCheckStatus("unchecked");
				}
			}
			return list;
		}
		for(Tag tag:list){
			for(RecruitmentTag rTag:rList){
				if(tag.getId().equals(rTag.getTagId())){
					tag.setCheckStatus("checked");
					break;
				}else{
					tag.setCheckStatus("unchecked");
				}
			}
		}
		return list;
	}


}
