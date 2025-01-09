package com.fullstack.springboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import com.fullstack.springboot.model.Payment;
import com.google.gson.Gson;
import com.siot.IamportRestClient.response.AccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstack.springboot.model.IamportResponse;
import com.fullstack.springboot.model.IamportResponseWrapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class IamportService {

    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secret}")
    private String apiSecret;

    private final RestTemplate restTemplate;

    public IamportService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 액세스 토큰을 가져오는 메서드
    private String getAccessToken() {
        try {
            String url = "https://api.iamport.kr/users/getToken";
            String requestBody = String.format(
                "{\"imp_key\":\"%s\",\"imp_secret\":\"%s\"}", apiKey, apiSecret
            );

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("response")) {
                Map<String, Object> responseData = (Map<String, Object>) responseBody.get("response");
                return (String) responseData.get("access_token");
            } else {
                throw new RuntimeException("액세스 토큰을 얻을 수 없습니다.");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("액세스 토큰 요청 중 오류 발생: " + e.getMessage(), e);
        }
    }

    
    
    
    
    // 결제 요청 처리
    public IamportResponse requestPayment(Payment payment) {
        try {
            // 액세스 토큰을 가져오는 메서드 호출
            String accessToken = getAccessToken();
            System.out.println("Access Token: " + accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<Payment> entity = new HttpEntity<>(payment, headers);
            String url = "https://api.iamport.kr/payments/prepare"; // 결제 준비 API

            // API 호출
            System.out.println("Sending request to IAMport API: " + url);
            ResponseEntity<IamportResponseWrapper> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, IamportResponseWrapper.class
            );

            // 응답 상태 코드 확인
            System.out.println("API Response Status Code: " + response.getStatusCode());

            IamportResponseWrapper wrapper = response.getBody();
            if (wrapper != null) {
                System.out.println("IAMport Response Wrapper: " + wrapper);
                if (wrapper.getResponse() != null) {
                    System.out.println("IAMport Response: " + wrapper.getResponse());
                    return wrapper.getResponse(); // 정상 응답 반환, imp_uid가 포함됨
                } else {
                    // 응답에서 오류 메시지를 추출하는 부분
                    System.out.println("Error: " + wrapper.toString());
                    throw new RuntimeException("결제 요청에 대한 응답이 유효하지 않습니다.");
                }
            } else {
                throw new RuntimeException("결제 요청에 대한 응답이 null입니다.");
            }
        } catch (Exception e) {
            System.err.println("Error during payment request: " + e.getMessage());
            throw new RuntimeException("결제 요청 처리 중 오류 발생: " + e.getMessage(), e);
        }
        
    }




   
    // 결제 상태 조회 (검증 없이 상태만 확인)
    public IamportResponse getPaymentStatus(String impUid) {
        try {
            String accessToken = getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            // 결제 상태 조회 API
            String url = String.format("https://api.iamport.kr/payments/%s", impUid);

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<IamportResponseWrapper> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, IamportResponseWrapper.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                IamportResponseWrapper wrapper = response.getBody();
                if (wrapper != null && wrapper.getResponse() != null) {
                    return wrapper.getResponse();
                } else {
                    throw new RuntimeException("결제 상태 조회 응답이 유효하지 않습니다.");
                }
            } else {
                throw new RuntimeException("결제 상태 조회 요청 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("결제 상태 조회 중 오류 발생: " + e.getMessage(), e);
        }
    }





    // 결제 취소
    public IamportResponse cancelPayment(String impUid, Double amount) {
    	System.out.println("5555555555555555555");
        try {
            String accessToken = getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            Map<String, Object> requestParams = Map.of(
                "imp_uid", impUid,
                "amount", amount
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestParams, headers);
            String url = "https://api.iamport.kr/payments/cancel"; // 결제 취소 API

            ResponseEntity<IamportResponseWrapper> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, IamportResponseWrapper.class
            );
            
            IamportResponseWrapper wrapper = response.getBody();
            if (wrapper != null && wrapper.getResponse() != null) {
                return wrapper.getResponse();
            } else {
                throw new RuntimeException("결제 취소 요청에 대한 응답이 유효하지 않습니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException("결제 취소 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
