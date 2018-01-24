package com.webapps.common.constant;

public class ExcelHeaders {
	
	public static final String[] EXPORT_ENTRY_STATISTICS_HEADERS = new String[]{"序号","会员姓名","联系方式","报名公司","发布标题","身份证号","报名日期","状态"};
	
	public static final String[] EXPORT_ENTRY_STATISTICS_FIELDS = new String[]{"index","userName","userMobile","companyName","recruitmentTitle","userIdCardNo","createTimeStr","stateStr"};

	public static final String[] EXPORT_RECRUIT_PROCESS_HEADERS = new String[]{"序号","会员姓名","联系方式","状态","报名公司","报名时间","入职时间","期满时间","招聘员","交接人"};

	public static final String[] EXPORT_RECRUIT_PROCESS_FIELDS = new String[]{"index","userName","userMobile","stateStr","companyName","createTimeStr","entryDateStr","expireDateStr","talkerName","oldTalkerName"};
}
