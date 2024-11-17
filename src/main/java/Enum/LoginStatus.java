package Enum;

public enum LoginStatus {

    NOT_FOUND("User not found in System!"),
    WORNG_PASSWORD("You have entered the Wrong Password, please try again!"),
    WRONG_USER_NAME("You have entered the Wrong Username, please try again!"),
    WRONG_EMAIL("You have entered the Wrong Email, please try again!"),
    BLOCKED("User is blocked !")
    ;


    private final String messager;

    LoginStatus(String messager) {
        this.messager = messager;
    }

    public String getMessager(){
        return this.messager;
    }
}
