package io.github.bhuyanp.restapp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import org.springframework.lang.Nullable;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ProblemDetail
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-31T06:19:25.366540-04:00[America/New_York]", comments = "Generator version: 7.16.0")
public class ProblemDetail {

  private @Nullable URI type;

  private @Nullable String title;

  private @Nullable Integer status;

  private @Nullable String detail;

  private @Nullable URI instance;

  
  private Map<String, Object> properties = new HashMap<>();

  public ProblemDetail type(@Nullable URI type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @JsonProperty("type")
  public @Nullable URI getType() {
    return type;
  }

  public void setType(@Nullable URI type) {
    this.type = type;
  }

  public ProblemDetail title(@Nullable String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   */
  
  @JsonProperty("title")
  public @Nullable String getTitle() {
    return title;
  }

  public void setTitle(@Nullable String title) {
    this.title = title;
  }

  public ProblemDetail status(@Nullable Integer status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  
  @JsonProperty("status")
  public @Nullable Integer getStatus() {
    return status;
  }

  public void setStatus(@Nullable Integer status) {
    this.status = status;
  }

  public ProblemDetail detail(@Nullable String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Get detail
   * @return detail
   */
  
  @JsonProperty("detail")
  public @Nullable String getDetail() {
    return detail;
  }

  public void setDetail(@Nullable String detail) {
    this.detail = detail;
  }

  public ProblemDetail instance(@Nullable URI instance) {
    this.instance = instance;
    return this;
  }

  /**
   * Get instance
   * @return instance
   */
  
  @JsonProperty("instance")
  public @Nullable URI getInstance() {
    return instance;
  }

  public void setInstance(@Nullable URI instance) {
    this.instance = instance;
  }

  public ProblemDetail properties(Map<String, Object> properties) {
    this.properties = properties;
    return this;
  }

  public ProblemDetail putPropertiesItem(String key, Object propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Get properties
   * @return properties
   */
  
  @JsonProperty("properties")
  public Map<String, Object> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, Object> properties) {
    this.properties = properties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProblemDetail problemDetail = (ProblemDetail) o;
    return Objects.equals(this.type, problemDetail.type) &&
        Objects.equals(this.title, problemDetail.title) &&
        Objects.equals(this.status, problemDetail.status) &&
        Objects.equals(this.detail, problemDetail.detail) &&
        Objects.equals(this.instance, problemDetail.instance) &&
        Objects.equals(this.properties, problemDetail.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, title, status, detail, instance, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProblemDetail {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    instance: ").append(toIndentedString(instance)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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

