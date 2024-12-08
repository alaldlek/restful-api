package test.restApi.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItems> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setMember(Member member){
        this.member = member;
        member.getOrder().add(this);
    }

    public void addOrderItems(OrderItems orderItems){
        this.orderItems.add(orderItems);
        orderItems.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //생성 매서드
    public static Order createOrder(Member member, Delivery delivery, OrderItems... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItems orderItem : orderItems){
            order.addOrderItems(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setDate(LocalDateTime.now());

        return order;
    }

    //비지니스 로직
    /**
     * 주문취소
     */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("배송이 이미 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.cancel);
        for (OrderItems orderItem : orderItems){
            orderItem.cancel();
        }
    }

    /**
     * 전체 주문 가격
     */
    public int getTotalPrice(){
        int totalPrice = orderItems.stream()
                .mapToInt(OrderItems::getTotalPrice)
                .sum();

        return totalPrice;
    }
}
