package test.restApi.api.domain.repository;

import lombok.Getter;
import lombok.Setter;
import test.restApi.api.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
