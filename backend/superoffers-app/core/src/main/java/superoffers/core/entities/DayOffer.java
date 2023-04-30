package superoffers.core.entities;

import java.time.Instant;
import java.util.UUID;

/**
 * Describes day offer entity according to the requirements proposed.
 * More details, see: {@link <a href="https://github.com/dancodingbr/superoffers/blob/main/docs/requirements.md">requirements document</a>}.
 *
 * @author dancodingbr
 */
public interface DayOffer {
    UUID getId();

    String getProductName();

    Double getPrice();

    Double getQuantityAvailable();

    String getProductUnit();

    Instant getOfferExpirationDate();

    String getSupermarketName();

    Long getUserId();
}
