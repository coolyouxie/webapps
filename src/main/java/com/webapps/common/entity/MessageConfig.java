package com.webapps.common.entity;

public class MessageConfig extends Entity {
	
	private String title;
	
	private String message;
	
	private Integer type;
	
	private Integer fkId;
	
	private Integer belongType;
	
	private Company company;
	
	private Recruitment recruitment;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFkId() {
		return fkId;
	}

	public void setFkId(Integer fkId) {
		this.fkId = fkId;
	}

	public Integer getBelongType() {
		return belongType;
	}

	public void setBelongType(Integer belongType) {
		this.belongType = belongType;
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

}
