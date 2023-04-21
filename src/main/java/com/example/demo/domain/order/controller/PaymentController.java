package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.service.OrderInfoService;
import com.example.demo.domain.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    final private OrderInfoService orderInfoService;
    final private RedisService redisService;
    @PostMapping("/kakaoPay")
    public String kakaoPayReady() throws IOException {

        URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
        try {
            // 서버연결 local
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "KakaoAK ede7cdbf05ad1b46fff056970bf6569e");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setDoOutput(true);

            String params = "cid=TC0ONETIME&" +
                    "partner_order_id=partner_order_id&" +
                    "partner_user_id=partner_user_id&" +
                    "item_name=초코파이&quantity=1&total_amount=2200&vat_amount=200&tax_free_amount=0&" +
                    "approval_url=http://localhost:8080/order/kakaoPaySuccess&" +
                    "cancel_url=http://localhost:8080/order/kakaoPayCancel&" +
                    "fail_url=http://localhost:8080/order/kakaoPaySuccessFail";

            // 데이터를 전송
            OutputStream os = conn.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeBytes(params);
            dos.close();

            // 결과 번호 받음
            int responseCode = conn.getResponseCode();

            // 결과 받는애
            InputStream is;
            // 200 = 통신 완료
            if (responseCode == 200) {
                // 받는애가 정보 받음
                is = conn.getInputStream();

            } else {
                is = conn.getErrorStream();
            }
            // 결과를 읽는애
            InputStreamReader isr = new InputStreamReader(is);

            // 형변환 할줄 아는애
            BufferedReader br = new BufferedReader(isr);

            // 결제 고유 정보
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
