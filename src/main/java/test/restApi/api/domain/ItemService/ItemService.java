package test.restApi.api.domain.ItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.restApi.api.domain.item.Item;
import test.restApi.api.domain.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public Item findOneItem(Long id){
        return itemRepository.findOne(id);
    }

    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }
}
