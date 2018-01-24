package com.webapps.common.dto;

import com.webapps.common.entity.Enrollment;

public class EnrollmentExportDto extends Enrollment {
	
	private int index;
	
	public String getUserName() {
		if(this.getUser()!=null){
			return this.getUser().getName();
		}
		return "";
	}

	public String getUserMobile() {
		if(this.getUser()!=null){
			return this.getUser().getMobile();
		}
		return "";
	}

	public String getUserIdCardNo() {
		if(this.getUser()!=null){
			return this.getUser().getIdCardNo();
		}
		return "";
	}

	public String getCompanyName() {
		if(this.getCompany()!=null){
			return this.getCompany().getName();
		}
		return "";
	}

	public String getRecruitmentTitle() {
		if(this.getRecruitment()!=null){
			return this.getRecruitment().getTitle();
		}
		return "";
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getStateStr(){
		//1已报名，20入职审核申请，21入职审核成功，22入职审核失败，
		//30全部期满审核申请，31全部期满审核通过，32全部期满审核失败，
		//4已离职，
		//50分阶段期满审核，51分阶段期满审核通过，52分阶段期满审核不通过
		if(this.getState()!=null){
			if(this.getState()==20){
				return "入职审核中";
			}else if(this.getState()==21){
				return "已入职";
			}else if(this.getState()==22){
				return "入职审核不通过";
			}else if(this.getState()==30){
				return "全部期满审核中";
			}else if(this.getState()==31){
				return "全部期满";
			}else if(this.getState()==32){
				return "全部期满审核不通过";
			}else if(this.getState()==50){
				return "分阶段期满审核中";
			}else if(this.getState()==51){
				return "分阶段期满";
			}else if(this.getState()==52){
				return "分阶段期满审核不通过";
			}else if(this.getState()==4){
				return "已离职";
			}
		}
		return "";
	}

	public String getExpireDateStr(){
		if(this.getState()!=null&&this.getState()==31){
			return getUpdateTimeStr();
		}
		return "";
	}
	
}
