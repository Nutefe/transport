package com.bluerizon.transport.helper;

import com.bluerizon.transport.response.Countries;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ReadeJsonFiles {

    public static List<Countries> readeCountry(){
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Countries>> typeReference = new TypeReference<List<Countries>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/CountryCodes.json");
        try {
            List<Countries> countries = mapper.readValue(inputStream,typeReference);
            return countries;
        } catch (IOException e){
            System.out.println("Unable to save users: " + e.getMessage());
            return null;
        }
    }

}
