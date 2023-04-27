package superoffers;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RetrieveAllDayOffersHandlerTest {

  @Test
  public void getAllDayOffers() {
    RetrieveAllDayOffersHandler retrieveAllDayOffersHandler = new RetrieveAllDayOffersHandler();

    APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
    request.setHttpMethod("GET");

    APIGatewayProxyResponseEvent response = retrieveAllDayOffersHandler.handleRequest(request, null);
    assertEquals(200, response.getStatusCode().intValue());
    assertEquals("application/json", response.getHeaders().get("Content-Type"));

    String content = response.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
  }

  @Test
  public void getAllDayOffersByProductName() {
    RetrieveAllDayOffersHandler retrieveAllDayOffersHandler = new RetrieveAllDayOffersHandler();

    Map<String, String> queryStringParams = new HashMap<String, String>();
    queryStringParams.put("productName", "lime");

    APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
    request.setHttpMethod("GET");
    request.setQueryStringParameters(queryStringParams);

    APIGatewayProxyResponseEvent response = retrieveAllDayOffersHandler.handleRequest(request, null);
    assertEquals(200, response.getStatusCode().intValue());
    assertEquals("application/json", response.getHeaders().get("Content-Type"));

    String content = response.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
    assertTrue(content.contains(queryStringParams.get("productName")));
  }

  @Test
  public void getAllDayOffersBySupermarketName() {
    RetrieveAllDayOffersHandler retrieveAllDayOffersHandler = new RetrieveAllDayOffersHandler();

    Map<String, String> queryStringParams = new HashMap<String, String>();
    queryStringParams.put("supermarketName", "bobs");

    APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
    request.setHttpMethod("GET");
    request.setQueryStringParameters(queryStringParams);

    APIGatewayProxyResponseEvent response = retrieveAllDayOffersHandler.handleRequest(request, null);
    assertEquals(200, response.getStatusCode().intValue());
    assertEquals("application/json", response.getHeaders().get("Content-Type"));

    String content = response.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
    assertTrue(content.contains(queryStringParams.get("supermarketName")));
  }

  @Test
  public void getAllDayOffersByProductNameAndSupermarketName() {
    RetrieveAllDayOffersHandler retrieveAllDayOffersHandler = new RetrieveAllDayOffersHandler();

    Map<String, String> queryStringParams = new HashMap<String, String>();
    queryStringParams.put("productName", "lime");
    queryStringParams.put("supermarketName", "bobs");

    APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
    request.setHttpMethod("GET");
    request.setQueryStringParameters(queryStringParams);

    APIGatewayProxyResponseEvent response = retrieveAllDayOffersHandler.handleRequest(request, null);
    assertEquals(200, response.getStatusCode().intValue());
    assertEquals("application/json", response.getHeaders().get("Content-Type"));

    String content = response.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
    assertTrue(content.contains(queryStringParams.get("productName")));
    assertTrue(content.contains(queryStringParams.get("supermarketName")));
  }

}
