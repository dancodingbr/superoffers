package superoffers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.ViewDayOfferUseCase;
import superoffers.impl.ViewDayOfferUseCaseImpl;
import superoffers.util.jsonserializers.InstantAdapter;

import java.net.URI;
import java.time.Instant;

import java.util.List;
import java.util.UUID;

public class RetrieveAllDayOffersHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private ViewDayOfferUseCase viewDayOfferUseCase = null;
  
    public RetrieveAllDayOffersHandler() {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create("http://localhost:8000"))
                .build();
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
        viewDayOfferUseCase = new ViewDayOfferUseCaseImpl(enhancedClient);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        List<DayOffer> dayOfferListRetrieved = null;

        String productName = null, supermarketName = null;
        if (apiGatewayProxyRequestEvent.getQueryStringParameters() != null && apiGatewayProxyRequestEvent.getQueryStringParameters().containsKey("productName")) {
            productName = apiGatewayProxyRequestEvent.getQueryStringParameters().get("productName");
            dayOfferListRetrieved = this.viewDayOfferUseCase.findAllByProductName(productName);
        }
        else if (apiGatewayProxyRequestEvent.getQueryStringParameters() != null && apiGatewayProxyRequestEvent.getQueryStringParameters().containsKey("supermarketName")) {
            supermarketName = apiGatewayProxyRequestEvent.getQueryStringParameters().get("supermarketName");
            dayOfferListRetrieved = this.viewDayOfferUseCase.findAllBySupermarketName(supermarketName);
        }

        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .serializeNulls()
            .registerTypeAdapter(Instant.class, new InstantAdapter())
            .create();
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(dayOfferListRetrieved));
    }

}
