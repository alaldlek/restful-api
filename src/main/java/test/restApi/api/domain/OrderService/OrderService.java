package test.restApi.api.domain.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.restApi.api.domain.Delivery;
import test.restApi.api.domain.Member;
import test.restApi.api.domain.Order;
import test.restApi.api.domain.OrderItems;
import test.restApi.api.domain.item.Item;
import test.restApi.api.domain.repository.*;
import test.restApi.api.dto.OrderDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     *  주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItems orderItems = OrderItems.createOrderItems(item, item.getPrice(), count);
        
        //주문생성
        Order order = Order.createOrder(member, delivery, orderItems);

        //주문저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     *  취소
     */
    @Transactional
    public void cancelOrder(Long id){
        Order order = orderRepository.findOne(id);
        order.cancel();
    }

    /**
     *  검색
     */
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }

    public List<Order> findAllMembersDelivery() {
        return orderRepository.findAllMembersDelivery();
    }

    public List<OrderDto> findOrderDto() {
        return orderRepository.findOrderDto();
    }
}
