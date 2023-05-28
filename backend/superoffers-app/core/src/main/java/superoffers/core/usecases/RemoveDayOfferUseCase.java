package superoffers.core.usecases;

import java.util.UUID;

import superoffers.core.entities.DayOffer;

/**
 * Describes the remove day offer use case according to the requirements proposed.
 * More details, see: {@link <a href="https://github.com/dancodingbr/superoffers/blob/main/docs/requirements.md">requirements document</a>}.
 *
 * @author dancodingbr
 */
public interface RemoveDayOfferUseCase {
    DayOffer delete(UUID id); 
}
