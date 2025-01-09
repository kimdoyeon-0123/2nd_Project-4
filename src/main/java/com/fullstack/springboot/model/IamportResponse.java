package com.fullstack.springboot.model;

public class IamportResponse {
    private String imp_uid;     // IAMport 결제 고유 ID
    private String merchant_uid; // 사용자 생성 주문 ID
    private Double amount;       // 결제 금액
    private String status;       // 결제 상태 (예: "success", "failed")
    private String pay_method;   // 결제 방법 (예: 카드, 계좌이체 등)
    private String buyer_name;   // 구매자 이름
    private String buyer_email;  // 구매자 이메일
    private String buyer_tel;    // 구매자 전화번호
    private String error_msg;    // 오류 메시지 (결제 실패 시)
    
    
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
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

    public String getBuyer_tel() {
        return buyer_tel;
    }

    public void setBuyer_tel(String buyer_tel) {
        this.buyer_tel = buyer_tel;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
