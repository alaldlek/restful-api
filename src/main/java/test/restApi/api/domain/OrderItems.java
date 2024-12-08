package test.restApi.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import test.restApi.api.domain.item.Item;

@Entity
@Getter @Setter
public class OrderItems {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    //생성 메서드
    public static OrderItems createOrderItems(Item item, int orderPrice, int count){
        OrderItems orderItems = new OrderItems();
        orderItems.setItem(item);
        orderItems.setOrderPrice(orderPrice);
        orderItems.setCount(count);

        item.removeStock(count);

        return orderItems;
    }

    //비지니스로직
    public void cancel(){
        getItem().addStock(count);
    }

    //주문 총 가격
    public int getTotalPrice() {
        int totalPrice = 0;

        totalPrice = getOrderPrice() * getCount();

        return totalPrice;
    }
}
