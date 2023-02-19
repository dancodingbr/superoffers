package superoffers;

import org.junit.Test;
import superoffers.core.usecases.CreateDayOfferUseCase;

import static org.junit.Assert.assertTrue;

public class CreateDayOfferUseCaseImplTest {

    @Test
    public void it_should_creates_the_instance_when_calls_constructor() {
        // arrange
        CreateDayOfferUseCase actualInstance = null;

        // act
        actualInstance = new CreateDayOfferUseCaseImpl();

        // assert
        assertTrue(actualInstance instanceof CreateDayOfferUseCase);
    }

    @Test
    public void it_should_returns_nothing_when_stores_a_day_offer_successfully() {

    }
}
