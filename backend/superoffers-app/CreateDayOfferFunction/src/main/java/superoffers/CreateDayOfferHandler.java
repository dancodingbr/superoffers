package superoffers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.CreateDayOfferUseCase;

/**
 * Handler for requests to Lambda function.
 */
public class CreateDayOfferHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private CreateDayOfferUseCase createDayOfferUseCase = new CreateDayOfferUseCaseImpl();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
        DayOffer dayOfferPayload = gson.fromJson(apiGatewayProxyRequestEvent.getBody(), DayOfferImpl.class);
        this.createDayOfferUseCase.store(dayOfferPayload);
        DayOffer dayOfferCreated = null;
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(dayOfferCreated));
    }
}
