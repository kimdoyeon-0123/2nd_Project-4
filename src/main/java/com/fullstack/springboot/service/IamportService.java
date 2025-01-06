package com.fullstack.springboot.service;

import com.fullstack.springboot.dto.PaymentDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Base64;

@Service
public class IamportService {

    private static final String IMP_API_URL = "https://api.iamport.kr/users/getToken";
    private static final String IMP_KEY = "2835460187504811";  // 아이엠포트 API Key
    private static final String IMP_SECRET = "3mDWxYACIdnZgOdFAe35a3578qgYunDOkEPiA93MuTjCsOd4yfFTLZt5qQfcyJyhLm2zNrqft8uCv7bA";  // 아이엠포트 API Secret
    private static final String VERIFY_API_URL = "https://api.iamport.kr/verify";

    // 액세스 토큰을 발급받는 메서드
    public String getAccessToken() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String auth = IMP_KEY + ":" + IMP_SECRET;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            headers.set("Authorization", "Basic " + encodedAuth);

            HttpEntity<String> entity = new HttpEntity<>("", headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    IMP_API_URL, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return extractAccessToken(response.getBody());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 응답에서 access_token 추출
    private String extractAccessToken(String responseBody) {
        if (responseBody.contains("access_token")) {
            int startIndex = responseBody.indexOf("access_token") + 15;
            int endIndex = responseBody.indexOf(",", startIndex);
            if (endIndex == -1) endIndex = responseBody.length();
            return responseBody.substring(startIndex, endIndex).replaceAll("\"", "");
        }
        return null;
    }

    // 결제 검증 메서드
    public boolean verifyPayment(PaymentDto paymentDto) {
        try {
            String accessToken = getAccessToken();
            if (accessToken == null) {
                return false;  // 액세스 토큰 발급 실패 시 검증 실패
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken);  // Bearer 토큰 인증

            HttpEntity<String> entity = new HttpEntity<>(paymentDto.getImpUid(), headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    VERIFY_API_URL, HttpMethod.POST, entity, String.class);

            // 결제 검증 응답을 통해 결제 성공 여부를 판단
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                return responseBody.contains("success");
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
