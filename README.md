
# FHIR Test Repo

FHIR sample setup consists of:

 * Two different FHIR servers (stores) based on HAPI-FHIR
 * An API Gateway (in this case basically a FHIR-Gateway) based on Spring Cloud Gateway
 * A simple client consuming the FHIR resources based on React 

## Requirements

 * Java >= 21
 * Node >= 20

## Run 

### FHIR Server 1

    cd fhir-server1
    ./mvnw spring-boot:run

Capabilities: http://localhost:5556/metadata 
Patient 1: http://localhost:5556/Patient/1

### FHIR Server 2

    cd fhir-server2
    ./mvnw spring-boot:run

Capabilities: http://localhost:5557/metadata
Immunization 1: http://localhost:5557/Immunization/1

### API/FHIR Gateway

    cd api-gateway
    ./mvnw spring-boot:run

Patient 1: http://localhost:5558/fhir/Patient/1
Immunization 1: http://localhost:5558/fhir/Immunization/1

### Frontend

    cd simple-frontend
    npm i
    npm start

Open: http://localhost:5559

Fetches a Patient resource via API Gateway from FHIR Server 1 (base URL http://localhost:5558/fhir)
