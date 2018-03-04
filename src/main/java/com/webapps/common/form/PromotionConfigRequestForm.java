package com.webapps.common.form;

import com.webapps.common.entity.PromotionConfig;

import java.io.Serializable;

public class PromotionConfigRequestForm extends PromotionConfig implements RequestForm, Serializable {

    private static final long serialVersionUID = -7915557175531200072L;

    private String effectiveDateStart;

    private String effectiveDateEnd;

    private String expiryDateStart;

    private String expiryDateEnd;

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
}
