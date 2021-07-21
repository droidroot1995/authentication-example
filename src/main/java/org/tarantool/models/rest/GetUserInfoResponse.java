/*
 * Authentication example API
 * Authentication example API
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.tarantool.models.rest;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * GetUserInfoResponse
 */


public class GetUserInfoResponse {
  @JsonProperty("uuid")
  private String uuid = null;

  @JsonProperty("login")
  private String login = null;

  @JsonProperty("password")
  private String password = null;

  public GetUserInfoResponse uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

   /**
   * Get uuid
   * @return uuid
  **/
  @Schema(required = true, description = "")
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public GetUserInfoResponse login(String login) {
    this.login = login;
    return this;
  }

   /**
   * Get login
   * @return login
  **/
  @Schema(required = true, description = "")
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public GetUserInfoResponse password(String password) {
    this.password = password;
    return this;
  }

   /**
   * Get password
   * @return password
  **/
  @Schema(required = true, description = "")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetUserInfoResponse getUserInfoResponse = (GetUserInfoResponse) o;
    return Objects.equals(this.uuid, getUserInfoResponse.uuid) &&
        Objects.equals(this.login, getUserInfoResponse.login) &&
        Objects.equals(this.password, getUserInfoResponse.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, login, password);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetUserInfoResponse {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}