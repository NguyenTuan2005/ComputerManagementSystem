package security;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class PasswordSecurity {

  private String plainPassword;

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public PasswordSecurity(String plainPassword) {
    this.plainPassword = plainPassword;
  }

  public String generatePassword() {
    return passwordEncoder.encode(plainPassword);
  }

  public boolean isVariablePassword(String encryptedPassword) {
    return passwordEncoder.matches(this.plainPassword, encryptedPassword);
  }
}
