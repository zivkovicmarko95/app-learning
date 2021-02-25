package monitoringapi.services;

import java.util.List;

public interface BaseMethods<T> {
    
    T findById(String id);
    
    List<T> findAll();
    
    T save(T object);
    
    void deleteById(String id);
    
    void deleteAll();

}
