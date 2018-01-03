package org.thecuriousdev.authserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class User implements AbstractDocument {

  @JsonIgnore
  public static final String DB_TYPE = "auth.user";

  private long cas;
  private String type;

  private String username;
  private String password;
  private List<String> roles;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  @Override
  public String getType() {
    return DB_TYPE;
  }

  @Override
  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String getId() {
    return ":" + DB_TYPE + ":" + username;
  }

  @Override
  public long getCas() {
    return cas;
  }

  @Override
  public void setCas(long cas) {
    this.cas = cas;
  }
}
