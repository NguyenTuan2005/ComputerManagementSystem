package enums;

public enum UserType {
    MANAGER,CUSTOMER;

    public boolean isManager(){ return this == MANAGER;}
    public boolean isCustomer(){return this == CUSTOMER;}
}
