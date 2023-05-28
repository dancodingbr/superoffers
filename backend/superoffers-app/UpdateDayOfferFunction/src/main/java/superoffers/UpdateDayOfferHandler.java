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
import superoffers.core.usecases.EditDayOfferUseCase;
import superoffers.impl.EditDayOfferUseCaseImpl;
import superoffers.util.jsonserializers.InstantAdapter;

import java.net.URI;
import java.time.Instant;


public class UpdateDayOfferHandler
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private EditDayOfferUseCase editDayOfferUseCase = null;
  
  public UpdateDayOfferHandler() {
    DynamoDbClient ddb = DynamoDbClient.builder()
              .region(Region.US_EAST_1)
              .endpointOverride(URI.create("http://localhost:8000"))
              .build();
    DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
              .dynamoDbClient(ddb)
              .build();
    editDayOfferUseCase = new EditDayOfferUseCaseImpl(enhancedClient);
  }
  
  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
    Gson gson = new GsonBuilder()
      .setDateFormat("yyyy-MM-dd")
      .serializeNulls()
      .registerTypeAdapter(Instant.class, new InstantAdapter())
      .create();
    DayOffer dayOfferPayload = gson.fromJson(apiGatewayProxyRequestEvent.getBody(), DayOfferImpl.class);
    DayOffer dayOfferUpdated = this.editDayOfferUseCase.update(dayOfferPayload);
    return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(dayOfferUpdated));
  }

}
