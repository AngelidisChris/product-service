package service.rest;

import com.fasterxml.jackson.annotation.JsonProperty;


import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;


public class Product {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty, should be between 1 and 100 characters")
    @Size(min = 1, max = 100, message = "Name should be between 1 and 100 characters")
    private String name;

    @JsonProperty("price")
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0.00")
    private BigDecimal price;

    @JsonProperty("productCode")
    @NotNull(message = "Product code cannot be null")
    private String productCode;

    @JsonProperty("expirationDate")
    @FutureOrPresent
    private Date expirationDate;

    @JsonProperty("availabilityDate")
    @NotNull(message = "Availability date code cannot be null")
    @FutureOrPresent
    private Date availabilityDate;

    @JsonProperty("active")
    @Min(value = 0, message = "Active field must be 0 or 1")
    @Max(value = 1, message = "Active field must be 0 or 1")
    private int active;

    public Product(){ }



    public Product(Long id, String name, BigDecimal price, String productCode, Date expirationDate, Date availabilityDate, int active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productCode = productCode;
        this.expirationDate = expirationDate;
        this.availabilityDate = availabilityDate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(Date availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public int isActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", productCode='" + productCode + '\'' +
                ", expirationDate=" + expirationDate +
                ", availabilityDate=" + availabilityDate +
                '}';
    }
}
