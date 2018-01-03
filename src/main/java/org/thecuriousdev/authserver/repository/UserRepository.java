package org.thecuriousdev.authserver.repository;

import com.couchbase.client.java.Bucket;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.thecuriousdev.authserver.entity.User;
import org.thecuriousdev.authserver.util.Serializer;


@Repository
public class UserRepository extends AbstractRepository<User> {

  private static String ID_PRE_FIX = ":" + User.DB_TYPE + ":";

  @Autowired
  public UserRepository(Bucket bucket, ObjectMapper objectMapper) {
    super(new Serializer<>(objectMapper, User.class), bucket);
  }

  public void create(User user) {
    insert(user);
  }

  public Optional<User> findById(String id) {
    System.out.println(ID_PRE_FIX + id);
    return get(ID_PRE_FIX + id);
  }
}
