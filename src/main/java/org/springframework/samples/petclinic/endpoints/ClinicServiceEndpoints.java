package org.springframework.samples.petclinic.endpoints;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.jaxb2.AddOwnerRequest;
import org.springframework.samples.petclinic.jaxb2.AddOwnerResponse;
import org.springframework.samples.petclinic.jaxb2.AddPetRequest;
import org.springframework.samples.petclinic.jaxb2.AddPetResponse;
import org.springframework.samples.petclinic.jaxb2.AddVetRequest;
import org.springframework.samples.petclinic.jaxb2.AddVetResponse;
import org.springframework.samples.petclinic.jaxb2.AddVisitRequest;
import org.springframework.samples.petclinic.jaxb2.AddVisitResponse;
import org.springframework.samples.petclinic.jaxb2.DeleteOwnerRequest;
import org.springframework.samples.petclinic.jaxb2.DeleteOwnerResponse;
import org.springframework.samples.petclinic.jaxb2.DeletePetRequest;
import org.springframework.samples.petclinic.jaxb2.DeletePetResponse;
import org.springframework.samples.petclinic.jaxb2.DeleteVetRequest;
import org.springframework.samples.petclinic.jaxb2.DeleteVetResponse;
import org.springframework.samples.petclinic.jaxb2.DeleteVisitRequest;
import org.springframework.samples.petclinic.jaxb2.DeleteVisitResponse;
import org.springframework.samples.petclinic.jaxb2.GeneratedOwner;
import org.springframework.samples.petclinic.jaxb2.GeneratedPet;
import org.springframework.samples.petclinic.jaxb2.GeneratedPetType;
import org.springframework.samples.petclinic.jaxb2.GeneratedSpecialty;
import org.springframework.samples.petclinic.jaxb2.GeneratedVet;
import org.springframework.samples.petclinic.jaxb2.GeneratedVisit;
import org.springframework.samples.petclinic.jaxb2.GetOwnerRequest;
import org.springframework.samples.petclinic.jaxb2.GetOwnerResponse;
import org.springframework.samples.petclinic.jaxb2.GetPetRequest;
import org.springframework.samples.petclinic.jaxb2.GetPetResponse;
import org.springframework.samples.petclinic.jaxb2.GetVetRequest;
import org.springframework.samples.petclinic.jaxb2.GetVetResponse;
import org.springframework.samples.petclinic.jaxb2.GetVisitRequest;
import org.springframework.samples.petclinic.jaxb2.GetVisitResponse;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ClinicServiceEndpoints {
   @Autowired
   private ClinicService clinicService;

   private static final String TARGET_NAMESPACE = "http://org.spring.framework.petclinic";

   @PayloadRoot(localPart = "GetOwnerRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   GetOwnerResponse getOwnerDetails(@RequestPayload GetOwnerRequest request) {
      Owner owner = clinicService.findOwnerById(request.getId());
      GetOwnerResponse response = new GetOwnerResponse();
      response.setOwner(convertOwner(owner));
      return response;
   }

   @PayloadRoot(localPart = "GetPetRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   GetPetResponse getPetDetails(@RequestPayload GetPetRequest request) throws DatatypeConfigurationException {
      GetPetResponse response = new GetPetResponse();
      response.setPet(convertPet(clinicService.findPetById(request.getId())));
      return response;
   }

   @PayloadRoot(localPart = "GetVetRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   GetVetResponse getVetDetails(@RequestPayload GetVetRequest request) {
      GetVetResponse response = new GetVetResponse();
      response.setVet(convertVet(clinicService.findVetById(request.getId())));
      return response;
   }

   @PayloadRoot(localPart = "GetVisitRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   GetVisitResponse getVisitDetails(@RequestPayload GetVisitRequest request) throws DatatypeConfigurationException {
      GetVisitResponse response = new GetVisitResponse();
      response.setVisit(convertVisit(clinicService.findVisitById(request.getId())));
      return response;
   }

   @PayloadRoot(localPart = "AddOwnerRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   AddOwnerResponse addOwner(@RequestPayload AddOwnerRequest request) {
      int id = clinicService.saveOwner(convertOwner(request.getOwnerDetails()));
      AddOwnerResponse response = new AddOwnerResponse();
      response.setId(id);
      return response;
   }

   @PayloadRoot(localPart = "AddPetRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   AddPetResponse addPet(@RequestPayload AddPetRequest request) {
      int id = clinicService.savePet(convertPet(request.getPet()));
      AddPetResponse response = new AddPetResponse();
      response.setId(id);
      return response;
   }

   @PayloadRoot(localPart = "AddVisitRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   AddVisitResponse addVisit(@RequestPayload AddVisitRequest request) {
      int id = clinicService.saveVisit(convertVisit(request.getVisit()));
      AddVisitResponse response = new AddVisitResponse();
      response.setId(id);
      return response;
   }

   @PayloadRoot(localPart = "AddVetRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   AddVetResponse addVet(@RequestPayload AddVetRequest request) {
      int id = clinicService.saveVet(convertVet(request.getVet()));
      AddVetResponse response = new AddVetResponse();
      response.setId(id);
      return response;
   }

   @PayloadRoot(localPart = "DeleteVetRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   DeleteVetResponse deleteVet(@RequestPayload DeleteVetRequest request) {
      DeleteVetResponse response = new DeleteVetResponse();
      int id = clinicService.deleteVet(request.getId());
      response.setId(id);
      return response;
   }

   @PayloadRoot(localPart = "DeleteOwnerRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   DeleteOwnerResponse deleteOwner(@RequestPayload DeleteOwnerRequest request) {
      DeleteOwnerResponse response = new DeleteOwnerResponse();
      int id = clinicService.deleteOwner(request.getId());
      response.setId(id);
      return response;
   }

   @PayloadRoot(localPart = "DeleteVisitRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   DeleteVisitResponse deleteVisit(@RequestPayload DeleteVisitRequest request) {
      DeleteVisitResponse response = new DeleteVisitResponse();
      int id = clinicService.deleteVisit(request.getId());
      response.setId(id);
      return response;
   }

   @PayloadRoot(localPart = "DeletePetRequest", namespace = TARGET_NAMESPACE)
   public @ResponsePayload
   DeletePetResponse deletePet(@RequestPayload DeletePetRequest request) {
      DeletePetResponse response = new DeletePetResponse();
      int id = clinicService.deletePet(request.getId());
      response.setId(id);
      return response;
   }

   private Vet convertVet(GeneratedVet generatedVet) {
      Vet vet = new Vet();
      vet.setFirstName(generatedVet.getFirstName());
      vet.setLastName(generatedVet.getLastName());
      for (GeneratedSpecialty generatedSpecialty : generatedVet.getSpecialties()) {
         vet.addSpecialty(convertSpecialty(generatedSpecialty));
      }
      return vet;
   }

   private Specialty convertSpecialty(GeneratedSpecialty generatedSpecialty) {
      Specialty specialty = new Specialty();
      specialty.setName(generatedSpecialty.getName());
      return specialty;
   }

   private Visit convertVisit(GeneratedVisit generatedVisit) {
      Visit visit = new Visit();
      visit.setDate(XMLCalendarToDateTime(generatedVisit.getDate()));
      visit.setDescription(generatedVisit.getDescription());
      visit.setPet(clinicService.findPetById(generatedVisit.getPetId()));
      return visit;
   }

   private GeneratedOwner convertOwner(Owner owner) {
      GeneratedOwner ownerType = new GeneratedOwner();
      ownerType.setAddress(owner.getAddress());
      ownerType.setCity(owner.getCity());
      ownerType.setFirstName(owner.getFirstName());
      ownerType.setLastName(owner.getLastName());
      ownerType.setTelephone(owner.getTelephone());
      return ownerType;
   }

   private GeneratedVisit convertVisit(Visit visit) throws DatatypeConfigurationException {
      GeneratedVisit generatedVisit = new GeneratedVisit();
      generatedVisit.setDate(DateTimeToXMLCalendar(visit.getDate()));
      generatedVisit.setDescription(visit.getDescription());
      generatedVisit.setPetId(visit.getPet().getId());
      return generatedVisit;
   }

   private GeneratedVet convertVet(Vet vet) {
      GeneratedVet generatedVet = new GeneratedVet();
      generatedVet.setFirstName(vet.getFirstName());
      generatedVet.setLastName(vet.getLastName());
      for (Specialty specialty : vet.getSpecialties()) {
         generatedVet.getSpecialties().add(convertSpecialty(specialty));
      }
      return generatedVet;
   }

   private Owner convertOwner(GeneratedOwner generatedOwner) {
      Owner owner = new Owner();
      owner.setAddress(generatedOwner.getAddress());
      owner.setCity(generatedOwner.getCity());
      owner.setFirstName(generatedOwner.getFirstName());
      owner.setLastName(generatedOwner.getLastName());
      owner.setTelephone(generatedOwner.getTelephone());
      return owner;
   }

   private Pet convertPet(GeneratedPet generatedPet) {
      Pet pet = new Pet();
      pet.setName(generatedPet.getName());
      pet.setType(convertPetType(generatedPet.getType()));
      pet.setBirthDate(XMLCalendarToDateTime(generatedPet.getBirthDate()));
      pet.setOwner(clinicService.findOwnerById(generatedPet.getOwnerId()));
      return pet;
   }

   private GeneratedPet convertPet(Pet pet) throws DatatypeConfigurationException {
      GeneratedPet generatedPet = new GeneratedPet();
      generatedPet.setBirthDate(DateTimeToXMLCalendar(pet.getBirthDate()));
      generatedPet.setName(pet.getName());
      generatedPet.setOwnerId(pet.getOwner().getId());
      generatedPet.setType(convertPetType(pet.getType()));
      return generatedPet;
   }

   private GeneratedPetType convertPetType(PetType petType) {
      GeneratedPetType generatedPetType = new GeneratedPetType();
      generatedPetType.setTypeId(petType.getId());
      return generatedPetType;
   }

   private PetType convertPetType(GeneratedPetType generatedPetType) {
      PetType petType = new PetType();
      petType.setId(generatedPetType.getTypeId());
      return petType;
   }

   private DateTime XMLCalendarToDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
      return new DateTime(xmlGregorianCalendar.toGregorianCalendar().getTime());
   }

   private XMLGregorianCalendar DateTimeToXMLCalendar(DateTime dateTime) throws DatatypeConfigurationException {
      DatatypeFactory dataTypeFactory = DatatypeFactory.newInstance();
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTimeInMillis(dateTime.getMillis());
      return dataTypeFactory.newXMLGregorianCalendar(gc);

   }

   private GeneratedSpecialty convertSpecialty(Specialty specialty) {
      GeneratedSpecialty generatedSpecialty = new GeneratedSpecialty();
      generatedSpecialty.setName(specialty.getName());
      return generatedSpecialty;
   }

}
