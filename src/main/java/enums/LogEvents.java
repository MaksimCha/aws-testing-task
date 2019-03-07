package enums;

public enum LogEvents {
    DELETE("delete"),
    UPLOAD("upload");

    String value;

    LogEvents(String value) {
        this.value = value;
    }
}
