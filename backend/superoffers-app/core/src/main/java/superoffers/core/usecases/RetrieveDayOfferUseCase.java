package superoffers.core.usecases;

import superoffers.core.entities.DayOffer;

import java.util.UUID;

public interface RetrieveDayOfferUseCase {
    DayOffer findById(UUID id);
}
