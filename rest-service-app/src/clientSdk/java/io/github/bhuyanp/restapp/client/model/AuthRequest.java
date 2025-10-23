package io.github.bhuyanp.restapp.client.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AuthRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-23T17:05:35.167278-04:00[America/New_York]", comments = "Generator version: 7.16.0")
public class AuthRequest {

  private String username;

  private String password;

  public AuthRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AuthRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public AuthRequest username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Username
   * @return username
   */
  @NotNull
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AuthRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Passwrod
   * @return password
   */
  @NotNull
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthRequest authRequest = (AuthRequest) o;
    return Objects.equals(this.username, authRequest.username) &&
        Objects.equals(this.password, authRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthRequest {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

