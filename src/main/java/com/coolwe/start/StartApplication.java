package com.coolwe.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

@SpringBootApplication
public class StartApplication  implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    public static void main(String[] args) {

        SpringApplication.run(StartApplication.class, args);
    }
        @Override
        public void customize(TomcatServletWebServerFactory factory) {
            factory.addConnectorCustomizers(connector -> connector.setAttribute("relaxedQueryChars", "{}[]|''"));
        }

}
