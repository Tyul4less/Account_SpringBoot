package net.plang.HoWooAccount.system.common.configuration;

import lombok.Data;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ContainerConfig {

    @Value("${tomcat.ajp.port}")
    int ajpPort;
    @Value("${tomcat.ajp.remoteauthentication}")
    String remoteAuthentication;
    @Value("${tomcat.ajp.enabled}")
    boolean tomcatAjpEnabled;

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory Tomcat = new TomcatServletWebServerFactory();
        if (tomcatAjpEnabled) {
            Connector ajpConnector = new Connector("AJP/1.3");
            ajpConnector.setPort(ajpPort);
            ajpConnector.setSecure(false);
            ajpConnector.setAllowTrace(false);
            ajpConnector.setScheme("http");   // 여기는 https로 하지마세요. 이유는 https로 해보시면 아실 수 있으니, 굳이 설명하지 않습니다.
            ((AbstractAjpProtocol<?>) ajpConnector.getProtocolHandler()).setSecretRequired(false);
            Tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }
        return Tomcat;
    }
}
