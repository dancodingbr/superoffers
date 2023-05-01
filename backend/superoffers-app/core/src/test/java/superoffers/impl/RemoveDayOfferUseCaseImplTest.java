package superoffers.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.RemoveDayOfferUseCase;

@RunWith(MockitoJUnitRunner.class)
public class RemoveDayOfferUseCaseImplTest {

    @Mock
    private DynamoDbEnhancedClient enhancedClientMock;

    @Mock
    private DynamoDbTable<DayOfferImpl> dayOfferTableMock;

    @Mock
    private DayOfferImpl expectedDayOfferMock;

    @Test
    public void it_should_create_the_instance_when_calls_constructor() {
        // arrange
        RemoveDayOfferUseCase actualInstance = null;

        // act
        actualInstance = new RemoveDayOfferUseCaseImpl(enhancedClientMock);

        // assert
        assertTrue(actualInstance instanceof RemoveDayOfferUseCase);
    }

    @Test
    public void it_should_return_the_day_offer_deleted_given_an_id() {
        // arrange
        RemoveDayOfferUseCase removeDayOfferUseCase = new RemoveDayOfferUseCaseImpl(enhancedClientMock);
        UUID id = UUID.fromString("2baf9517-ad43-4893-848d-7a7d43103839");
        Key key = Key.builder().partitionValue(String.valueOf(id)).build();

        given(enhancedClientMock.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class))).willReturn(dayOfferTableMock);
        when(dayOfferTableMock.deleteItem(key)).thenReturn(expectedDayOfferMock);

        // act
        DayOffer actualDayOffer = removeDayOfferUseCase.delete(id);

        // assert
        assertEquals(expectedDayOfferMock, actualDayOffer);
    }
    
}
