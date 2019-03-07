package models;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder;
import com.amazonaws.services.dynamodbv2.xspec.UpdateItemExpressionSpec;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import static com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder.S;
import static enums.Attributes.*;
import static enums.TableStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamoModel {

    private String clientRegion = System.getProperty("client.region");
    private String tableName = System.getProperty("table.name");
    private AmazonDynamoDB dynamoDBClient;
    private DynamoDB dynamoDB;
    private Table viewTable;

    public void prepare() {
        dynamoDBClient = AmazonDynamoDBClientBuilder.standard().withRegion(clientRegion).build();
    }

    public void checkParameters() {
        assertEquals(dynamoDBClient.listTables().getTableNames().get(0), tableName);
        checkTableStructure();
        checkTableStatus();
        checkCRUD();
    }

    private void checkTableStructure() {
        Iterator<AttributeDefinition> iter = dynamoDBClient.describeTable(tableName).getTable().getAttributeDefinitions().iterator();
        for (String atr : getAttributes()) {
            assertEquals(iter.next().getAttributeName(), atr);
        }
    }

    private void checkTableStatus(){
        assertEquals(dynamoDBClient.describeTable(tableName).getTable().getTableStatus(), ACTIVE.getStatus());
    }

    private void checkCRUD() {
        dynamoDB = new DynamoDB(dynamoDBClient);
        viewTable = dynamoDB.getTable(tableName);
        ItemModel itemModel = new ItemModel("2", 3, "123", "123");
        SETDynamoDBItem(itemModel);
        itemModel.setFilePath("124");
        UPDATEDynamoDBItem(itemModel);
        Map<String, Object> map = GETDynamoDBItem(itemModel).asMap();
        assertEquals(itemModel.getPackageId(), map.get(PACKAGE_ID.getAttribute()));
        assertEquals(BigDecimal.valueOf(itemModel.getOriginTimeStamp().longValue()), map.get(ORIGIN_TIME_STAMP.getAttribute()));
        assertEquals(itemModel.getFilePath(), map.get(FILE_PATH.getAttribute()));
        assertEquals(itemModel.getFileType(), map.get(FILE_TYPE.getAttribute()));
        DELETEDynamoDBItem(itemModel);
    }

    private Item GETDynamoDBItem(ItemModel item) {
        return viewTable.getItem(PACKAGE_ID.getAttribute(), item.getPackageId(), ORIGIN_TIME_STAMP.getAttribute(), item.getOriginTimeStamp());
    }

    private void SETDynamoDBItem(ItemModel item) {
        Item item1 = new Item()
                .withPrimaryKey(PACKAGE_ID.getAttribute(), item.getPackageId())
                .withString(FILE_PATH.getAttribute(), item.getFilePath())
                .withString(FILE_TYPE.getAttribute(), item.getFileType())
                .withNumber(ORIGIN_TIME_STAMP.getAttribute(), item.getOriginTimeStamp());
        viewTable.putItem(item1);
    }

    private void UPDATEDynamoDBItem(ItemModel item) {
        UpdateItemExpressionSpec xSpec = new ExpressionSpecBuilder()
                .addUpdate(S(FILE_PATH.getAttribute()).set(item.getFilePath()))
                .addUpdate(S(FILE_TYPE.getAttribute()).set(item.getFileType()))
                .buildForUpdate();
        viewTable.updateItem(PACKAGE_ID.getAttribute(), item.getPackageId(), ORIGIN_TIME_STAMP.getAttribute(), item.getOriginTimeStamp(), xSpec);
    }

    private void DELETEDynamoDBItem(ItemModel item) {
        viewTable.deleteItem(PACKAGE_ID.getAttribute(), item.getPackageId(), ORIGIN_TIME_STAMP.getAttribute(), item.getOriginTimeStamp());
    }
}
