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

import java.util.UUID;

public class RetrieveDayOfferHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private ViewDayOfferUseCase viewDayOfferUseCase = null;
  
    public RetrieveDayOfferHandler() {
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
        UUID dayOfferId = UUID.fromString(apiGatewayProxyRequestEvent.getPathParameters().get("id"));
        DayOffer dayOfferRetrieved = this.viewDayOfferUseCase.findById(dayOfferId);
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .serializeNulls()
            .registerTypeAdapter(Instant.class, new InstantAdapter())
            .create();
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(dayOfferRetrieved));
    }

}
