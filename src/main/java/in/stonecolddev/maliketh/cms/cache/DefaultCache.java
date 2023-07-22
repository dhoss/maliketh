package in.stonecolddev.maliketh.cms.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import java.util.function.Supplier;


public class DefaultCache<K, V> implements Cache<K, V>{

  private final Logger log = LoggerFactory.getLogger(DefaultCache.class);
  private final ConcurrentSkipListMap<K, V> cache;

  // TODO: figure out how to set a default comparitor
  public DefaultCache(Comparator<? super K> comparator) {
    this.cache = new ConcurrentSkipListMap<>(comparator);
  }

  @Override
  public V put(K key, V value) {
    cache.put(key, value);
    return value;
  }

  @Override
  public Optional<V> get(K key) {
    return Optional.ofNullable(cache.get(key));
  }

  @Override
  public V get(K key, Function<K, V> loader) {
    return cache.computeIfAbsent(key, loader);
  }

  @Override
  public Map<K, V> getAll() {
    return cache;
  }

  @Override
  public Map<K, V> getAll(Supplier<Map<K, V>> loader){
    // TODO: handle cache expiration
    if (cache.isEmpty()) {
      log.info("Cache is empty, loading.");
      return load(loader);
    }
    return cache;
  }

  @Override
  public Map<K, V> load(Supplier<Map<K, V>> loadingFunction) {
    log.info("Populating cache from supplier");
    for (var m : loadingFunction.get().entrySet()) {
      log.info("Put {} -> {}", m.getKey(), m.getValue());
      cache.put(m.getKey(), m.getValue());
    }
    log.info("Cache population complete");

    return cache;
  }
}