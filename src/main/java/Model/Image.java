package Model;

import lombok.*;

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
}
