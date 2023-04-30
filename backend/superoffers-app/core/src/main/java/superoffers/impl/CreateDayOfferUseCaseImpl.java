package superoffers.impl;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.CreateDayOfferUseCase;

public class CreateDayOfferUseCaseImpl implements CreateDayOfferUseCase {

    private DynamoDbEnhancedClient enhancedClient;

    public CreateDayOfferUseCaseImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @Override
    public void store(DayOffer dayOffer) {
        DynamoDbTable<DayOfferImpl> dayOfferTable = enhancedClient.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));
        dayOfferTable.putItem((DayOfferImpl)dayOffer);
    }
}
