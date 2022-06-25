package pl.pawc.jtru.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Item {

    private String id;
    private List<String> artists;
    private String title;
    private int year;
    private String type;
    private String img_src;

}