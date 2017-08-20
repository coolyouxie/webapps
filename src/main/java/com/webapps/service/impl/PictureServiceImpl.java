package com.webapps.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.PictureRequestForm;
import com.webapps.mapper.IBannerConfigMapper;
import com.webapps.mapper.IPictureMapper;
import com.webapps.service.IPictureService;

@Service
@Transactional
public class PictureServiceImpl implements IPictureService {
	
	Logger logger = Logger.getLogger(PictureServiceImpl.class);
	
	@Autowired
	private IPictureMapper iPictureMapper;
	
	@Autowired
	private IBannerConfigMapper iBannerConfigMapper;

	@Override
	public List<Picture> getByFkId(Integer fkId) throws Exception {
		List<Picture> list = iPictureMapper.queryListByFkId(fkId);
		return list;
	}

	@Override
	public ResultDto savePicture(Picture obj,HttpServletRequest request) {
		ResultDto<Picture> dto = null;
		String sourceType = request.getParameter("sourceType");
		int picType = 0;
		File destFile = null;
		Picture file = new Picture();
		// 转换为文件类型的request
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获取对应file对象
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Iterator<String> fileIterator = multipartRequest.getFileNames();
        String requestURL = request.getRequestURL().toString();
        String projectPath = requestURL.replace("/fileUpload/pictureUpload","");
        String path="/Users/xieshuai/fileupload/";
        Integer id = Integer.valueOf(request.getParameter("id"));
        if("company".equals(sourceType)){
        	path += "company"+File.separator;
        }else if("banner".equals(sourceType)){
        	path += "banner"+File.separator;
        }
        File dir = new File(path+sourceType+"_"+id);
        if(!dir.exists()){
        	dir.mkdirs();
        }
        while (fileIterator.hasNext()) {
            String fileKey = fileIterator.next();
            logger.debug("文件名为：" + fileKey);
            // 获取对应文件
            MultipartFile multipartFile = fileMap.get(fileKey);
            String contentType = multipartFile.getContentType();
            String fileType = contentType.substring(contentType.indexOf("/") + 1);
            String fileName = new Date().getTime() + "." + fileType;
            destFile = new File(dir.getAbsolutePath()+File.separator+fileName);
            try {
				multipartFile.transferTo(destFile);
			} catch (IllegalStateException e) {
				logger.error("图片文件状态异常");
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("图片文件输入输出异常");
				e.printStackTrace();
			}
        }
		if("company".equals(sourceType)){
			picType = 1;
			try {
				dto = savePicture(obj, id, picType, destFile, file, projectPath);
			} catch (Exception e) {
				logger.error("保存图片异常");
				e.printStackTrace();
			}
		}else if("banner".equals(sourceType)){
			Integer companyId = -1;
			Integer recruitmentId = -1;
			String companyIdStr = request.getParameter("companyId");
			String recruitmentIdStr = request.getParameter("recruitmentId");
			if(StringUtils.isNotBlank(companyIdStr))
				companyId = Integer.valueOf(companyIdStr);
			if(StringUtils.isNotBlank(recruitmentIdStr))
				recruitmentId = Integer.valueOf(recruitmentIdStr);
			saveBannerConfig(obj, id, picType, destFile, projectPath, sourceType, companyId, recruitmentId);
		}
        return dto;
	}

	private ResultDto<Picture> savePicture(Picture obj, Integer id, int picType, File destFile, Picture file, String projectPath)
			throws Exception {
		ResultDto<Picture> dto = new ResultDto<Picture>();
		file.setDataState(1);
        file.setType(picType);
        file.setPicUrl(projectPath+File.separator+"fileupload/company/company_"+id+File.separator+destFile.getName());
        file.setFkId(id);
        int count = 0;
		if(file.getId()==null){
			count = iPictureMapper.insert(file);
		}else{
			count = iPictureMapper.updateById(obj.getId(), file);
		}
		if(count==1){
			dto.setResult("S");
		}else{
			dto.setErrorMsg("图片保存或更新失败，请稍后重试");
			dto.setResult("F");
		}
		dto.setData(file);
		return dto;
	}
	
	private ResultDto<BannerConfig> saveBannerConfig(Picture obj, Integer id, int picType, File destFile, String projectPath,String type,Integer companyId,Integer recruitmentId){
		ResultDto<BannerConfig> dto = new ResultDto<BannerConfig>();
		BannerConfig bc = iBannerConfigMapper.getById(id);
		bc.setPicUrl(projectPath+File.separator+"fileupload/banner/"+type+"_"+id+File.separator+destFile.getName());
		int count = 0;
		try {
			count = iBannerConfigMapper.updateById(bc.getId(), bc);
			if(count==1){
				dto.setData(bc);
				dto.setResult("S");
			}else{
				dto.setErrorMsg("保存失败");
				dto.setResult("F");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto ;
	}

	@Override
	public Page loadPictureList(Page page, PictureRequestForm form) {
		int startRow  = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iPictureMapper.queryCount(form);
		List<Picture> list = iPictureMapper.queryPage(startRow, endRow, form);
		page.setRecords(count);
		page.setResultList(list);
		return page;
	}

	@Override
	public ResultDto<String> uploadBannerPicture(HttpServletRequest request) {
		ResultDto<String> dto = new ResultDto<String>();
		String sourceType = request.getParameter("sourceType");
		File destFile = null;
		// 转换为文件类型的request
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获取对应file对象
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Iterator<String> fileIterator = multipartRequest.getFileNames();
        String requestURL = request.getRequestURL().toString();
        String projectPath = requestURL.replace("/fileUpload/uploadBannerPic","");
        String path="/Users/xieshuai/fileupload/";
        path += sourceType+File.separator;
        File dir = new File(path);
        if(!dir.exists()){
        	dir.mkdirs();
        }
        //文件写入本地路径
        while (fileIterator.hasNext()) {
            String fileKey = fileIterator.next();
            logger.debug("文件名为：" + fileKey);
            // 获取对应文件
            MultipartFile multipartFile = fileMap.get(fileKey);
            String contentType = multipartFile.getContentType();
            String fileType = contentType.substring(contentType.indexOf("/") + 1);
            String fileName = new Date().getTime() + "." + fileType;
            destFile = new File(dir.getAbsolutePath()+File.separator+fileName);
            try {
				multipartFile.transferTo(destFile);
			} catch (IllegalStateException e) {
				logger.error("图片文件状态异常");
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("图片文件输入输出异常");
				e.printStackTrace();
			}
        }
        String picUrl = projectPath+File.separator+"fileupload/banner"+File.separator+destFile.getName();
		dto.setData(picUrl);
		dto.setResult("S");
        return dto;
	}

}
