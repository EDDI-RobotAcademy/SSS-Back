//package com.example.demo.domain.selfSalad.entity.convert;
//
//import com.example.demo.domain.selfSalad.entity.AmountType;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//import java.util.stream.Stream;
//
//@Converter(autoApply = true)
//public class AmountTypeConverter implements AttributeConverter<AmountType, String> {
//
//        @Override
//        public String convertToDatabaseColumn(AmountType amountType) {
//            if (amountType == null) {
//                return null;
//            }
//            return amountType.getType();
//        }
//
//        @Override
//        public AmountType convertToEntityAttribute(String code) {
//            if (code == null) {
//                return null;
//            }
//
//            return Stream.of(AmountType.values())
//                    .filter(c -> c.getType().equals(code))
//                    .findFirst()
//                    .orElseThrow(IllegalArgumentException::new);
//        }
//
//}
