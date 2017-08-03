package com.webapps.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webapps.common.entity.Picture;

public interface IPictureService {
	
	/**
	 * 根据fkId查询图片信息
	 * @param fkId
	 * @return
	 * @throws Exception
	 */
	public List<Picture> getByFkId(Integer fkId)throws Exception;
	
	/**
	 * 保存图片信息
	 * @param obj
	 * @return
	 */
	public int savePicture(Picture obj,HttpServletRequest request)throws Exception;
	
}
