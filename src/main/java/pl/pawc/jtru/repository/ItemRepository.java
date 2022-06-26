package pl.pawc.jtru.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pawc.jtru.entity.Item;

public interface ItemRepository extends CrudRepository <Item, String> {

}