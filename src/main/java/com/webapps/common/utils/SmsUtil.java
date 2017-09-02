package com.webapps.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsUtil {

	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	private static String accessKeyId = "LTAIoWytea2T5OH4";
	private static String accessKeySecret = "sPs7AQ6hwKwsWunSiuq9aqTwh5jTc3";
	private static String signName = "嘉聘网";
	private static String templateCode = "SMS_89545074";

	public static Map<String,Object> sendSms(String phoneNum) throws ClientException {

		if (accessKeyId == null || accessKeySecret == null) {
			accessKeyId = (String) PropertyUtil.getProperty("ali_accessKeyId");
			accessKeySecret = (String) PropertyUtil.getProperty("ali_accessKeySecret");
			signName = (String) PropertyUtil.getProperty("project_name");
		}

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(phoneNum);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(signName);
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		String code = createRandom(true,6);
		request.setTemplateParam("{\"name\":\""+code+"\"}");

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("yourOutId");

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("request", request);
		resultMap.put("response", sendSmsResponse);
		resultMap.put("validateCode", code);
		return resultMap;
	}

	public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象
		QuerySendDetailsRequest request = new QuerySendDetailsRequest();
		// 必填-号码
		request.setPhoneNumber("17602115068,17602114371");
		// 可选-流水号
		request.setBizId(bizId);
		// 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		request.setSendDate(ft.format(new Date()));
		// 必填-页大小
		request.setPageSize(10L);
		// 必填-当前页码从1开始计数
		request.setCurrentPage(1L);

		// hint 此处可能会抛出异常，注意catch
		QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

		return querySendDetailsResponse;
	}

	/**
	 * 创建指定数量的随机字符串
	 * @param numberFlag 是否是数字
	 * @param length
	 * @return
	 */
	private static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		return retStr;
	}
	
	public static void main(String[] args) throws ClientException, InterruptedException {

		// 发短信
		String phoneNum = "17321004552";
		SendSmsResponse response = (SendSmsResponse) sendSms(phoneNum).get("response");
		System.out.println("短信接口返回的数据----------------");
		System.out.println("Code=" + response.getCode());
		System.out.println("Message=" + response.getMessage());
		System.out.println("RequestId=" + response.getRequestId());
		System.out.println("BizId=" + response.getBizId());
		//
		// Thread.sleep(3000L);
		//
		// //查明细
		// if(response.getCode() != null && response.getCode().equals("OK")) {
		// QuerySendDetailsResponse querySendDetailsResponse =
		// querySendDetails(response.getBizId());
		// System.out.println("短信明细查询接口返回数据----------------");
		// System.out.println("Code=" + querySendDetailsResponse.getCode());
		// System.out.println("Message=" +
		// querySendDetailsResponse.getMessage());
		// int i = 0;
		// for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO :
		// querySendDetailsResponse.getSmsSendDetailDTOs())
		// {
		// System.out.println("SmsSendDetailDTO["+i+"]:");
		// System.out.println("Content=" + smsSendDetailDTO.getContent());
		// System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
		// System.out.println("OutId=" + smsSendDetailDTO.getOutId());
		// System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
		// System.out.println("ReceiveDate=" +
		// smsSendDetailDTO.getReceiveDate());
		// System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
		// System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
		// System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
		// }
		// System.out.println("TotalCount=" +
		// querySendDetailsResponse.getTotalCount());
		// System.out.println("RequestId=" +
		// querySendDetailsResponse.getRequestId());
		// }

	}
}
