package superoffers.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import superoffers.core.entities.DayOffer;
import superoffers.core.usecases.CreateDayOfferUseCase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateDayOfferUseCaseImplTest {

    @Mock
    private DynamoDbEnhancedClient enhancedClientMock;

    @Mock
    private DynamoDbTable<DayOfferImpl> dayOfferTableMock;

    @Test
    public void it_should_create_the_instance_when_calls_constructor() {
        // arrange
        CreateDayOfferUseCase actualInstance = null;

        // act
        actualInstance = new CreateDayOfferUseCaseImpl(enhancedClientMock);

        // assert
        assertTrue(actualInstance instanceof CreateDayOfferUseCase);
    }

    @Test
    public void it_should_return_nothing_when_stores_a_day_offer_successfully() {
        // arrange
        CreateDayOfferUseCase createDayOfferUseCase = new CreateDayOfferUseCaseImpl(enhancedClientMock);
        LocalDate localDate = LocalDate.parse("2022-12-31");
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Instant offerExpirationDate = localDateTime.toInstant(ZoneOffset.UTC);
        DayOffer dayOffer = new DayOfferImpl(UUID.randomUUID(), "Lime", 0.49, 1000.0, "PKG", offerExpirationDate, "Bob's Supermarket", null);

        given(enhancedClientMock.table("DayOffers", TableSchema.fromBean(DayOfferImpl.class))).willReturn(dayOfferTableMock);
        doNothing().when(dayOfferTableMock).putItem((DayOfferImpl)dayOffer);

        // act
        createDayOfferUseCase.store((DayOfferImpl)dayOffer);

        // verify
        verify(enhancedClientMock).table("DayOffers", TableSchema.fromBean(DayOfferImpl.class));
        verify(dayOfferTableMock).putItem((DayOfferImpl) dayOffer);
    }
}
