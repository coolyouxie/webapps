package com.webapps.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.PictureRequestForm;

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
	public ResultDto<String> uploadCompanyPicture(HttpServletRequest request);
	
	public ResultDto<Picture> savePicture(PictureRequestForm form);
	
	public ResultDto<Picture> saveCompanyPicture(PictureRequestForm form);
	
	public Page loadPictureList(Page page ,PictureRequestForm form);
	
	public ResultDto<String> uploadBannerPicture(HttpServletRequest request);
	
}
