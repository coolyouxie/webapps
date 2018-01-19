package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.entity.ParamConfig;
import com.webapps.common.form.ParamConfigRequestForm;
import com.webapps.mapper.IParamConfigMapper;
import com.webapps.service.IParamConfigService;

@Service
@Transactional
public class ParamConfigService implements IParamConfigService {
	
	@Autowired private IParamConfigMapper iParamConfigMapper;

	public static enum ParamConfigType{
		注册红包(1) , 入职红包(2) , 期满红包(3);
		private int id;
		ParamConfigType(int id) {
			this.id = id;
		}
		public int getId() {
			return this.id;
		}
	}
	
	/**
	 * 根据红包类型，获取对应的红包配置对象
	 * @author scorpio.yang
	 * @since 2018-01-19
	 * @param type
	 * @return
	 */
	public ParamConfig getParamConfigByAwardType(ParamConfigType type) {
		try {
			return iParamConfigMapper.getById(type.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据paramConfig对象，获取最终的红包金额
	 * @author scorpio.yang
	 * @since 2018-01-17
	 * @param pc
	 * @return
	 */
	public BigDecimal getParamConfigPrice(ParamConfig pc) {
		if(null != pc) {
			switch (pc.getType()) {
				case 1:
					return pc.getMaxNum();
				case 2:
					int min = pc.getMinNum().multiply(new BigDecimal(100)).intValue();
					int max = pc.getMaxNum().multiply(new BigDecimal(100)).intValue();
					if(min >= max) {
						return null;
					}
					long rand = Math.round(Math.random()*(max-min))+min;
					return new BigDecimal((double)(rand/100.0)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
				default:
					return null;
			}
		}
		return null;
	}
	
	@Override
	public int saveParamConfig(ParamConfigRequestForm form) throws Exception {
		int result = 0;
		if(form.getId()==null){
			result = iParamConfigMapper.insert(form);
		}else{
			result = iParamConfigMapper.updateById(form.getId(),form);
		}
		return result;
	}

	@Override
	public int deleteParamConfigById(Integer id) throws Exception {
		int result = iParamConfigMapper.deleteByIdInLogic(id);
		return result;
	}

	@Override
	public ParamConfig getById(Integer id) throws Exception {
		ParamConfig paramConfig = iParamConfigMapper.getById(id);
		return paramConfig;
	}

	@Override
	public List<ParamConfig> queryAll() throws Exception {
		List<ParamConfig> list = iParamConfigMapper.queryAll();
		return list;
	}

}
