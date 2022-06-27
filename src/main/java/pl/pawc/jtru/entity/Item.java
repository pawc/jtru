package pl.pawc.jtru.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
@ToString
public class Item {

    @Id
    private String itemKey;

    @Column
    private String type;

    @Column
    private String artists;

    @Column
    private String title;

    @Column
    private int year;

    @Column
    private String img_src;

    @Transient
    private boolean isFav;

}