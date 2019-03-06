package serviceRepresentations;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;

import java.util.Iterator;

import static enums.Attributes.getAttributes;
import static enums.TableStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamoRepresentation {

    private String clientRegion = System.getProperty("client.region");
    private String tableName = System.getProperty("table.name");
    private AmazonDynamoDB dynamoDB;

    public void prepare() {
        dynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(clientRegion).build();
    }

    public void checkParameters() {
        assertEquals(dynamoDB.listTables().getTableNames().get(0), tableName);
        checkTableStructure();
        checkTableStatus();
    }

    private void checkTableStructure() {
        Iterator<AttributeDefinition> iter = dynamoDB.describeTable(tableName).getTable().getAttributeDefinitions().iterator();
        for (String atr : getAttributes()) {
            assertEquals(iter.next().getAttributeName(), atr);
        }
    }

    private void checkTableStatus(){
        assertEquals(dynamoDB.describeTable(tableName).getTable().getTableStatus(), ACTIVE.getStatus());
    }
}
