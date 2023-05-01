package superoffers.core.usecases;

import java.util.UUID;

import superoffers.core.entities.DayOffer;

public interface RemoveDayOfferUseCase {
    DayOffer delete(UUID id); 
}
