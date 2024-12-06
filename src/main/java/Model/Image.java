package Model;

import lombok.*;

import java.util.Objects;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    private int id;

    private int productId;

    private String url;

    private String  alt;

    public Image(int productId, String url, String alt) {
        this.productId = productId;
        this.url = url;
        this.alt = alt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id && productId == image.productId && Objects.equals(url, image.url) && Objects.equals(alt, image.alt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, url, alt);
    }
}
