package models;

public class ItemModel {

    private String packageId;
    private Number originTimeStamp;
    private String filePath;
    private String fileType;

    public ItemModel(String packageId, Number originTimeStamp, String filePath, String fileType) {
        this.packageId = packageId;
        this.originTimeStamp = originTimeStamp;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public String getPackageId() {
        return packageId;
    }


    public Number getOriginTimeStamp() {
        return originTimeStamp;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }
}
