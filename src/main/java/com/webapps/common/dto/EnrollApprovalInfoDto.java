package com.webapps.common.dto;

import java.util.List;

import com.webapps.common.entity.Company;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.EnrollmentExtra;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.entity.User;

public class EnrollApprovalInfoDto extends Dto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8115132107129702597L;

	private Enrollment enrollment;
	
	private EnrollApproval enrollApproval;
	
	private List<EnrollmentExtra> extraList;
	
	private User user;
	
	private Company company;
	
	private Recruitment recruitment;
	
	private User approver;

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public EnrollApproval getEnrollApproval() {
		return enrollApproval;
	}

	public void setEnrollApproval(EnrollApproval enrollApproval) {
		this.enrollApproval = enrollApproval;
	}

	public List<EnrollmentExtra> getExtraList() {
		return extraList;
	}

	public void setExtraList(List<EnrollmentExtra> extraList) {
		this.extraList = extraList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Recruitment getRecruitment() {
		return recruitment;
	}

	public void setRecruitment(Recruitment recruitment) {
		this.recruitment = recruitment;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

}
