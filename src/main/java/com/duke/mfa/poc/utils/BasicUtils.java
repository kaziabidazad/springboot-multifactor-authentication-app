package com.duke.mfa.poc.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Kazi
 */
public class BasicUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(BasicUtils.class);

    /**
     * <p>
     * It returns the object in a <code>json</code> pretty format with single indent
     * </p>
     * 
     * @param o
     * @return
     */
    public static String prettifyObject(Object o) {
        String s = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            s = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error converting to pretty string : ", e);
        }
        return s;
    }

    public static String unPretify(String prettyString) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        String unprettyString = null;
        if (prettyString != null && !prettyString.isEmpty()) {
            try {
                jsonNode = objectMapper.readValue(prettyString, JsonNode.class);
            } catch (IOException e) {
                LOGGER.error("incorrect json in request body : " + prettyString);
            }
            if (jsonNode != null)
                unprettyString = jsonNode.toString();
        }
        return unprettyString;
    }

    public static String unPretify(Object prettyObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            String prettyString = objectMapper.writeValueAsString(prettyObject);
            jsonNode = objectMapper.readValue(prettyString, JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String unprettyString = null;
        if (jsonNode != null)
            unprettyString = jsonNode.toString();
        return unprettyString;
    }

}
