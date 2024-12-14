package Enum;

public enum OrderType {
    ACTIVE("Received the application"),
    UN_ACTIVE("Cancel");
    private final String status ;

    OrderType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
