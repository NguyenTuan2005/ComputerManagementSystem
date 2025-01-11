package model;

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
    if (obj == null || !(obj instanceof model.Image)) return false;
    else {
      model.Image that = (model.Image) obj;
      return this.id == that.id && this.url.equals(that.url);
    }
  }
}
