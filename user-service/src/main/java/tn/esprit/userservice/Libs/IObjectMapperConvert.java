package tn.esprit.userservice.Libs;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;



public interface IObjectMapperConvert {
     String convertToJsonString (Object obj) throws JsonProcessingException ;
     List<?> convertToObjectList (String json) throws JsonProcessingException ;
     List<?> convertToCollection (String json, Class<? extends Collection> collectionClass, Class<?> elementClass)   throws JsonProcessingException ;

     Object convertToObject (String json , Class<?> typeclass ) throws JsonProcessingException ;
     Object importFileToCollection(String filePath , Class<? extends Collection> collectionClass, Class<?> elementClass) throws IOException;

     Object  importFileToObject(String filePath , Class<?> elementClass) throws IOException;
     void exportObjectToFile(String filePath , Object payload) throws  IOException;
}