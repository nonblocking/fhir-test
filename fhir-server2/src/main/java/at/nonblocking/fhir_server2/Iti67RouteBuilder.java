package at.nonblocking.fhir_server2;

import jakarta.annotation.Resource;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Iti67RouteBuilder extends RouteBuilder {

    @Resource(name = "iti67ResourceProcessor")
    private Processor documentReferenceResourceProcessor;

    @Override
    public void configure() throws Exception {
        from("mhd-iti67://service?audit=true")
                .process(this.documentReferenceResourceProcessor);
    }
}
