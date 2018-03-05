package com.webapps.common.form;

import com.webapps.common.entity.PromotionConfig;

import java.io.Serializable;

public class PromotionConfigRequestForm extends PromotionConfig implements RequestForm, Serializable {

    private static final long serialVersionUID = -7915557175531200072L;

    private String effectiveDateStart;

    private String effectiveDateEnd;

    private String expiryDateStart;

    private String expiryDateEnd;

    private String effectiveDateStr;

    private String expiryDateStr;

    public String getEffectiveDateStart() {
        return effectiveDateStart;
    }

    public void setEffectiveDateStart(String effectiveDateStart) {
        this.effectiveDateStart = effectiveDateStart;
    }

    public String getEffectiveDateEnd() {
        return effectiveDateEnd;
    }

    public void setEffectiveDateEnd(String effectiveDateEnd) {
        this.effectiveDateEnd = effectiveDateEnd;
    }

    public String getExpiryDateStart() {
        return expiryDateStart;
    }

    public void setExpiryDateStart(String expiryDateStart) {
        this.expiryDateStart = expiryDateStart;
    }

    public String getExpiryDateEnd() {
        return expiryDateEnd;
    }

    public void setExpiryDateEnd(String expiryDateEnd) {
        this.expiryDateEnd = expiryDateEnd;
    }

    public String getEffectiveDateStr() {
        return effectiveDateStr;
    }

    public void setEffectiveDateStr(String effectiveDateStr) {
        this.effectiveDateStr = effectiveDateStr;
    }

    public String getExpiryDateStr() {
        return expiryDateStr;
    }

    public void setExpiryDateStr(String expiryDateStr) {
        this.expiryDateStr = expiryDateStr;
    }
}
