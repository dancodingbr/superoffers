package superoffers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.tests.annotations.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import superoffers.core.entities.DayOffer;
import superoffers.impl.DayOfferImpl;
import superoffers.util.jsonserializers.InstantAdapter;
import superoffers.util.context.TestContext;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RetrieveAllDayOffersHandlerTest {

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
  @HandlerParams(events = @Events(events = {@Event("apigtw/events/valid_day_offer_json_event_01.json")}, type = APIGatewayProxyRequestEvent.class), responses = @Responses(responses = {@Response("apigtw/responses/valid_day_offer_json_response_01.json")}, type = APIGatewayProxyResponseEvent.class))
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
  }

}
