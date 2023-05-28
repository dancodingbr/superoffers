package superoffers.core.usecases;

import superoffers.core.entities.DayOffer;

import java.util.List;
import java.util.UUID;

/**
 * Describes the view day offer use case according to the requirements proposed.
 * More details, see: {@link <a href="https://github.com/dancodingbr/superoffers/blob/main/docs/requirements.md">requirements document</a>}.
 *
 * @author dancodingbr
 */
public interface ViewDayOfferUseCase {
    DayOffer findById(UUID id);
    List<DayOffer> findAllByProductName(String productName);
    List<DayOffer> findAllBySupermarketName(String supermarketName);
}
