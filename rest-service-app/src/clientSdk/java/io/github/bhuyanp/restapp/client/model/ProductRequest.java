package io.github.bhuyanp.restapp.client.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-23T17:05:35.167278-04:00[America/New_York]", comments = "Generator version: 7.16.0")
public class ProductRequest {

  private String title;

  /**
   * Product type.
   */
  public enum TypeEnum {
    ELECTRONICS("ELECTRONICS"),
    
    BABY("BABY"),
    
    BEAUTY("BEAUTY");

    private final String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  private Double price;

  public ProductRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductRequest(String title, TypeEnum type, Double price) {
    this.title = title;
    this.type = type;
    this.price = price;
  }

  public ProductRequest title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Title of the product.
   * @return title
   */
  @NotNull
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ProductRequest type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Product type.
   * @return type
   */
  @NotNull
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public ProductRequest price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * A non negative product price.
   * @return price
   */
  @NotNull
  @JsonProperty("price")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductRequest productRequest = (ProductRequest) o;
    return Objects.equals(this.title, productRequest.title) &&
        Objects.equals(this.type, productRequest.type) &&
        Objects.equals(this.price, productRequest.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, type, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductRequest {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

