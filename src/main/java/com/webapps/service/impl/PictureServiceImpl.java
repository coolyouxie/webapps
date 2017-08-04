package com.webapps.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.webapps.common.entity.Picture;
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
	public int savePicture(Picture obj,HttpServletRequest request) throws Exception {
		String type = request.getParameter("type");
		Integer id = Integer.valueOf(request.getParameter("id"));
		int picType = 0;
		File destFile = null;
		Picture file = new Picture();
		if("company".equals(type)){
			picType = 1;
		}
		// 转换为文件类型的request
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获取对应file对象
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Iterator<String> fileIterator = multipartRequest.getFileNames();
        String requestURL = request.getRequestURL().toString();
        String projectPath = requestURL.replace("/fileUpload/pictureUpload","");
        String path="/Users/xieshuai/fileupload/company"+File.separator;
        File dir = new File(path+"company_"+id);
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
            multipartFile.transferTo(destFile);
        }
        file.setDataState(1);
        file.setType(picType);
        file.setPicUrl(projectPath+File.separator+"fileupload/company_"+id+File.separator+destFile.getName());
        file.setFkId(id);
		if(file.getId()==null){
			int count = iPictureMapper.insert(file);
			return count ;
		}else{
			int count = iPictureMapper.updateById(obj.getId(), file);
			return count ;
		}
	}

}
