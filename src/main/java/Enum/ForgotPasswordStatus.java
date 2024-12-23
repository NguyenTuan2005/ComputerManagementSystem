package Enum;

public enum ForgotPasswordStatus {
  MANAGER(1),
  CUSTOMER(2);

  private final int status;

  ForgotPasswordStatus(int status) {
    this.status = status;
  }
}
