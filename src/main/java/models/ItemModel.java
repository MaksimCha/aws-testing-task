package models;

import java.util.Objects;

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

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Number getOriginTimeStamp() {
        return originTimeStamp;
    }

    public void setOriginTimeStamp(Number originTimeStamp) {
        this.originTimeStamp = originTimeStamp;
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

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemModel)) return false;
        ItemModel itemModel = (ItemModel) o;
        return Objects.equals(getPackageId(), itemModel.getPackageId()) &&
                Objects.equals(getOriginTimeStamp(), itemModel.getOriginTimeStamp()) &&
                Objects.equals(getFilePath(), itemModel.getFilePath()) &&
                Objects.equals(getFileType(), itemModel.getFileType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPackageId(), getOriginTimeStamp(), getFilePath(), getFileType());
    }
}
