package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.PictureRequestForm;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
	
	ResultDto<Picture> savePicture(Picture pic) throws Exception;
	
	ResultDto<Picture> saveCompanyPicture(PictureRequestForm form);
	
	Page loadPictureList(Page page, PictureRequestForm form);
	
	ResultDto<String> uploadBannerPicture(HttpServletRequest request);
	
	ResultDto<String> uploadImgForApp(MultipartFile[] files, String userId);

	ResultDto<List<Picture>> queryUserPictures(Integer userId);

	ResultDto<String> deleteById(Integer id);

	Page queryPageByFkIdTypes(Page page,PictureRequestForm form)throws Exception;

	Page queryPagePromotionPics(Page page,Integer fkId)throws Exception;
	
}
