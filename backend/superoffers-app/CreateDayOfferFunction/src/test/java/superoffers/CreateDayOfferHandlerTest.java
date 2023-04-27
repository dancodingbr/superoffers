package superoffers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CreateDayOfferHandlerTest {

  @Test
  public void postDayOffer() {
    CreateDayOfferHandler createDayOfferHandler = new CreateDayOfferHandler();

    // create a new APIGatewayProxyRequestEvent object with a POST method and
    // request body
    APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
    request.setHttpMethod("POST");
    request.setBody("{\"message\":\"create day offer\"}");

    // call the handleRequest method with the request object
    APIGatewayProxyResponseEvent response = createDayOfferHandler.handleRequest(request, null);

    assertEquals(201, response.getStatusCode().intValue());
    assertEquals("application/json", response.getHeaders().get("Content-Type"));
    String content = response.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
    assertTrue(content.contains("\"create day offer\""));
  }

}
