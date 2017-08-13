package com.webapps.service.impl;

import java.io.File;
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

import com.webapps.common.entity.BannerConfig;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Picture;
import com.webapps.common.entity.Recruitment;
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
	public int savePicture(Picture obj,HttpServletRequest request) throws Exception {
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
            multipartFile.transferTo(destFile);
        }
        int result = 0;
		if("company".equals(sourceType)){
			picType = 1;
			result = savePicture(obj, id, picType, destFile, file, projectPath);
		}else if("banner".equals(sourceType)){
			Integer companyId = -1;
			Integer recruitmentId = -1;
			String companyIdStr = request.getParameter("companyId");
			String recruitmentIdStr = request.getParameter("recruitmentId");
			if(StringUtils.isNotBlank(companyIdStr))
				companyId = Integer.valueOf(companyIdStr);
			if(StringUtils.isNotBlank(recruitmentIdStr))
				recruitmentId = Integer.valueOf(recruitmentIdStr);
			result = saveBannerConfig(obj, id, picType, destFile, projectPath, sourceType, companyId, recruitmentId);
		}
        
        
        return result;
	}

	private int savePicture(Picture obj, Integer id, int picType, File destFile, Picture file, String projectPath)
			throws Exception {
		file.setDataState(1);
        file.setType(picType);
        file.setPicUrl(projectPath+File.separator+"fileupload/company/company_"+id+File.separator+destFile.getName());
        file.setFkId(id);
		if(file.getId()==null){
			int count = iPictureMapper.insert(file);
			return count ;
		}else{
			int count = iPictureMapper.updateById(obj.getId(), file);
			return count ;
		}
	}
	
	private int saveBannerConfig(Picture obj, Integer id, int picType, File destFile, String projectPath,String type,Integer companyId,Integer recruitmentId){
		BannerConfig bc = iBannerConfigMapper.getById(id);
		bc.setPicUrl(projectPath+File.separator+"fileupload/banner/"+type+"_"+id+File.separator+destFile.getName());
		int count = 0;
		try {
			count = iBannerConfigMapper.updateById(bc.getId(), bc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count ;
	}

}
