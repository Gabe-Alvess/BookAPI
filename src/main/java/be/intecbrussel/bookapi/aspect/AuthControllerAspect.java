package be.intecbrussel.bookapi.aspect;


import be.intecbrussel.bookapi.model.dto.LoginRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class AuthControllerAspect {
    private final Logger logger = new Logger();
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.controller.AuthController.login(..))",
            returning = "methodResult"
    )
    public void  afterReturningLogin(ResponseEntity methodResult){
        if (methodResult!= null) {
            logger.log("controller login attempt successful : " + methodResult.getStatusCode());
        }else {
            logger.log("controller login attempt failed");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.controller.AuthController.login(..))",
            throwing = "ex")
    public void afterThrowingLogin(Exception ex){
        logger.log("controller login attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.controller.AuthController.register(..))",
            returning = "methodResult"
    )
    public void  afterReturningCreatAccount(ResponseEntity methodResult){
        if (methodResult!= null) {
            logger.log("controller create account attempt successful : " +methodResult.getStatusCode());
        }else {
            logger.log("controller create account attempt failed");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.controller.AuthController.register(..))",
            throwing = "ex")
    public void afterThrowingCreatAccount(Exception ex){
        logger.log("controller create account attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
}
