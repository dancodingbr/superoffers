package superoffers.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.EditDayOfferUseCase;

@RunWith(MockitoJUnitRunner.class)
public class EditDayOfferUseCaseImplTest {

    @Mock
    private DynamoDbEnhancedClient enhancedClientMock;

    @Mock
    private DynamoDbTable<DayOfferImpl> dayOfferTableMock;

    @Mock
    private DayOfferImpl expectedDayOfferMock;

    @Test
    public void it_should_create_the_instance_when_calls_constructor() {
        // arrange
        EditDayOfferUseCase actualInstance = null;

        // act
        actualInstance = new EditDayOfferUseCaseImpl(enhancedClientMock);

        // assert
        assertTrue(actualInstance instanceof EditDayOfferUseCase);
    }

    @Test
    public void it_should_return_the_day_offer_updated_when_updates_a_day_offer_successfully() {
        // arrange
        EditDayOfferUseCase editDayOfferUseCase = new EditDayOfferUseCaseImpl(enhancedClientMock);
        LocalDate localDate = LocalDate.parse("2022-12-31");
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Instant offerExpirationDate = localDateTime.toInstant(ZoneOffset.UTC);
        DayOffer dayOffer = new DayOfferImpl(UUID.randomUUID(), "Lime", 0.49, 1000.0, "PKG", offerExpirationDate, "Bob's Supermarket", null);

        given(enhancedClientMock.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class))).willReturn(dayOfferTableMock);
        when(dayOfferTableMock.updateItem((DayOfferImpl)dayOffer)).thenReturn(expectedDayOfferMock);

        // act
        DayOffer actualDayOffer = editDayOfferUseCase.update((DayOfferImpl)dayOffer);

        // assert
        assertEquals(expectedDayOfferMock, actualDayOffer);

        // verify
        verify(enhancedClientMock).table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));
        verify(dayOfferTableMock).updateItem((DayOfferImpl) dayOffer);
    }
    
}
