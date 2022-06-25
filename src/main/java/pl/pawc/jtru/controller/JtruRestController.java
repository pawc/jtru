package pl.pawc.jtru.controller;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pawc.jtru.model.Item;
import pl.pawc.jtru.service.SpotifyService;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class JtruRestController {

    @Autowired
    SpotifyService spotifyService;

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
                       HttpServletResponse response){

        System.out.println(request.getRemoteUser());

    }


}