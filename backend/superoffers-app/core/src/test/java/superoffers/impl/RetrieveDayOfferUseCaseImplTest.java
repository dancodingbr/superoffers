package superoffers.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.RetrieveDayOfferUseCase;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveDayOfferUseCaseImplTest {

    @Mock
    private DynamoDbEnhancedClient enhancedClientMock;

    @Mock
    private DynamoDbTable<DayOfferImpl> dayOfferTableMock;

    @Mock
    private DayOfferImpl expectedDayOfferMock;

    @Test
    public void it_should_creates_the_instance_when_calls_constructor() {
        // arrange
        RetrieveDayOfferUseCase actualInstance = null;

        // act
        actualInstance = new RetrieveDayOfferUseCaseImpl(enhancedClientMock);

        // assert
        assertTrue(actualInstance instanceof RetrieveDayOfferUseCase);
    }

    @Test
    public void it_should_returns_a_day_offer_given_an_id() {
        // arrange
        RetrieveDayOfferUseCase retrieveDayOfferUseCase = new RetrieveDayOfferUseCaseImpl(enhancedClientMock);
        UUID id = UUID.fromString("2baf9517-ad43-4893-848d-7a7d43103839");
        Key key = Key.builder().partitionValue(String.valueOf(id)).build();

        given(enhancedClientMock.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class))).willReturn(dayOfferTableMock);
        when(dayOfferTableMock.getItem(GetItemEnhancedRequest.builder().key(key).build())).thenReturn(expectedDayOfferMock);

        // act
        DayOffer actualDayOffer = retrieveDayOfferUseCase.findById(id);

        // assert
        assertEquals(expectedDayOfferMock, actualDayOffer);
    }

}
