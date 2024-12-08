package test.restApi.api.dto;

import lombok.Data;
import test.restApi.api.domain.Address;
import test.restApi.api.domain.Order;
import test.restApi.api.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Address address;

    public OrderDto(Long id, String name, LocalDateTime date, OrderStatus status, Address address) {
        this.orderId = id;
        this.name = name;
        this.orderDate = date;
        this.orderStatus = status;
        this.address = address;
    }
}
