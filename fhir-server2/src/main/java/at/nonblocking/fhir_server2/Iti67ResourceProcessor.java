package at.nonblocking.fhir_server2;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.IdType;
import org.openehealth.ipf.commons.ihe.fhir.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("iti67ResourceProcessor")
public class Iti67ResourceProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(Iti67ResourceProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        // Read
        if (exchange.getIn().getBody() instanceof IdType id) {
            logger.info("Processing DocumentReference read request: {}", id);
            if (id.getResourceType().equals("DocumentReference") && id.getIdPart().equals("1")) {
                var parameters = exchange.getIn().getHeaders();

                var documentRef = new DocumentReference();
                documentRef.setId("1");
                documentRef.setDescription("test");

                var resultMessage = exchange.getMessage();
                resultMessage.setBody(documentRef);
                resultMessage.getHeaders().putAll(parameters);
                resultMessage.setHeader(Constants.FHIR_REQUEST_GET_ONLY, true);
            } else {
                // Not found
                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
            }
            return;
        }

        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
    }
}
