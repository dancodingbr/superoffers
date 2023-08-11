package superoffers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.tests.annotations.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import superoffers.core.entities.DayOffer;
import superoffers.impl.DayOfferImpl;
import superoffers.util.jsonserializers.InstantAdapter;
import superoffers.util.context.TestContext;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.net.URI;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class RetrieveAllDayOffersHandlerTest {

  private DynamoDbClient ddb;
  private DynamoDbEnhancedClient enhancedClient;
  private DynamoDbTable<DayOfferImpl> dayOfferTable;

  @BeforeAll
  public void setUp() {
    this.ddb = DynamoDbClient.builder()
                  .region(Region.US_EAST_1)
                  .endpointOverride(URI.create("http://localhost:8000"))
                  .build();
    this.enhancedClient = DynamoDbEnhancedClient.builder()
                  .dynamoDbClient(ddb)
                  .build();
    this.dayOfferTable = enhancedClient.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));
    DayOffer dayOfferOne = new DayOfferImpl(UUID.fromString("2baf9517-ad43-4893-848d-7a7d43103839"), "Lime", 0.49, 1000.0, "PKG", Instant.now(), "Bob's Supermarket", 0L);
    dayOfferTable.putItem((DayOfferImpl)dayOfferOne);
    DayOffer dayOfferTwo = new DayOfferImpl(UUID.fromString("6ee0a649-906c-424f-b38a-d99db5059aef"), "Garlic", 0.49, 5000.0, "PKG", Instant.now(), "Bob's Supermarket", 0L);
    dayOfferTable.putItem((DayOfferImpl)dayOfferTwo);
  }

  @AfterAll
  public void tearDown() {
    this.ddb.close();
  }

  @ParameterizedTest
  @HandlerParams(events = @Events(events = {@Event("apigtw/events/valid_day_offer_json_event_01.json")}, type = APIGatewayProxyRequestEvent.class), responses = @Responses(responses = {@Response("apigtw/responses/valid_day_offer_json_response_01.json")}, type = APIGatewayProxyResponseEvent.class))
  public void it_should_return_200_response_when_call_get_all_day_offers_by_product_name(APIGatewayProxyRequestEvent event, APIGatewayProxyResponseEvent expectedResponse) {
    // given
    RetrieveAllDayOffersHandler handler = new RetrieveAllDayOffersHandler();
    TestContext context = new TestContext();

    Map<String, String> queryStringParams = new HashMap<String, String>();
    queryStringParams.put("productName", "Lime");
    event.setHttpMethod("GET");
    event.setQueryStringParameters(queryStringParams);

    // when
    APIGatewayProxyResponseEvent actualResponse = handler.handleRequest(event, context);

    // then
    assertEquals(expectedResponse.getStatusCode().intValue(), actualResponse.getStatusCode().intValue());
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .serializeNulls()
            .registerTypeAdapter(Instant.class, new InstantAdapter())
            .create();
    List<DayOffer> actualDayOfferListRetrieved = gson.fromJson(actualResponse.getBody(), new TypeToken<ArrayList<DayOfferImpl>>(){}.getType());
    assertTrue(actualDayOfferListRetrieved.size() == 1);
    assertTrue(actualDayOfferListRetrieved.get(0).getProductName().equals("Lime"));
  }

  @ParameterizedTest
  @HandlerParams(events = @Events(events = {@Event("apigtw/events/valid_day_offer_json_event_02.json")}, type = APIGatewayProxyRequestEvent.class), responses = @Responses(responses = {@Response("apigtw/responses/valid_day_offer_json_response_02.json")}, type = APIGatewayProxyResponseEvent.class))
  public void it_should_return_200_response_when_call_get_all_day_offers_by_supermarket_name(APIGatewayProxyRequestEvent event, APIGatewayProxyResponseEvent expectedResponse) {
    // given
    RetrieveAllDayOffersHandler handler = new RetrieveAllDayOffersHandler();
    TestContext context = new TestContext();

    Map<String, String> queryStringParams = new HashMap<String, String>();
    queryStringParams.put("supermarketName", "Bob's Supermarket");
    event.setHttpMethod("GET");
    event.setQueryStringParameters(queryStringParams);

    // when
    APIGatewayProxyResponseEvent actualResponse = handler.handleRequest(event, context);

    // then
    assertEquals(expectedResponse.getStatusCode().intValue(), actualResponse.getStatusCode().intValue());
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .serializeNulls()
            .registerTypeAdapter(Instant.class, new InstantAdapter())
            .create();
    List<DayOffer> actualDayOfferListRetrieved = gson.fromJson(actualResponse.getBody(), new TypeToken<ArrayList<DayOfferImpl>>(){}.getType());
    assertTrue(actualDayOfferListRetrieved.size() == 2);
    assertTrue(actualDayOfferListRetrieved.get(0).getSupermarketName().equals("Bob's Supermarket"));
    assertTrue(actualDayOfferListRetrieved.get(1).getSupermarketName().equals("Bob's Supermarket"));
  }

}
