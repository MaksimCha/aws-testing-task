package enums;

import java.util.ArrayList;
import java.util.List;

public enum Attributes {
    FILE_PATH("filePath"),
    FILE_TYPE("fileType"),
    ORIGIN_TIME_STAMP("originTimeStamp"),
    PACKAGE_ID("packageId");

    private String attribute;

    Attributes(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public static List<String> getAttributes() {
        List<String> attributeItems = new ArrayList<>();
        for (Attributes item : Attributes.values()) {
            attributeItems.add(item.getAttribute());
        }
        return attributeItems;
    }
}
