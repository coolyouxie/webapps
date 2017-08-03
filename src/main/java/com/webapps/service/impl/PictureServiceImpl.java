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
		if("company".equals(type)){
			picType = 1;
		}
		// 转换为文件类型的request
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获取对应file对象
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Iterator<String> fileIterator = multipartRequest.getFileNames();

        // 获取项目的相对路径（http://localhost:8080/file）
        String requestURL = request.getRequestURL().toString();
        String prePath = requestURL.substring(0, requestURL.indexOf("http://localhost:8080/webapps/"));
        while (fileIterator.hasNext()) {
            String fileKey = fileIterator.next();
            logger.debug("文件名为：" + fileKey);

            // 获取对应文件
            MultipartFile multipartFile = fileMap.get(fileKey);
            if (multipartFile.getSize() != 0L) {
                validateImage(multipartFile);
                // 调用saveImage方法保存
                Picture file = saveImage(multipartFile);
                file.setPicUrl(prePath);
                file.setType(picType);
                file.setFkId(id);
            }
        }
        
		if(obj!=null){
			if(obj.getId()==null){
				int count = iPictureMapper.insert(obj);
				return count ;
			}else{
				int count = iPictureMapper.updateById(obj.getId(), obj);
				return count ;
			}
		}
		return 0;
	}
	
	private Picture saveImage(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        logger.debug("文件原始名称为:" + originalFilename);

        String contentType = image.getContentType();
        String type = contentType.substring(contentType.indexOf("/") + 1);
        String fileName = new Date().getTime() + new Random().nextInt(100) + "." + type;
        // 封装了一个简单的file对象，增加了几个属性
        Picture picture = new Picture();
        picture.setType(1);
        logger.debug("文件保存路径：" + "d:/test");
        // 通过org.apache.commons.io.FileUtils的writeByteArrayToFile对图片进行保存
        FileUtils.writeByteArrayToFile(new File("D:/test"+File.separator+fileName), image.getBytes());
        return picture;
    }

    private void validateImage(MultipartFile image) {
    	
    }

}
