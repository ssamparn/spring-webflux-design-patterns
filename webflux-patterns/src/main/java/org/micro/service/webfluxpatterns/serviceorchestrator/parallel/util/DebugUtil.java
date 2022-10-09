package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.OrchestrationRequestContext;

public class DebugUtil {

    public static void print(OrchestrationRequestContext ctx) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ctx));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
