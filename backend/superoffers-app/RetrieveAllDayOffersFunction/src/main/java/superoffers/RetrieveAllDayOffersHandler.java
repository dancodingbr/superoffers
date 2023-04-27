package superoffers;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RetrieveAllDayOffersHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        response.setHeaders(headers);

        String productName = null;
        if (input.getQueryStringParameters() != null && input.getQueryStringParameters().containsKey("productName")) {
            productName = input.getQueryStringParameters().get("productName");
        }

        String supermarketName = null;
        if (input.getQueryStringParameters() != null
                && input.getQueryStringParameters().containsKey("supermarketName")) {
            supermarketName = input.getQueryStringParameters().get("supermarketName");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String output = String.format("{ \"message\": \"retrieve all day offers (productName: %s, supermarketName: %s) \" }",
                    productName, supermarketName);
            Dummy dummy = objectMapper.readValue(output, Dummy.class);

            String responseBody = objectMapper.writeValueAsString(dummy);
            response.setBody(responseBody);
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setBody("{\"message\": \"Internal server error\"}");
        }

        return response;
    }

}
