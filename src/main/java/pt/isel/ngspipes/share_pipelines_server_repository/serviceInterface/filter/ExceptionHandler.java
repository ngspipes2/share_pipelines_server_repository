package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ngspipes.share_core.logic.service.exceptions.DuplicatedEntityException;
import pt.isel.ngspipes.share_core.logic.service.exceptions.NonExistentEntityException;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;

@CrossOrigin
@ControllerAdvice
@RestController
public class ExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = NonExistentEntityException.class)
    public String serverExceptionHandler(NonExistentEntityException e){
        logServiceException(e);

        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = DuplicatedEntityException.class)
    public String serverExceptionHandler(DuplicatedEntityException e){
        logServiceException(e);

        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ServiceException.class)
    public String serverExceptionHandler(ServiceException e){
        logServiceException(e);

        return e.getMessage();
    }

    private void logServiceException(ServiceException e){
        String msg = "Service Error: " + e.getMessage();

        LOGGER.error(msg, e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e){
        logException(e);

        return "There was an Internal Error please try again later!";
    }

    private void logException(Exception e){
        String msg = "Error: " + e.getMessage();

        LOGGER.error(msg, e);
    }

}