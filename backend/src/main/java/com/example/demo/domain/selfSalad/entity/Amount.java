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
