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

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateDayOfferHandlerTest {

  @ParameterizedTest
  @HandlerParams(events = @Events(events = {@Event("apigtw/events/valid_day_offer_json_event_01.json"), @Event("apigtw/events/valid_day_offer_json_event_02.json"),}, type = APIGatewayProxyRequestEvent.class), responses = @Responses(responses = {@Response("apigtw/responses/valid_day_offer_json_response_01.json"), @Response("apigtw/responses/valid_day_offer_json_response_02.json")}, type = APIGatewayProxyResponseEvent.class))
  public void it_should_returns_http_200_response_when_post_a_valid_day_offer_json_object(APIGatewayProxyRequestEvent event, APIGatewayProxyResponseEvent expectedResponse) {
      // given
      CreateDayOfferHandler handler = new CreateDayOfferHandler();
      TestContext context = new TestContext();

      // when
      APIGatewayProxyResponseEvent actualResponse = handler.handleRequest(event, context);

      // then
      assertEquals(expectedResponse.getStatusCode().intValue(), actualResponse.getStatusCode().intValue());
      Gson gson = new GsonBuilder()
              .setDateFormat("yyyy-MM-dd")
              .serializeNulls()
              .registerTypeAdapter(Instant.class, new InstantAdapter())
              .create();
      DayOffer actualDayOfferCreated = gson.fromJson(actualResponse.getBody(), DayOfferImpl.class);
      assertTrue(actualDayOfferCreated.getId() != null);
  }

}
