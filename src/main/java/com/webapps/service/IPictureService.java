package com.webapps.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

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
    List<Picture> getByFkId(Integer fkId)throws Exception;
	
	/**
	 * 保存图片信息
	 * @param obj
	 * @return
	 */
    ResultDto<String> uploadCompanyPicture(HttpServletRequest request);
	
	ResultDto<Picture> savePicture(PictureRequestForm form);
	
	ResultDto<Picture> saveCompanyPicture(PictureRequestForm form);
	
	Page loadPictureList(Page page, PictureRequestForm form);
	
	ResultDto<String> uploadBannerPicture(HttpServletRequest request);
	
	ResultDto<String> uploadImgForApp(MultipartFile[] files, String userId);

	ResultDto<List<Picture>> queryUserPictures(Integer userId);

	ResultDto<String> deleteById(Integer id);
	
}
