package pl.pawc.jtru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.pawc.jtru.entity.User;
import pl.pawc.jtru.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "${frontOrigin}")
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public void register(HttpServletRequest request,
           HttpServletResponse response,
           @RequestBody User user){

        if(user.getEmail().length() < 7  || user.getPassword().length() < 4 ){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User oneByEmail = userRepository.findOneByEmail(user.getEmail());

        if(oneByEmail != null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User userNew = new User();
        userNew.setEmail(user.getEmail());
        userNew.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userNew);

    }

}