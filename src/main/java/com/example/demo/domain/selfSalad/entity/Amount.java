package com.example.demo.domain.selfSalad.entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "measure_uq_measure_name",
                columnNames = {"amountType"})
)
/**
 * amountType 컬럼에 대해 유니크 제약 조건을 설정하고, 이를 "measure_uq_measure_name"이라는 이름으로 지정
 * amountType 컬럼에는 중복된 값이 들어갈 수 없음
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AmountType amountType;

    public Amount(AmountType type) {
        this.amountType = type;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "id=" + id +
                ", amountType=" + amountType +
                '}';
    }
}
