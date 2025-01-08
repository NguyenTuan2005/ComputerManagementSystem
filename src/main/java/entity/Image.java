package  entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    int id;
    String url;

    @Override
    public boolean equals(Object obj) {
        if (obj==null || !(obj instanceof entity.Image))
            return false;
        else {
            entity.Image that = (entity.Image) obj;
            return this.id == that.id &&
                    this.url.equals(that.url);
        }
    }
}
