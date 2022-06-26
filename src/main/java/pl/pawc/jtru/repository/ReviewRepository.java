package pl.pawc.jtru.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pawc.jtru.entity.Review;
import pl.pawc.jtru.entity.Item;
import pl.pawc.jtru.entity.User;

public interface ReviewRepository extends CrudRepository <Review, String> {

    Review findOneByUserAndItem(User user, Item item);

}