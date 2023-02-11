package com.poc.ecommerce.shareddomain.events;

import lombok.Data;
import org.springframework.core.serializer.Serializer;

import java.io.IOException;
import java.io.OutputStream;

@Data
public class OrderCancelledEvent implements Serializer<Object> {
    private String userId;
    private String orderId;

    @Override
    public void serialize(Object object, OutputStream outputStream) throws IOException {

    }
}
