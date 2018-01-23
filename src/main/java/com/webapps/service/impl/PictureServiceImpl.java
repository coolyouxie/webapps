package com.webapps.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.PictureRequestForm;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.mapper.IPictureMapper;
import com.webapps.service.IPictureService;

@Service
@Transactional
public class PictureServiceImpl implements IPictureService {
	
	Logger logger = Logger.getLogger(PictureServiceImpl.class);
	
	private static final String bankCardDir = "bankCardPics";
	private static final String idCardDir = "idCardPics";
	
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
        String projectPath = (String) PropertiesUtil.getProperty("WebApp_Path");
        String path=(String) PropertiesUtil.getProperty("FileUpload_Path");
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
		int rows = page.getRows();
		int count = iPictureMapper.queryCount(form);
		List<Picture> list = iPictureMapper.queryPage(startRow, rows, form);
		page.setRecords(count);
		page.setResultList(list);
		page.countRecords(count);
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
				if(CollectionUtils.isNotEmpty(list)){
					iPictureMapper.batchDeleteInLogic(list);
				}
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class, RuntimeException.class,MyBatisSystemException.class})
	public ResultDto<String> uploadImgForApp(MultipartFile[] files, String userId) {
		ResultDto<String> dto = new ResultDto<String>();
		String failedPics = "";
		try {
			if(files!=null&&files.length>0){
				for(int i=0;i<files.length;i++){
					MultipartFile file = files[i];
					String contentType = file.getContentType();
			        String fileType = contentType.substring(contentType.indexOf("/") + 1);
					String originalName = file.getOriginalFilename();
					//bankCard_1:银行卡正面，bankCard_2:银行卡反面,idCard_1:身份证正面,idCard_2:身份证反面，具体内容请参考下面这个数组中的内容
					String[] fileNameAndType = originalName.split("_");
					String dirName = fileNameAndType[0];//文件名作为文件夹名使用
					String type = fileNameAndType[1];
					File destFile = null;
					String destFilePath = (String)PropertiesUtil.getProperty("FileUpload_Path");
					File dir = null;
					if(StringUtils.isNotBlank(dirName)){
						//取文件名作为文件夹名
						dir = new File(destFilePath + dirName);
						if(!dir.exists()){
				        	dir.mkdirs();
				        }
					}
					//组装目标文件路径
					destFilePath =  dir.getAbsolutePath() + File.separator + new Date().getTime() + "." + fileType;
					//保存源文件到目标文件
					destFile = new File(destFilePath);
					file.transferTo(destFile);
					String projectPath = (String) PropertiesUtil.getProperty("WebApp_Path");
					String picUrl = projectPath+"fileupload/"+dirName+File.separator+destFile.getName();
					dto = saveBankAndIdCardPic(picUrl, dirName, type, userId);
					if("F".equals(dto.getResult())){
						failedPics += dirName+"_"+type+",";
					}
				}
			}
			String errorMsg = "上传";
			if(StringUtils.isNotBlank(failedPics)){
				if(failedPics.contains("bankCard_1")){
					errorMsg += " 银行卡正面图片 ";
				}
				if(failedPics.contains("bankCard_2")){
					errorMsg += " 银行卡反面图片  ";
				}
				if(failedPics.contains("idCard_1")){
					errorMsg += " 身份证正面图片 ";
				}
				if(failedPics.contains("idCard_2")){
					errorMsg += " 身份证反面图片 ";
				}
				errorMsg +=" 失败";
				dto.setErrorMsg(errorMsg);
				dto.setResult("F");
				return dto;
			}
			dto.setResult("S");
			return dto;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("保存图片时异常，请稍后重试");
			e.printStackTrace();
			dto.setErrorMsg("保存图片时异常，请稍后重试");
			dto.setResult("F");
			return dto;
		}
	}

	@Override
	public ResultDto<List<Picture>> queryUserPictures(Integer userId) {
		ResultDto<List<Picture>> dto = new ResultDto<List<Picture>>();
		try {
			List<Picture> list = iPictureMapper.queryUserPictures(userId);
			if(CollectionUtils.isEmpty(list)){
				dto.setResult("E");
				dto.setErrorMsg("未上传图片");
				return dto;
			}
			if(list.size()<3){
				dto.setData(list);
				dto.setResult("L");
				dto.setErrorMsg("部分图片未上传");
				return dto;
			}
			dto.setResult("S");
			dto.setData(list);
			return dto;
		} catch (Exception e) {
			logger.error("context",e);
			dto.setResult("F");
			dto.setErrorMsg("查询用户图片异常，请稍后再试。");
			return dto;
		}
	}

	@Override
	public ResultDto<String> deleteById(Integer id) {
		ResultDto<String> dto = new ResultDto<>();
		try {
			iPictureMapper.deleteByIdInLogic(id);
			dto.setResult("S");
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			return dto;
		}
	}

	private ResultDto<String> saveBankAndIdCardPic(String picUrl,String fileName,String type,String userId)throws Exception{
		ResultDto<String> dto = new ResultDto<String>();
		Integer uId = Integer.valueOf(userId);
		int count = 0;
		String remark = null;
		if(fileName.contains("bankCard")){
			if("1".equals(type)){
				remark = "银行卡正面";
				count = saveOrUpdateBandAndIdCardPic(picUrl, uId, count,51,remark);
			}else{
				remark = "银行卡反面";
				count = saveOrUpdateBandAndIdCardPic(picUrl, uId, count,52,remark);
			}
		}else if(fileName.contains("idCard")){
			if("1".equals(type)){
				remark = "身份证正面";
				count = saveOrUpdateBandAndIdCardPic(picUrl, uId, count,41,remark);
			}else{
				remark = "身份证反面";
				count = saveOrUpdateBandAndIdCardPic(picUrl, uId, count,42,remark);
			}
		}
		
		if(count==1){
			dto.setResult("S");
		}else{
			dto.setErrorMsg("保存用户身份证或银行卡图片失败");
			dto.setResult("F");
		}
		return dto;
	}

	private int saveOrUpdateBandAndIdCardPic(String picUrl, Integer uId,int count,int type,String remark) throws Exception {
		List<Picture> pList = iPictureMapper.queryListByFkIdAndType(uId,type);
		 Picture p = null;
		if(CollectionUtils.isEmpty(pList)){
			count = insertPicture(picUrl, uId, type, remark);
			return count;
		}
		if(pList.size()==1){
			p = pList.get(0);
			p.setRemark(remark);
			p.setType(type);
			p.setPicUrl(picUrl);
			p.setUpdateTime(new Date());
			count = iPictureMapper.updateById(p.getId(), p);
		}else{
			//删除之前全部的老数据，再插入新数据
			iPictureMapper.batchDeleteInLogic(pList);
			count = insertPicture(picUrl, uId, type, remark);
		}
		return count;
	}

	private int insertPicture(String picUrl, Integer uId, int type, String remark) {
		int count;
		Picture p;
		p = new Picture();
		p.setRemark(remark);
		p.setType(type);
		p.setPicUrl(picUrl);
		p.setCreateTime(new Date());
		p.setDataState(1);
		p.setFkId(uId);
		count = iPictureMapper.insert(p);
		return count;
	}

}
