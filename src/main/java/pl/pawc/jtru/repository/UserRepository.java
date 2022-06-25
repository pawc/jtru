package pl.pawc.jtru.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pawc.jtru.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

    User findOneByEmail(String email);

}