package com.webapps.common.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by xieshuai on 2017-6-27.
 */
public class Company extends Entity{

    private String name;
    private String address;
    private String telephone;
    private String mobile;
    private String email;
    private int industryId;
    private String enterpriseLegalPerson;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String briefs;
    private List<Picture> pictures;
    private String environment;
    private String contactName;
    
    private String companySize;
    
    private String province;
    
    private String city;
    
    private String area;
    
    /**
     * 公司性质
     */
    private String type;
    
    /**
     * 是否作为banner展示
     */
    private Integer isBanner;
    
    private BannerConfig bannerConfig;
    
    private Integer isMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getEnterpriseLegalPerson() {
        return enterpriseLegalPerson;
    }

    public void setEnterpriseLegalPerson(String enterpriseLegalPerson) {
        this.enterpriseLegalPerson = enterpriseLegalPerson;
    }

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getBriefs() {
		return briefs;
	}

	public void setBriefs(String briefs) {
		this.briefs = briefs;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public Integer getIsBanner() {
		return isBanner;
	}

	public void setIsBanner(Integer isBanner) {
		this.isBanner = isBanner;
	}

	public BannerConfig getBannerConfig() {
		return bannerConfig;
	}

	public void setBannerConfig(BannerConfig bannerConfig) {
		this.bannerConfig = bannerConfig;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getIsMessage() {
		return isMessage;
	}

	public void setIsMessage(Integer isMessage) {
		this.isMessage = isMessage;
	}

}
