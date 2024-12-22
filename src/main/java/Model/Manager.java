package Model;

import java.sql.Date;
import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Manager {

  private int id;

  @NonNull private String fullName;

  @NonNull private String address;

  @NonNull private Date birthDay;

  @NonNull private String phoneNumber;

  public boolean birthDayIsNull() {
    return this.birthDay == null;
  }
}
