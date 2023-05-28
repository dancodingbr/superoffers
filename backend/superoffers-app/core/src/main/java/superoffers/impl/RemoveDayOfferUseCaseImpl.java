package superoffers.impl;

import java.util.UUID;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.RemoveDayOfferUseCase;

public class RemoveDayOfferUseCaseImpl implements RemoveDayOfferUseCase {

    private DynamoDbEnhancedClient enhancedClient;

    public RemoveDayOfferUseCaseImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @Override
    public DayOffer delete(UUID id) {
        DynamoDbTable<DayOfferImpl> dayOfferTable = enhancedClient.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));
        Key key = Key
                .builder()
                .partitionValue(String.valueOf(id))
                .build();
        return dayOfferTable.deleteItem(key);
    }
    
}
