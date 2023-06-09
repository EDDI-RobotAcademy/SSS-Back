package com.example.demo.domain.utility.password;

import com.example.demo.domain.utility.encrypt.EncryptionUtil;

import javax.persistence.AttributeConverter;

public class PasswordHashConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return EncryptionUtil.generateHash(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
