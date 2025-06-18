package at.nonblocking.fhir_server1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

public class PatientResourceProvider implements IResourceProvider {

   private final Map<String, Patient> patientMap = new HashMap<>();

   public PatientResourceProvider() {
      var pat1 = new Patient();
      pat1.setId("1");
      pat1.addIdentifier().setSystem("http://acme.com/MRNs").setValue("7000135");
      pat1.addName().setFamily("Simpson").addGiven("Homer").addGiven("J");
      patientMap.put("1", pat1);
      var pat2 = new Patient();
      pat2.setId("2");
      pat2.addIdentifier().setSystem("http://acme.com/MRNs").setValue("7000135");
      pat2.addName().setFamily("Simpson").addGiven("Marge");
      patientMap.put("2", pat2);
   }

   @Override
   public Class<? extends IBaseResource> getResourceType() {
      return Patient.class;
   }

   /**
    * Read
    */
   @Read()
   public Patient read(@IdParam IdType theId) {
      var retVal = patientMap.get(theId.getIdPart());
      if (retVal == null) {
         throw new ResourceNotFoundException(theId);
      }
      return retVal;
   }

   /**
    * Search
    */
   @Search
   public List<Patient> search(@RequiredParam(name = Patient.SP_FAMILY) StringParam familyNameParam) {
      var retVal = new ArrayList<Patient>();

      for (var patient: patientMap.values()) {
         var familyName = patient.getNameFirstRep().getFamily().toLowerCase();
         if (familyName.contains(familyNameParam.getValue().toLowerCase())) {
            retVal.add(patient);
         }
      }

      return retVal;
   }

}
