package superoffers.impl;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import superoffers.core.entities.DayOffer;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@DynamoDbBean
public class DayOfferImpl implements DayOffer {

    private UUID id;
    private String productName;
    private Double price;
    private Double quantityAvailable;
    private String productUnit;
    private Instant offerExpirationDate;
    private String supermarketName;
    private Long userId;

    public DayOfferImpl() {
    }

    public DayOfferImpl(UUID id, String productName, Double price, Double quantityAvailable, String productUnit, Instant offerExpirationDate, String supermarketName, Long userId) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.productUnit = productUnit;
        this.offerExpirationDate = offerExpirationDate;
        this.supermarketName = supermarketName;
        this.userId = userId;
    }

    @Override
    @DynamoDbPartitionKey
    public UUID getId() {
        return this.id;
    }

    /*
        @Override
        @DynamoDbPartitionKey
        public Long getId() {
            return this.id != null ? this.id.getMostSignificantBits() : null;
        }
    */
    @Override
    public String getProductName() {
        return this.productName;
    }

    @Override
    @DynamoDbSortKey
    public Double getPrice() {
        return this.price;
    }

    @Override
    public Double getQuantityAvailable() {
        return this.quantityAvailable;
    }

    @Override
    public String getProductUnit() {
        return this.productUnit;
    }

    @Override
    public Instant getOfferExpirationDate() {
        return this.offerExpirationDate;
    }

    @Override
    public String getSupermarketName() {
        return this.supermarketName;
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantityAvailable(Double quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public void setOfferExpirationDate(Instant offerExpirationDate) {
        this.offerExpirationDate = offerExpirationDate;
    }

    public void setSupermarketName(String supermarketName) {
        this.supermarketName = supermarketName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayOfferImpl dayOffer = (DayOfferImpl) o;
        return Objects.equals(id, dayOffer.id) && Objects.equals(productName, dayOffer.productName) && Objects.equals(price, dayOffer.price) && Objects.equals(quantityAvailable, dayOffer.quantityAvailable) && Objects.equals(productUnit, dayOffer.productUnit) && Objects.equals(offerExpirationDate, dayOffer.offerExpirationDate) && Objects.equals(supermarketName, dayOffer.supermarketName) && Objects.equals(userId, dayOffer.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, price, quantityAvailable, productUnit, offerExpirationDate, supermarketName, userId);
    }

}
