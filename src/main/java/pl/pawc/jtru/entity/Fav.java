package pl.pawc.jtru.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Fav {

    @ManyToOne
    User user;

    @Id
    private String itemKey;

    @Column
    private String type;

}
