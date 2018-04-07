package com.webapps.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Province;
import com.webapps.mapper.IProvinceMapper;
import com.webapps.service.IProvinceService;

@Service
@Transactional
public class ProvinceServiceImpl implements IProvinceService {
	
	private static Logger logger = Logger.getLogger(ProvinceServiceImpl.class);
	
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
	public ResultDto<List<Province>> queryProvinceByParentId(Integer parentId) {
		ResultDto<List<Province>> dto = new ResultDto<>();
		List<Province> list = null;
		try {
			list = iProvinceMapper.queryByParentId(parentId);
			dto.setData(list);
			dto.setResult("S");
		} catch (Exception e) {
			logger.error("根据上级行政区ID查询等政区信息异常："+e.getMessage());
			dto.setErrorMsg("根据上级行政区ID查询等政区信息异常");
			dto.setResult("F");
		}
		
		return dto;
	}

	@Override
	public ResultDto<List<Province>> queryProvinceByLevel(Integer level) {
		ResultDto<List<Province>> dto = new ResultDto<List<Province>>();
		List<Province> list = null;
		try {
			list = iProvinceMapper.queryByLevel(level);
			dto.setData(list);
			dto.setResult("S");
		} catch (Exception e) {
			logger.error("根据等级查询等政区信息异常："+e.getMessage());
			dto.setErrorMsg("根据等级查询等政区信息异常");
			dto.setResult("F");
		}
		return dto;
	}

}
