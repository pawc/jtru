package pl.pawc.jtru.service;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.pawc.jtru.entity.Item;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Component
public class SpotifyService {

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    private SpotifyApi spotifyApi;


    @PostConstruct
    public void postConstruct() throws IOException, ParseException, SpotifyWebApiException {
        spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
            .setClientSecret(clientSecret).build();

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        //spotifyApi.getRefreshToken();
    }

    public List<Item> search(String input) throws IOException, ParseException, SpotifyWebApiException {

        Paging<AlbumSimplified> response;
        try{
            response = spotifyApi.searchAlbums(input)
                .build()
                .execute();
        }
        catch(UnauthorizedException e){
            String refreshToken = spotifyApi.getRefreshToken();
            spotifyApi.setAccessToken(refreshToken);
            response = spotifyApi.searchAlbums(input)
                .build()
                .execute();
        }

        List<Item> results = new ArrayList<>();

        for (AlbumSimplified i : response.getItems()) {
            Item item = new Item();
            List<String> artists = new ArrayList<>();
            for (ArtistSimplified artist : i.getArtists()) {
                artists.add(artist.getName());
            }

            item.setYear(Integer.parseInt(i.getReleaseDate().substring(0, 4)));
            item.setArtists(StringUtils.join(artists, ","));
            item.setTitle(i.getName());
            item.setType(i.getType().getType());
            item.setItemKey(i.getId());
            item.setImg_src(Arrays.stream(i.getImages()).findFirst().get().getUrl());

            results.add(item);
        }

        return results;

    }

}