package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Repository
public class PostRepository {

  private final ConcurrentHashMap<Long, Post> storage = new ConcurrentHashMap<>();
  private final AtomicLong id = new AtomicLong(0);

  public List<Post> all() {
    return new ArrayList<>(storage.values());
  }

  public Optional<Post> getById(long id) {
    if (storage.containsKey(id)) {
      return Optional.of(storage.get(id));
    }
    return Optional.empty();
  }

  public Post save(Post post) {
    id.set(post.getId());
    if (id.get() == 0) {
      if (storage.size() > 0) {
        List<Long> idList = new ArrayList<>(storage.keySet());
        Collections.sort(idList);
        post.setId(idList.get(idList.size() - 1) + 1); // use next free id
      } else {
        post.setId(1);
      }
    }
    storage.put(post.getId(), post);
    return post;
  }

  public void removeById(long id) {
    storage.remove(id);
  }

}
