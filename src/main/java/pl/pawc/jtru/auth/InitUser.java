package pl.pawc.jtru.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.pawc.jtru.entity.User;
import pl.pawc.jtru.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class InitUser implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${myEmail}")
    private String myEmail;

    @Value("${myPassword}")
    private String myPassword;

    @Override
    public void run(ApplicationArguments args){
        myUser();
    }

    private void myUser() {
        User user = new User();
        if(myEmail.length() > 5 && myPassword.length() > 3 && userRepository.findOneByEmail(myEmail) == null){
            user.setEmail(myEmail);
            user.setPassword(passwordEncoder.encode(myPassword));
            userRepository.save(user);
        }
        else {
            if(userRepository.findOneByEmail(myEmail) == null){
                user.setEmail("admin@jtru.pl");
                user.setPassword(passwordEncoder.encode("abc123"));
                userRepository.save(user);
            }
        }
    }

}