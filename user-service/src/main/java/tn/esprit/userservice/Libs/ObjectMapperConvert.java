package tn.esprit.userservice.Libs;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;

@Service("object-mapper-convert")

public class ObjectMapperConvert implements  IObjectMapperConvert {


    ObjectMapper mapper;
    ObjectMapperConvert (){
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }
    @Override
    public String convertToJsonString (Object obj) throws JsonProcessingException {
        //ObjectMapper mapper = new ObjectMapper();//https://www.tabnine.com/blog/how-to-convert-a-java-object-into-a-json-string/
        //mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(obj);
        return json;
    }
    @Override
    public List<?> convertToObjectList (String json) throws JsonProcessingException {
        //ObjectMapper mapper = new ObjectMapper();//https://www.tabnine.com/blog/how-to-convert-a-java-object-into-a-json-string/
        //mapper.registerModule(new JavaTimeModule());
        List<?> listObj = mapper.readValue(json, new TypeReference<List<?>>() {});
        return listObj;
    }
    @Override
    public List<?> convertToCollection (String json, Class<? extends Collection> collectionClass, Class<?> elementClass) throws JsonProcessingException {
        //ObjectMapper mapper = new ObjectMapper();//https://www.tabnine.com/blog/how-to-convert-a-java-object-into-a-json-string/
        //mapper.registerModule(new JavaTimeModule());
        JavaType type = mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
        return  mapper.readValue(json, type);
    }
    @Override
    public Object convertToObject (String json , Class<?> typeclass ) throws JsonProcessingException {
        //ObjectMapper mapper = new ObjectMapper();//https://www.tabnine.com/blog/how-to-convert-a-java-object-into-a-json-string/
        //mapper.registerModule(new JavaTimeModule());
        Object obj = mapper.readValue(json, typeclass);
        return obj;
    }
    @Override
    public void exportObjectToFile(String filePath , Object payload) throws IOException {
        String tringListMsg  = this.convertToJsonString(payload);
        File file = new File(filePath);
        FileWriter writer = new FileWriter(file);
        writer.write(tringListMsg);
        writer.close();
    }
    @Override                                              //List.class                                 ,     Msg.class
    public List<?>  importFileToCollection(String filePath , Class<? extends Collection> collectionClass, Class<?> elementClass) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            String content = new String(Files.readAllBytes(file.toPath()));
            JavaType type = mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
            return  mapper.readValue(content, type);
        }
        return null;
    }
    @Override                                           // Msg.class
    public Object  importFileToObject(String filePath , Class<?> elementClass) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            String content = new String(Files.readAllBytes(file.toPath()));
            return   mapper.readValue(content, elementClass);
        }
        return null;
    }
}