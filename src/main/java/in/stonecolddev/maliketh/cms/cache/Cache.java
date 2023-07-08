package in.stonecolddev.maliketh.cms.cache;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Cache<K, V> {

  V put(K key, V value);

  Optional<V> get(K key);

  V get(K key, Function<K, V> loader);

  Map<K, V> getAll();

  Map<K, V> getAll(Supplier<Map<K, V>> loader);

  Map<K, V> load(Supplier<Map<K, V>> loading);

}