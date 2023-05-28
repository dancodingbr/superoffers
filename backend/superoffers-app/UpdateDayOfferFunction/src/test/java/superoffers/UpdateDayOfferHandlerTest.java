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

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UpdateDayOfferHandlerTest {

  @ParameterizedTest
  @HandlerParams(events = @Events(events = {@Event("apigtw/events/valid_day_offer_json_event_01.json"), @Event("apigtw/events/valid_day_offer_json_event_02.json"),}, type = APIGatewayProxyRequestEvent.class), responses = @Responses(responses = {@Response("apigtw/responses/valid_day_offer_json_response_01.json"), @Response("apigtw/responses/valid_day_offer_json_response_02.json")}, type = APIGatewayProxyResponseEvent.class))
  public void it_should_return_http_200_response_when_call_put_a_valid_day_offer_json_object(APIGatewayProxyRequestEvent event, APIGatewayProxyResponseEvent expectedResponse) {
      // given
      UpdateDayOfferHandler handler = new UpdateDayOfferHandler();
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
      DayOffer actualDayOfferUpdated = gson.fromJson(actualResponse.getBody(), DayOfferImpl.class);
      assertTrue(actualDayOfferUpdated.getId() != null);
  }

}
