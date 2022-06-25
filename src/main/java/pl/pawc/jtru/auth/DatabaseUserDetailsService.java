package pl.pawc.jtru.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import pl.pawc.jtru.entity.User;
import pl.pawc.jtru.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserDetailsMapper userDetailsMapper;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findOneByEmail(email);
        if(user != null){
            return userDetailsMapper.toUserDetails(user);
        }
        else{
            throw  new UsernameNotFoundException(email);
        }

    }

}