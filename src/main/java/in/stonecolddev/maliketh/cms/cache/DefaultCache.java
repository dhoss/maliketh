package in.stonecolddev.maliketh.cms.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;


public class DefaultCache<K, V> implements Cache<K, V>{

  private final Logger log = LoggerFactory.getLogger(DefaultCache.class);
  private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();

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
    if (cache.isEmpty()) {
      return load(loader);
    }
    return cache;
  }

  @Override
  public Map<K, V> load(Supplier<Map<K, V>> loadingFunction) {
    for (var m : loadingFunction.get().entrySet()) {
      cache.merge(m.getKey(), m.getValue(), (ogVal, newVal) -> newVal);
    }

    return cache;
  }
}