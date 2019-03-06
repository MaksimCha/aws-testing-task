package enums;

public enum TableStatus {
    ACTIVE("ACTIVE");

    String status;

    TableStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
