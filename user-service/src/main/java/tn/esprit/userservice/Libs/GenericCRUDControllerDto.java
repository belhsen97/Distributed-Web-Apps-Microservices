package tn.esprit.userservice.Libs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

public class GenericCRUDControllerDto <S,T,Object> {
    private final IGenericCRUD<T, Object> genericCRUDService;
    private final IGenericMapperDto<S,T> genericMapperDto;
    public GenericCRUDControllerDto(IGenericCRUD<T, Object> genericCRUDService ,IGenericMapperDto<S,T> genericMapperDto)
    {  this.genericCRUDService = genericCRUDService;
        this.genericMapperDto = genericMapperDto;  }
    @GetMapping
    public List<S> SelectAll () {
        List<T>  listObj = genericCRUDService.selectAll () ;
        return listObj.stream()
                .map(Obj -> genericMapperDto.mapToDto(Obj))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<S> SelectById (@PathVariable Object id) {
        return ResponseEntity.ok(genericMapperDto.mapToDto(genericCRUDService.selectById(id)));}


    @PostMapping
    public S Insert( @RequestBody S objDto) {
        T obj =  genericMapperDto.mapToEntity(objDto);
    return genericMapperDto.mapToDto(  genericCRUDService.insert(obj) );}

    @PutMapping("{id}")
    public  ResponseEntity<S> update( @PathVariable Object id ,@RequestBody S objDto){
        T obj =  genericMapperDto.mapToEntity(objDto);
        return ResponseEntity.ok(
                genericMapperDto.mapToDto(  genericCRUDService.update( id,  obj)));}

    @DeleteMapping("{id}")
    public  ResponseEntity<HttpStatus> delete(@PathVariable  Object id ){
        genericCRUDService.delete( id ); return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
}
