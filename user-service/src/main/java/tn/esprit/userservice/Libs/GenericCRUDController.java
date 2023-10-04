package tn.esprit.userservice.Libs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class GenericCRUDController<T,Object> {
    private final IGenericCRUD<T, Object> genericCRUDService;
    public GenericCRUDController( IGenericCRUD<T, Object> genericCRUDService) {  this.genericCRUDService = genericCRUDService;  }
    @GetMapping
    public List<T> SelectAll () { return genericCRUDService.selectAll () ; }

    @GetMapping("{id}")
    public ResponseEntity<T> SelectById (@PathVariable Object id) {
        return ResponseEntity.ok(genericCRUDService.selectById (id));}

    @PostMapping
    public T Insert( @RequestBody T obj) {return genericCRUDService.insert(obj);}

    @PutMapping("{id}")
    public  ResponseEntity<T> update( @PathVariable Object id ,@RequestBody T obj){
        return ResponseEntity.ok(genericCRUDService.update( id,  obj));}

    @DeleteMapping("{id}")
    public  ResponseEntity<HttpStatus> delete(@PathVariable  Object id ){
        genericCRUDService.delete( id ); return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
}
