package tn.esprit.userservice.Libs;


public interface IGenericMapperDto<S,T> {
      T mapToEntity(S Dto);
      S mapToDto(T Entity);}