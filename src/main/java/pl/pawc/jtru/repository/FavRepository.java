package pl.pawc.jtru.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pawc.jtru.entity.Fav;
import pl.pawc.jtru.entity.User;

public interface FavRepository extends CrudRepository <Fav, String> {

    Fav findOneByUserAndItemKey(User user, String itemKey);

}