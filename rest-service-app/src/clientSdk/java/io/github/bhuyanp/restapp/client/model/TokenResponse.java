package io.github.bhuyanp.restapp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * TokenResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-31T06:19:25.366540-04:00[America/New_York]", comments = "Generator version: 7.16.0")
public class TokenResponse {

  private @Nullable String token;

  public TokenResponse token(@Nullable String token) {
    this.token = token;
    return this;
  }

  /**
   * Valid JWT token.
   * @return token
   */
  
  @JsonProperty("token")
  public @Nullable String getToken() {
    return token;
  }

  public void setToken(@Nullable String token) {
    this.token = token;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenResponse tokenResponse = (TokenResponse) o;
    return Objects.equals(this.token, tokenResponse.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenResponse {\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
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

