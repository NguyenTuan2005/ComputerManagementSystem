package enums;

public enum TableStatus {
  ADD("Add manager"),
  MODIFY("Modify manager"),
  NONE("No operation ");

  private final String message;

  TableStatus(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
