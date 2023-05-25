package superoffers.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.ViewDayOfferUseCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewDayOfferUseCaseImplTest {

    @Mock
    private DynamoDbEnhancedClient enhancedClientMock;

    @Mock
    private DynamoDbTable<DayOfferImpl> dayOfferTableMock;

    @Mock
    private QueryConditional queryMock;

    @Mock
    private DayOfferImpl expectedDayOfferMock;

    @Mock
    private SdkIterable<Page<DayOfferImpl>> expectedDayOfferListMock;

    @Mock
    private DynamoDbIndex<DayOfferImpl> productNameIndexMock;

    @Mock
    private DynamoDbIndex<DayOfferImpl> supermarketNameIndexMock;

    @Test
    public void it_should_create_the_instance_when_calls_constructor() {
        // arrange
        ViewDayOfferUseCase actualInstance = null;

        // act
        actualInstance = new ViewDayOfferUseCaseImpl(enhancedClientMock);

        // assert
        assertTrue(actualInstance instanceof ViewDayOfferUseCase);
    }

    @Test
    public void it_should_return_a_day_offer_given_an_id() {
        // arrange
        ViewDayOfferUseCase viewDayOfferUseCase = new ViewDayOfferUseCaseImpl(enhancedClientMock);
        UUID id = UUID.fromString("2baf9517-ad43-4893-848d-7a7d43103839");
        Key key = Key.builder().partitionValue(String.valueOf(id)).build();

        given(enhancedClientMock.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class))).willReturn(dayOfferTableMock);
        when(dayOfferTableMock.getItem(GetItemEnhancedRequest.builder().key(key).build())).thenReturn(expectedDayOfferMock);

        // act
        DayOffer actualDayOffer = viewDayOfferUseCase.findById(id);

        // assert
        assertEquals(expectedDayOfferMock, actualDayOffer);
    }

    @Test
    public void it_should_return_all_day_offers_given_a_product_name() {
        // arrange
        String productName = "Lime";
        DayOffer dayOffer1 = new DayOfferImpl(UUID.randomUUID(), "Lime", 0.0, 0.0, null, null, "Bob's Supermarket", null);
        DayOffer dayOffer2 = new DayOfferImpl(UUID.randomUUID(), "Garlic", 0.0, 0.0, null, null, "Bob's Supermarket", null);

        ViewDayOfferUseCase viewDayOfferUseCase = new ViewDayOfferUseCaseImpl(enhancedClientMock);
        when(enhancedClientMock.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class))).thenReturn(dayOfferTableMock);
        when(dayOfferTableMock.index("productName-index")).thenReturn(productNameIndexMock);

        when(productNameIndexMock.query(any(QueryConditional.class))).thenReturn(expectedDayOfferListMock);
        Page<DayOfferImpl> page1 = mock(Page.class);
        Page<DayOfferImpl> page2 = mock(Page.class);
        when(page1.items()).thenReturn(Collections.singletonList((DayOfferImpl)dayOffer1));
        when(page2.items()).thenReturn(Collections.singletonList((DayOfferImpl)dayOffer2));
        when(expectedDayOfferListMock.spliterator()).thenReturn(Arrays.asList(page1, page2).spliterator());

        // act
        List<DayOffer> actualDayOfferList = viewDayOfferUseCase.findAllByProductName(productName);

        // assert
        assertEquals(2, actualDayOfferList.size());
        assertEquals(dayOffer1.getProductName(), actualDayOfferList.get(0).getProductName());
    }

    @Test
    public void it_should_return_all_day_offers_given_a_supermarket_name() {
        // arrange
        String supermarketName = "Bob's Supermarket";
        DayOffer dayOffer1 = new DayOfferImpl(UUID.randomUUID(), "Lime", 0.0, 0.0, null, null, "Bob's Supermarket", null);
        DayOffer dayOffer2 = new DayOfferImpl(UUID.randomUUID(), "Garlic", 0.0, 0.0, null, null, "Bob's Supermarket", null);

        ViewDayOfferUseCase viewDayOfferUseCase = new ViewDayOfferUseCaseImpl(enhancedClientMock);
        when(enhancedClientMock.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class))).thenReturn(dayOfferTableMock);
        when(dayOfferTableMock.index("supermarketName-index")).thenReturn(supermarketNameIndexMock);

        when(supermarketNameIndexMock.query(any(QueryConditional.class))).thenReturn(expectedDayOfferListMock);
        Page<DayOfferImpl> page1 = mock(Page.class);
        Page<DayOfferImpl> page2 = mock(Page.class);
        when(page1.items()).thenReturn(Collections.singletonList((DayOfferImpl)dayOffer1));
        when(page2.items()).thenReturn(Collections.singletonList((DayOfferImpl)dayOffer2));
        when(expectedDayOfferListMock.spliterator()).thenReturn(Arrays.asList(page1, page2).spliterator());

        // act
        List<DayOffer> actualDayOfferList = viewDayOfferUseCase.findAllBySupermarketName(supermarketName);

        // assert
        assertEquals(2, actualDayOfferList.size());
        assertEquals(dayOffer1.getSupermarketName(), actualDayOfferList.get(0).getSupermarketName());
        assertEquals(dayOffer2.getSupermarketName(), actualDayOfferList.get(1).getSupermarketName());
    }

}
