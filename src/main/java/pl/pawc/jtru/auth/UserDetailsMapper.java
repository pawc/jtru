package pl.pawc.jtru.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.pawc.jtru.entity.User;

@Component
public class UserDetailsMapper {

    UserDetails toUserDetails(User user){

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();

    }

}