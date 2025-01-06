package com.fullstack.springboot.dto;

public class PaymentDto {

    private String impUid;
    private String merchantUid;
    private String channelKey;
    private double amount;
    private String buyerEmail;
    private String buyerName;
    private String buyerTel;
    private String buyerAddr;
    private String buyerPostcode;

    // Getters and Setters
    public String getImpUid() {
        return impUid;
    }

    public void setImpUid(String impUid) {
        this.impUid = impUid;
    }

    public String getMerchantUid() {
        return merchantUid;
    }

    public void setMerchantUid(String merchantUid) {
        this.merchantUid = merchantUid;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public String getBuyerAddr() {
        return buyerAddr;
    }

    public void setBuyerAddr(String buyerAddr) {
        this.buyerAddr = buyerAddr;
    }

    public String getBuyerPostcode() {
        return buyerPostcode;
    }

    public void setBuyerPostcode(String buyerPostcode) {
        this.buyerPostcode = buyerPostcode;
    }
}
