package tn.esprit.userservice.Libs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
public class GenericCRUDService<T,Object> implements IGenericCRUD<T, Object> {
    private final JpaRepository<T, Object> customRepository;
    public GenericCRUDService( JpaRepository<T, Object> customRepository) {  this.customRepository = customRepository;  }
    @Override
    public List<T> selectAll() {   return customRepository.findAll();  }
    @Override
    public T selectById(Object o) { return (T) customRepository.findById(o).orElse(null);  }
    @Override
    public T insert(T object) {return customRepository.save(object);}
    @Override
    public List<T> insertAll(List<T> object) {
        List<T> list = new ArrayList<T>();
        for ( T obj : object ){
            list.add(this.insert(obj));
        }
        return list;
    }
    @Override
    public T update(Object object, T object2) {
        T obj = (T) customRepository.findById(object);
        return customRepository.save(object2);
    }
    @Override
    public boolean delete(Object id) {
        boolean deleted = false;
        T obj  = customRepository.findById(id).orElse(null) ;
        if (obj != null ) {
            customRepository.delete(obj);
            deleted = true;
        }
        return deleted;
    }
    @Override
    public void deleteAll() {  customRepository.deleteAll();  }
}
