package com.example.demo.domain.order.service.request;

import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.order.entity.Payment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentRequest {

    private final String merchant_id; // 상점 고유 번호

    private final String imp_uid; // 아임포트에서 부여한 결제 거래 고유 ID

    private final String pay_method; // 결제 수단

    private final Long paid_amount; // 결제된 금액
    

    public Payment toPayment(OrderInfo myOrderInfo){
        return new Payment(merchant_id, imp_uid, pay_method,
                           paid_amount, myOrderInfo);
    }

}
