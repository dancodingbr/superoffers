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
import superoffers.impl.DayOfferImpl;
import superoffers.core.usecases.CreateDayOfferUseCase;
import superoffers.impl.CreateDayOfferUseCaseImpl;
import superoffers.core.usecases.RetrieveDayOfferUseCase;
import superoffers.impl.RetrieveDayOfferUseCaseImpl;
import superoffers.util.jsonserializers.InstantAdapter;

import java.net.URI;
import java.time.Instant;

public class CreateDayOfferHandler
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

      private CreateDayOfferUseCase createDayOfferUseCase = null;
      private RetrieveDayOfferUseCase retrieveDayOfferUseCase = null;
  
      public CreateDayOfferHandler() {
          DynamoDbClient ddb = DynamoDbClient.builder()
                  .region(Region.US_EAST_1)
                  .endpointOverride(URI.create("http://localhost:8000"))
                  .build();
          DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                  .dynamoDbClient(ddb)
                  .build();
          createDayOfferUseCase = new CreateDayOfferUseCaseImpl(enhancedClient);
          retrieveDayOfferUseCase = new RetrieveDayOfferUseCaseImpl(enhancedClient);
      }
  
  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
    Gson gson = new GsonBuilder()
      .setDateFormat("yyyy-MM-dd")
      .serializeNulls()
      .registerTypeAdapter(Instant.class, new InstantAdapter())
      .create();
    DayOffer dayOfferPayload = gson.fromJson(apiGatewayProxyRequestEvent.getBody(), DayOfferImpl.class);
    this.createDayOfferUseCase.store(dayOfferPayload);
    DayOffer dayOfferCreated = this.retrieveDayOfferUseCase.findById(dayOfferPayload.getId());
    return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(dayOfferCreated));
  }

}
