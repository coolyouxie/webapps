package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.Province;
import com.webapps.common.entity.User;
import com.webapps.common.form.UserRequestForm;
import com.webapps.mapper.IProvinceMapper;
import com.webapps.service.IProvinceService;

@Service
@Transactional
public class ProvinceServiceImpl implements IProvinceService {
	
	@Autowired
	private IProvinceMapper iProvinceMapper;

	@Override
	public Province getById(Integer id) throws Exception {
		Province p = iProvinceMapper.getById(id);
		return p;
	}

	@Override
	public List<Province> setProvinceParentId() throws Exception {
		List<Province> list = iProvinceMapper.queryAll();
		for(Province p:list){
			if(p.getCode().contains("0000")){
				p.setLevel(1);
			}else if(p.getCode().contains("00")){
				String tempCode = p.getCode().substring(0, 2)+"0000";
				Province province = iProvinceMapper.getByCode(tempCode);
				p.setParentId(province.getId());
				p.setLevel(2);
			}else{
				String tempCode = p.getCode().substring(0, 4)+"00";
				Province province = iProvinceMapper.getByCode(tempCode);
				p.setParentId(province.getId());
				p.setLevel(3);
			}
		}
		iProvinceMapper.batchUpdate(list);
		return null;
	}

	@Override
	public List<Province> queryProvinceByParentId(Integer parentId) throws Exception {
		List<Province> list = iProvinceMapper.queryByParentId(parentId);
		return list;
	}

	@Override
	public List<Province> queryProvinceByLevel(Integer level) throws Exception {
		List<Province> list = iProvinceMapper.queryByLevel(level);
		return list;
	}

}
