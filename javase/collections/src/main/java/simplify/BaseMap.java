package simplify;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/20
 */
public abstract class BaseMap<K, V> extends AbstractMap<K, V> {

    public void clear() {
        throw new UnsupportedOperationException("不关注的方法");
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("不关注的方法");
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException("不关注的方法");
    }

    public Collection<V> values() {
        throw new UnsupportedOperationException("不关注的方法");
    }

    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("不关注的方法");
    }

    // Overrides of JDK8 Map extension methods

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        throw new UnsupportedOperationException("不关注的方法");
    }

    @Override
    public V putIfAbsent(K key, V value) {
        throw new UnsupportedOperationException("不关注的方法");
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        throw new UnsupportedOperationException("不关注的方法");
    }

    @Override
    public V replace(K key, V value) {
        throw new UnsupportedOperationException("不关注的方法");
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        throw new UnsupportedOperationException("不关注的方法");
    }

    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException("不关注的方法");
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException("不关注的方法");
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        throw new UnsupportedOperationException("不关注的方法");
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw new UnsupportedOperationException("不关注的方法");
    }


    // Callbacks to allow LinkedHashMap post-actions
    void afterNodeAccess(HashMap.Node<K, V> p) {
    }

    void afterNodeInsertion(boolean evict) {
    }

    void afterNodeRemoval(HashMap.Node<K, V> p) {
    }
}
