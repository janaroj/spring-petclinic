package org.springframework.samples.petclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity doesn't exist")
public class ResourceNotFoundException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public ResourceNotFoundException() {
      super();
   }

}
