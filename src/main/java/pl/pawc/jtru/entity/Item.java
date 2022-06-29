package pl.pawc.jtru.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Objects;

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

    @Transient
    private float stars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemKey, item.itemKey) && Objects.equals(type, item.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemKey, type);
    }

}