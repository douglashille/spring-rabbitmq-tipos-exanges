package com.app.rabbitmq.demo.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;

@Log
@Data
@ToString
public class MyMessage {
    private int id;
    private String name;

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.writeValue(new File("target/car.json"), car);
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            log.info("Erro na conversão JSON" + e.getLocalizedMessage());
        }

        return "";
    }
}
