package com.fullstack.springboot.controller;

import com.fullstack.springboot.dto.PaymentDto;
import com.fullstack.springboot.service.IamportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final IamportService iamportService;

    @Autowired
    public PaymentController(IamportService iamportService) {
        this.iamportService = iamportService;
    }

    @PostMapping("/payment")
    public ResponseEntity<String> handlePayment(@RequestBody PaymentDto paymentDto) {
        try {
            // 결제 검증을 위해 Iamport API 호출
            boolean isVerified = iamportService.verifyPayment(paymentDto);
            if (isVerified) {
                // 결제 처리 후 데이터 저장 등의 추가 작업
                return ResponseEntity.ok("결제 완료");
            } else {
                return ResponseEntity.status(400).body("결제 검증 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }
}
