package superoffers.core.usecases;

import superoffers.core.entities.DayOffer;

public interface CreateDayOfferUseCase {
    void store(DayOffer dayOffer);
}
