package superoffers.impl;

import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.ViewDayOfferUseCase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ViewDayOfferUseCaseImpl implements ViewDayOfferUseCase {
    private DynamoDbEnhancedClient enhancedClient;

    public ViewDayOfferUseCaseImpl(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @Override
    public DayOffer findById(UUID id) {
        DynamoDbTable<DayOfferImpl> dayOfferTable = enhancedClient.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));
        Key key = Key
                .builder()
                .partitionValue(String.valueOf(id))
                .build();
        return dayOfferTable.getItem(GetItemEnhancedRequest.builder().key(key).build());
    }

    @Override
    public List<DayOffer> findAllByProductName(String productName) {
        DynamoDbTable<DayOfferImpl> dayOfferTable = enhancedClient.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));

        DynamoDbIndex<DayOfferImpl> productNameIndex = dayOfferTable.index("productName-index");

        QueryConditional query = QueryConditional.keyEqualTo(Key.builder()
            .partitionValue(productName)
            .build());
        SdkIterable<Page<DayOfferImpl>> pageIterable = productNameIndex.query(query);
        return StreamSupport.stream(pageIterable.spliterator(), false)
            .flatMap(page -> page.items().stream())
            .collect(Collectors.toList());
    }

    @Override
    public List<DayOffer> findAllBySupermarketName(String supermarketName) {
        DynamoDbTable<DayOfferImpl> dayOfferTable = enhancedClient.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));

        DynamoDbIndex<DayOfferImpl> supermarketNameIndex = dayOfferTable.index("supermarketName-index");

        QueryConditional query = QueryConditional.keyEqualTo(Key.builder()
            .partitionValue(supermarketName)
            .build());
        SdkIterable<Page<DayOfferImpl>> pageIterable = supermarketNameIndex.query(query);
        return StreamSupport.stream(pageIterable.spliterator(), false)
            .flatMap(page -> page.items().stream())
            .collect(Collectors.toList());
    }

}
