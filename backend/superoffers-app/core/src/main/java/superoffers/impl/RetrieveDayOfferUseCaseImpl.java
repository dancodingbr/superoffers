package superoffers.impl;

import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.RetrieveDayOfferUseCase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RetrieveDayOfferUseCaseImpl implements RetrieveDayOfferUseCase {
    private DynamoDbEnhancedClient enhancedClient;

    public RetrieveDayOfferUseCaseImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @Override
    public DayOffer findById(UUID id) {
        DynamoDbTable<DayOfferImpl> dayOfferTable = enhancedClient.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));
        Key key = Key
                .builder()
                .partitionValue(String.valueOf(id))
                .build();
        DayOffer out = dayOfferTable.getItem(GetItemEnhancedRequest.builder().key(key).build());
        return out;
    }

}
