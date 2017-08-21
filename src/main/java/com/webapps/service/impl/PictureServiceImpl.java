package com.webapps.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.PictureRequestForm;
import com.webapps.common.utils.PropertyUtil;
import com.webapps.mapper.IPictureMapper;
import com.webapps.service.IPictureService;

@Service
@Transactional
public class PictureServiceImpl implements IPictureService {
	
	Logger logger = Logger.getLogger(PictureServiceImpl.class);
	
	@Autowired
	private IPictureMapper iPictureMapper;

	@Override
	public List<Picture> getByFkId(Integer fkId) throws Exception {
		List<Picture> list = iPictureMapper.queryListByFkId(fkId);
		return list;
	}

	@Override
	public ResultDto<String> uploadCompanyPicture(HttpServletRequest request) {
		ResultDto<String> dto = uploadPicture(request);
        return dto;
	}
	
	private ResultDto<String> uploadPicture(HttpServletRequest request){
		ResultDto<String> dto = new ResultDto<String>();
		String sourceType = request.getParameter("sourceType");
		File destFile = null;
		// 转换为文件类型的request
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获取对应file对象
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Iterator<String> fileIterator = multipartRequest.getFileNames();
        String projectPath = (String) PropertyUtil.getProperty("WebApp_Path");
        String path=(String) PropertyUtil.getProperty("FileUpload_Path");
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
        String picUrl = projectPath+File.separator+"fileupload/"+sourceType+File.separator+destFile.getName();
        logger.info("文件路径："+picUrl);
		dto.setData(picUrl);
		dto.setResult("S");
		return dto;
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
		ResultDto<String> dto = uploadPicture(request);
        return dto;
	}

	@Override
	public ResultDto<Picture> saveCompanyPicture(PictureRequestForm form) {
		ResultDto<Picture> dto = new ResultDto<Picture>();
		//插入新图片前将原来相同类型的图片记录置为无效
		try {
			List<Picture> list = iPictureMapper.queryListByFkIdAndType(form.getFkId(),form.getType());
			if(form.getType()==1){
				iPictureMapper.batchDeleteInLogic(list);
			}else if(form.getType()==2&&list.size()>=2){
				iPictureMapper.deleteInLogicByPicture(list.get(list.size()-1));
			}
		} catch (Exception e) {
			logger.error("设置相同类型的图片状态为无效时异常："+e.getMessage());
			dto.setErrorMsg("设置相同类型的图片状态为无效时异常，请稍后再试");
			dto.setResult("F");
			return dto;
		}
		form.setDataState(1);
		form.setCreateTime(new Date());
		int count = iPictureMapper.insert(form);
		if(count==1){
			dto.setData(form);
			dto.setResult("S");
		}else{
			dto.setErrorMsg("图片保存失败，请稍后重试");
			dto.setResult("F");
			logger.error(dto.getErrorMsg());
		}
		return dto;
	}

	@Override
	public ResultDto<Picture> savePicture(PictureRequestForm form) {
		
		return null;
	}

}
