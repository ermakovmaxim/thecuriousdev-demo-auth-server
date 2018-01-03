package org.thecuriousdev.authserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AbstractDocument {

  String getType();
  void setType(String type);

  @JsonIgnore String getId();

  @JsonIgnore long getCas();
  @JsonIgnore void setCas(long cas);
}
