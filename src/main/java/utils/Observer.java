package utils;

/**
 * Standard Observer interface
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public interface Observer<T> {

    void update(T message);

}
