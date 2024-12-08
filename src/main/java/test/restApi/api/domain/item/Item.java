package test.restApi.api.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import test.restApi.api.domain.Category;
import test.restApi.api.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "item")
    private List<Category> category = new ArrayList<>();

    //비지니스 로직
    public void addStock(int stockQuantity){
        this.stockQuantity += stockQuantity;
    }

    public void removeStock(int stockQuantity){
        int restStock = this.stockQuantity - stockQuantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock;
    }
}
