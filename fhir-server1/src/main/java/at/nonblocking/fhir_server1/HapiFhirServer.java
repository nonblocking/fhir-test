package at.nonblocking.fhir_server1;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import ca.uhn.fhir.rest.server.RestfulServer;

@WebServlet("/*")
public class HapiFhirServer extends RestfulServer {

    @Override
    protected void initialize() throws ServletException {
        // Create a context for the appropriate version
        setFhirContext(FhirContext.forR4());

        // Register resource providers
        registerProvider(new PatientResourceProvider());

        // Format the responses in nice HTML
        registerInterceptor(new ResponseHighlighterInterceptor());
    }

}
