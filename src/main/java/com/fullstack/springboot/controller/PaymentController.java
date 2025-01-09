package com.fullstack.springboot.controller;

import com.fullstack.springboot.model.IamportResponse;
import com.fullstack.springboot.model.Payment;
import com.fullstack.springboot.service.IamportService;
import com.mysql.cj.log.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class PaymentController {

    @Autowired
    private IamportService iamportService; // IAMport 결제 서비스
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    
    
    
    // 결제 요청 API
    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Payment paymentRequest) {
        Map<String, Object> response = new HashMap<>();
        
        
        try {
            // IAMport 결제 API 호출
            IamportResponse paymentResponse = iamportService.requestPayment(paymentRequest);
            
            if (paymentResponse != null) {
                // 결제 요청이 성공적으로 처리되면 imp_uid와 merchant_uid를 반환
                response.put("imp_uid", paymentResponse.getImp_uid());
                response.put("merchant_uid", paymentResponse.getMerchant_uid());
                response.put("status", "success");
            } else {
                response.put("status", "failed");
                response.put("message", "결제 요청 처리에 실패했습니다.");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "결제 요청 처리 중 오류 발생: " + e.getMessage());
        }
        
        System.out.println(paymentRequest.getBuyer_email());
        logger.info("결제 요청 수신: {}", paymentRequest.getBuyer_name());
        logger.info("결제 요청 응답: {}", response);
        
        return ResponseEntity.ok(response);
    }

    // 결제 확인 API
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyPayment(@RequestBody Payment paymentRequest) {
    	
        Map<String, Object> response = new HashMap<>();
        
        String impUid = paymentRequest.getImp_uid();
        String merchantUid = paymentRequest.getMerchant_uid();
        
        System.out.println(impUid);
        System.out.println(merchantUid);
        
        
        
        // imp_uid와 merchant_uid가 제대로 전달되었는지 확인
        if (impUid == null || merchantUid == null) {
            response.put("status", "error");
            response.put("message", "imp_uid 또는 merchant_uid가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }
        
        try {
            // 결제 상태만 확인 (검증 없이 상태만 확인)
            IamportResponse paymentResponse = iamportService.getPaymentStatus(impUid);

            if (paymentResponse != null) {
                response.put("imp_uid", paymentResponse.getImp_uid());
                response.put("merchant_uid", paymentResponse.getMerchant_uid());
                response.put("status", "success");
                logger.info("결제 확인 성공: imp_uid = {}, merchant_uid = {}", paymentResponse.getImp_uid(), paymentResponse.getMerchant_uid());
            } else {
                response.put("status", "failed");
                response.put("message", "결제 상태 확인에 실패했습니다.");
                logger.error("결제 상태 확인 실패: imp_uid = {}, merchant_uid = {}", impUid, merchantUid);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "결제 상태 확인 중 오류 발생: " + e.getMessage());
            logger.error("결제 상태 확인 처리 중 오류 발생", e);
        }

        return ResponseEntity.ok(response);
    }

    
    
    
    
    
    // 결제 취소 API
 // 결제 취소 API
    @PostMapping("/cancel")
    public ResponseEntity<Map<String, Object>> cancelPayment(@RequestBody Map<String, Object> cancelRequest) {
        Map<String, Object> response = new HashMap<>();

        String impUid = (String) cancelRequest.get("imp_uid");
        Object amountObject = cancelRequest.get("amount");

        // amount를 Integer나 Double로 받을 수 있기 때문에 이를 Double로 변환
        Double amount = null;
        if (amountObject instanceof Integer) {
            amount = ((Integer) amountObject).doubleValue();
        } else if (amountObject instanceof Double) {
            amount = (Double) amountObject;
        }

        // imp_uid와 amount 값이 제대로 전달되었는지 확인
        if (impUid == null || impUid.isEmpty()) {
            response.put("status", "error");
            response.put("message", "imp_uid가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        if (amount == null || amount <= 0) {
            response.put("status", "error");
            response.put("message", "취소 금액이 유효하지 않습니다.");
            return ResponseEntity.ok(response);
        }

        try {
            // 결제 취소 처리
            IamportResponse cancelResponse = iamportService.cancelPayment(impUid, amount);

            if (cancelResponse != null) {
                // 결제 취소 성공
                response.put("imp_uid", cancelResponse.getImp_uid());
                response.put("status", "success");
                logger.info("결제 취소 성공: imp_uid = {}, 취소 금액 = {}", cancelResponse.getImp_uid(), amount);
            } else {
                // 결제 취소 실패
                response.put("status", "failed");
                response.put("message", "결제 취소에 실패했습니다.");
                logger.error("결제 취소 실패: imp_uid = {}, 취소 금액 = {}", impUid, amount);
            }
        } catch (Exception e) {
            // 예외 발생 시 처리
            response.put("status", "error");
            response.put("message", "결제 취소 처리 중 오류 발생: " + e.getMessage());
            logger.error("결제 취소 처리 중 오류 발생", e);
        }

        return ResponseEntity.ok(response);
    }


}