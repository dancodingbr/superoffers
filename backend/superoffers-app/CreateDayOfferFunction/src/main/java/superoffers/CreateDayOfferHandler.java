package superoffers;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateDayOfferHandler
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    response.setHeaders(headers);

    try {
      // Parse the input body
      ObjectMapper objectMapper = new ObjectMapper();
      Dummy dummy = objectMapper.readValue(input.getBody(), Dummy.class);

      // Build the response body
      String responseBody = objectMapper.writeValueAsString(dummy);
      response.setBody(responseBody);
      response.setStatusCode(201);

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setBody("{\"message\": \"Internal server error\"}");
    }

    return response;
  }

}
