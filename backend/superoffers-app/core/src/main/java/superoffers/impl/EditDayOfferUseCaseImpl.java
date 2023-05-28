package superoffers.impl;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.EditDayOfferUseCase;

public class EditDayOfferUseCaseImpl implements EditDayOfferUseCase {

    private DynamoDbEnhancedClient enhancedClient;

    public EditDayOfferUseCaseImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @Override
    public DayOffer update(DayOffer dayOffer) {
        DynamoDbTable<DayOfferImpl> dayOfferTable = enhancedClient.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));
        return dayOfferTable.updateItem((DayOfferImpl)dayOffer);
    }
    
}
