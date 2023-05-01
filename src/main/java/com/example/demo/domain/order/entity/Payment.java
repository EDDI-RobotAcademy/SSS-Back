package com.example.demo.domain.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String merchant_uid; // 상점 고유 번호

    @Column(nullable = false)
    private String imp_uid; // 아임포트에서 부여한 결제 거래 고유 ID

    @Column(nullable = false)
    private String pay_method; // 결제 수단

    @Column(nullable = false)
    private Long paid_amount; // 결제된 금액

    @Column(nullable = false)
    private Date paid_at; // 결제 일시

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderInfo_id")
    private OrderInfo orderInfo;

    public Payment(String merchantId, String impUid, String payMethod,
                   Long paidAmount, OrderInfo myOrderInfo) {
        this.merchant_uid = merchantId;
        this.imp_uid = impUid;
        this.pay_method = payMethod;
        this.paid_amount = paidAmount;
        this.paid_at = new Date();
        this.orderInfo = myOrderInfo;
    }
}
