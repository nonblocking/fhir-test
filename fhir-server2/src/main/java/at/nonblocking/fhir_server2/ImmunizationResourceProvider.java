package at.nonblocking.fhir_server2;

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
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.Reference;

public class ImmunizationResourceProvider implements IResourceProvider {

   private final Map<String, Immunization> immunizationMap = new HashMap<>();

   public ImmunizationResourceProvider() {
      var imm1 = new Immunization();
      imm1.setId("1");
      imm1.setPatient(new Reference("Patient/1"));
      imm1.setVaccineCode(new CodeableConcept().setText("Vax 2010"));
      imm1.setOccurrence(new DateTimeType("2010-01-01T00:00:00Z"));
      immunizationMap.put("1", imm1);
      var imm2 = new Immunization();
      imm2.setId("2");
      imm1.setPatient(new Reference("Patient/1"));
      imm1.setVaccineCode(new CodeableConcept().setText("Vax 2015"));
      imm1.setOccurrence(new DateTimeType("2015-01-01T00:00:00Z"));
      immunizationMap.put("2", imm2);
   }

   @Override
   public Class<? extends IBaseResource> getResourceType() {
      return Immunization.class;
   }

   /**
    * Read
    */
   @Read()
   public Immunization read(@IdParam IdType theId) {
      var retVal = immunizationMap.get(theId.getIdPart());
      if (retVal == null) {
         throw new ResourceNotFoundException(theId);
      }
      return retVal;
   }

   /**
    * Search
    */
   @Search
   public List<Immunization> search(@RequiredParam(name = Immunization.SP_PATIENT) StringParam patientParam) {
      var retVal = new ArrayList<Immunization>();

      // Loop through the patients looking for matches
      for (var immunization : immunizationMap.values()) {
         if (immunization.getPatient().getReference().equals("Patient/" + patientParam.getValue())) {
            retVal.add(immunization);
         }
      }

      return retVal;
   }

}
