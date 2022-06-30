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
@CrossOrigin(origins = "${frontOrigin}")
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

        List<Item> search = spotifyService.search(q);

        String email = request.getRemoteUser();
        User oneByEmail = userRepository.findOneByEmail(email);
        List<Review> reviewsByUser = reviewRepository.findAllByUser(oneByEmail);

        search.forEach(item -> {
            reviewsByUser.forEach(review -> {
                if(item.equals(review.getItem())){
                    item.setStars(review.getStars());
                    item.setFav(review.isFav());
                }
            });
        });

        return search;
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response){
        response.setStatus(200);
    }

    @PostMapping("/review")
    public void toggle(HttpServletRequest request,
                       @RequestBody Review review){

        String email = request.getRemoteUser();
        User oneByEmail = userRepository.findOneByEmail(email);

        review.setUser(oneByEmail);

        Optional<Item> itemById = itemRepository.findById(review.getItem().getItemKey());

        if(!itemById.isPresent()){
            itemRepository.save(review.getItem());
        }

        Review reviewByUserAndItem = reviewRepository.findOneByUserAndItem(oneByEmail, review.getItem());

        if(reviewByUserAndItem != null){
            if(review.getStars() == 0 && !review.isFav()){
                reviewRepository.delete(reviewByUserAndItem);
                return;
            }
            else{
                reviewByUserAndItem.setStars(review.getStars());
                reviewByUserAndItem.setFav(review.isFav());
                reviewRepository.save(reviewByUserAndItem);
                return;
            }
        }
        reviewRepository.save(review);

    }

    @GetMapping("/myReviews")
    public List<Review> myReviews(HttpServletRequest request){
        String email = request.getRemoteUser();
        User oneByEmail = userRepository.findOneByEmail(email);
        List<Review> reviews = reviewRepository.findAllByUser(oneByEmail);
        reviews.forEach(r -> {
            r.setUser(null);
        });
        return reviews;
    }

}