package superoffers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DeleteDayOfferHandlerTest {

  @Test
  public void deleteDayOffer() {
    DeleteDayOfferHandler deleteDayOfferHandler = new DeleteDayOfferHandler();

    APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
    request.setHttpMethod("DELETE");

    APIGatewayProxyResponseEvent response = deleteDayOfferHandler.handleRequest(request, null);
    assertEquals(200, response.getStatusCode().intValue());
    assertEquals("application/json", response.getHeaders().get("Content-Type"));

    String content = response.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
    assertTrue(content.contains("\"delete day offer\""));
  }

}
