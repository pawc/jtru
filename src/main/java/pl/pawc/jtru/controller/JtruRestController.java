package pl.pawc.jtru.controller;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pawc.jtru.entity.Review;
import pl.pawc.jtru.entity.Item;
import pl.pawc.jtru.entity.User;
import pl.pawc.jtru.repository.ReviewRepository;
import pl.pawc.jtru.repository.ItemRepository;
import pl.pawc.jtru.repository.UserRepository;
import pl.pawc.jtru.service.SpotifyService;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class JtruRestController {

    @Autowired
    SpotifyService spotifyService;

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    private final ItemRepository itemRepository;

    @GetMapping("/search")
    public List<Item> test(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam("q") String q) throws IOException, ParseException, SpotifyWebApiException {
        return spotifyService.search(q);
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response){
        response.setStatus(200);
    }

    @PostMapping("/toggle")
    public void toggle(HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody Review review){

        System.out.println(review);

        String email = request.getRemoteUser();
        User oneByEmail = userRepository.findOneByEmail(email);

        review.setUser(oneByEmail);

        Optional<Item> itemById = itemRepository.findById(review.getItem().getItemKey());

        if(!itemById.isPresent()){
            itemRepository.save(review.getItem());
        }

        Review reviewByUserAndItem = reviewRepository.findOneByUserAndItem(oneByEmail, review.getItem());

        if(reviewByUserAndItem == null){
            reviewRepository.save(review);
        }
        else{
            reviewRepository.delete(reviewByUserAndItem);
        }

    }

}