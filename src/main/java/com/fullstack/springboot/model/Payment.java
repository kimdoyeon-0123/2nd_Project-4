package com.fullstack.springboot.model;

public class Payment {
    private String imp_uid;     // IAMport에서 결제 식별자
    private String merchant_uid; // 사용자가 생성한 주문 식별자
    private String buyer_name;   // 구매자 이름
    private String buyer_email;  // 구매자 이메일
    private Double amount;       // 결제 금액
    // 다른 필요한 필드들 (예: 결제 방식, 카드 정보 등)

    // Getters and Setters
    public String getImp_uid() {
        return imp_uid;
    }

    public void setImp_uid(String imp_uid) {
        this.imp_uid = imp_uid;
    }

    public String getMerchant_uid() {
        return merchant_uid;
    }

    public void setMerchant_uid(String merchant_uid) {
        this.merchant_uid = merchant_uid;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
