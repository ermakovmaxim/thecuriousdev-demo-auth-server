package org.thecuriousdev.authserver.repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import java.util.Optional;
import org.thecuriousdev.authserver.entity.AbstractDocument;
import org.thecuriousdev.authserver.util.SerializationException;
import org.thecuriousdev.authserver.util.Serializer;


class AbstractRepository<T extends AbstractDocument> {

  private Serializer<T> serializer;
  private Bucket bucket;

  AbstractRepository(Serializer<T> serializer, Bucket bucket) {
    this.serializer = serializer;
    this.bucket = bucket;
  }

  void insert(T obj) {
    String json = serializer.seralize(obj)
        .orElseThrow(
            () -> new SerializationException("Failed to serialize object: " + obj.toString()));
    bucket.insert(RawJsonDocument.create(obj.getId(), json, obj.getCas()));
  }

  Optional<T> get(String id) {
    Optional<JsonDocument> optDoc = Optional.ofNullable(bucket.get(id));
    if (optDoc.isPresent()) {
      JsonDocument doc = optDoc.get();
      Optional<T> opt = serializer.deserialize(doc.content().toString());

      if (opt.isPresent()) {
        T obj = opt.get();
        obj.setCas(doc.cas());
        return Optional.of(obj);
      }
    }

    return Optional.empty();
  }
}
