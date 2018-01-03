package org.thecuriousdev.authserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Serializer<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(Serializer.class);

  private final ObjectMapper mapper;

  private final Class<T> typeClass;

  public Serializer(ObjectMapper mapper, Class<T> typeClass) {
    this.mapper = mapper;
    this.typeClass = typeClass;
  }

  public Optional<String> seralize(Object value) {
    try {
      return Optional.ofNullable(mapper.writeValueAsString(value));
    } catch (JsonProcessingException e) {
      LOGGER.info("Failed to serialize object {}", value, e);
      return Optional.empty();
    }
  }

  public Optional<T> deserialize(String json) {
    try {
      return Optional.ofNullable(mapper.readValue(json, typeClass));
    } catch (IOException e) {
      LOGGER.info("Failed to deserialize json {}", json, e);
      return Optional.empty();
    }
  }
}
