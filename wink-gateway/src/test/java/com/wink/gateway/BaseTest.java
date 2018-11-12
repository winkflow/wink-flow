package com.wink.gateway;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GateWayApplication.class)
public class BaseTest {

    protected void prettyResult(Object result) {
        ObjectMapper mapper = new ObjectMapper();
        if (result instanceof String) {
            System.out.println(result);
            return;
        }
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception e) {
            System.out.println(result.toString());
        }
    }

}
