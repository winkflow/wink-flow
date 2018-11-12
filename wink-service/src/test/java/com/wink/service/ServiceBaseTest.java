package com.wink.service;

import com.wink.WinkServiceApplication;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = WinkServiceApplication.class)
public class ServiceBaseTest {

    protected String prettyResult(Object result) {
        ObjectMapper mapper = new ObjectMapper();
        if (result instanceof String) {
            System.out.println(result);
            return result.toString();
        }
        try {
            final String string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            System.out.println(string);
            return string;
        } catch (Exception e) {
            System.out.println(result.toString());
            return result.toString();
        }
    }

}
