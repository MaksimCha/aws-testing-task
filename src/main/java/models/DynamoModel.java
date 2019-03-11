package models;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder;
import com.amazonaws.services.dynamodbv2.xspec.UpdateItemExpressionSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import static Singletones.SingletonManager.getDynamoDBClient;
import static com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder.S;
import static enums.Attributes.*;
import static enums.TableStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamoModel {

    private Table viewTable;
    private static final Logger log = LogManager.getLogger(DynamoModel.class);

    public void checkParameters() {
        assertEquals(getDynamoDBClient().listTables().getTableNames().get(0), System.getProperty("table.name"),"Table name is correct");
        checkTableStructure();
        checkTableStatus();
        checkCRUD();
        log.info("Check Dynamo client parameters done");
    }

    private void checkTableStructure() {
        Iterator<AttributeDefinition> iter = getDynamoDBClient()
                .describeTable(System.getProperty("table.name"))
                .getTable()
                .getAttributeDefinitions()
                .iterator();
        for (String atr : getAttributes()) {
            assertEquals(iter.next().getAttributeName(), atr, "Attribute is correct: ");
        }
        log.info("Check table structure done");
    }

    private void checkTableStatus(){
        assertEquals(getDynamoDBClient()
                .describeTable(System.getProperty("table.name"))
                .getTable()
                .getTableStatus(), ACTIVE.getStatus(), "Table status is correct: ");
        log.info("Check table status done");
    }

    private void checkCRUD() {
        DynamoDB dynamoDB = new DynamoDB(getDynamoDBClient());
        viewTable = dynamoDB.getTable(System.getProperty("table.name"));
        ItemModel item = new ItemModel("2", 3, "123", "123");
        SETDynamoDBItem(item);
        item.setFilePath("124");
        UPDATEDynamoDBItem(item);
        Map<String, Object> map = GETDynamoDBItem(item).asMap();
        assertEquals(item.getPackageId(), map.get(PACKAGE_ID.getAttribute()), "Package id field is correct: ");
        assertEquals(BigDecimal.valueOf(item.getOriginTimeStamp().longValue()), map.get(ORIGIN_TIME_STAMP.getAttribute()), "Origin time stamp field is correct: ");
        assertEquals(item.getFilePath(), map.get(FILE_PATH.getAttribute()), "File path field is correct: ");
        assertEquals(item.getFileType(), map.get(FILE_TYPE.getAttribute()), "Pile type field is correct: ");
        DELETEDynamoDBItem(item);
        log.info("Check CRUD availability done");
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
