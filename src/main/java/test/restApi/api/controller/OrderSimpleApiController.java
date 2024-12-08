package test.restApi.api.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.restApi.api.domain.Address;
import test.restApi.api.domain.Order;
import test.restApi.api.domain.OrderService.OrderService;
import test.restApi.api.domain.OrderStatus;
import test.restApi.api.dto.OrderDto;
import test.restApi.api.domain.repository.OrderSearch;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * xToOne(ManyToOne, OneToOne) 관계 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 *
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){

        List<Order> orders = orderService.findOrders(new OrderSearch());

        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderDto> ordersV2(){

        return orderService.findOrders(new OrderSearch()).stream().map(OrderDto::new).toList();
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderDto> ordersV3(){

        return orderService.findAllMembersDelivery().stream().map(OrderDto::new).toList();
    }

    @GetMapping("/api/v4/simple-orders")
    public List<test.restApi.api.dto.OrderDto> ordersV4(){
        List<test.restApi.api.dto.OrderDto> orderDto = orderService.findOrderDto();

        return orderDto;
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;

        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}
