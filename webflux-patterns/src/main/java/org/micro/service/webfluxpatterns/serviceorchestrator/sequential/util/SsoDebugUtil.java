package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;

public class SsoDebugUtil {

    public static void print(SsoOrchestrationRequestContext ctx) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ctx));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
