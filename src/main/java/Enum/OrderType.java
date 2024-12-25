package Enum;

public enum OrderType {
  ACTIVE("Received the application"),
  UN_ACTIVE("Cancel"),
  DISPATCHED("Order processed for shipping"),
  NONE("None");
  private final String status;
  public static final String ACTIVE_MESSAGE = "Received the application";
  public static final String UN_ACTIVE_MESSAGE = "Cancel";
  public static final String DISPATCHED_MESSAGE = "Order processed for shipping";

  OrderType(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public boolean isActive() {
    return this == ACTIVE;
  }

  public boolean isUnActive() {
    return this == UN_ACTIVE;
  }

  public boolean isDispatched() {
    return this == DISPATCHED;
  }

  public static void main(String[] args) {}
}
