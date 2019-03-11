package enums;

public enum TableStatus {
    ACTIVE("ACTIVE");

    private String status;

    TableStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
