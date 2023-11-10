package be.intecbrussel.bookapi.aspect;

import be.intecbrussel.bookapi.model.dto.LoginRequest;
import be.intecbrussel.bookapi.model.dto.LoginResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Aspect
public class AuthAspect {
    private final Logger logger = new Logger();

    // Login
    @Before("execution(* be.intecbrussel.bookapi.service.AuthService.login(..))")
    public void beforeLoginAttempt(JoinPoint joinPoint) {
        logger.log("attempting to login : " + ((LoginRequest) joinPoint.getArgs()[0]).getEmail());
    }

    @After("execution(* be.intecbrussel.bookapi.service.AuthService.login(..))")
    public void afterRegisterServiceMethods(JoinPoint joinPoint) {
        logger.log(joinPoint.getSignature().getName() + " attempt to login has ended : " + ((LoginRequest) joinPoint.getArgs()[0]).getEmail());
    }

    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.service.AuthService.login(..))",
            returning = "methodResult"
    )
    public void afterReturningLogin(JoinPoint joinPoint, Optional<LoginResponse> methodResult) {
        if (methodResult.isPresent()) {
            logger.log("Log in attempt successful : " + ((LoginRequest) joinPoint.getArgs()[0]).getEmail());
        } else {
            logger.log("log in attempt failed : no user found");
        }
    }

    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.service.AuthService.login(..))",
            throwing = "ex")
    public void afterThrowingLogin(JoinPoint joinPoint, Exception ex) {
        logger.log("log in attempt failed : " + ((LoginRequest) joinPoint.getArgs()[0]).getEmail());
        logger.log("log in attempt failed : " + ex.getMessage());
        logger.exceptionLog(ex);

    }

    // Register
    @Before("execution(* be.intecbrussel.bookapi.service.AuthService.createUser(..))")
    public void beforeCreatAccountAttempt(JoinPoint joinPoint) {
        logger.log("attempting to Creat account : " + joinPoint.getArgs()[2]);
    }

    @After("execution(* be.intecbrussel.bookapi.service.AuthService.createUser(..))")
    public void afterCreatAccountServiceMethods(JoinPoint joinPoint) {
        logger.log(joinPoint.getSignature().getName() + " attempt to creat account has ended : " + joinPoint.getArgs()[2]);
    }

    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.service.AuthService.createUser(..))",
            returning = "methodResult"
    )
    public void afterReturningCreatAccount(JoinPoint joinPoint, Optional<LoginResponse> methodResult) {
        if (methodResult.isPresent()) {
            logger.log("creat account attempt successful : " + joinPoint.getArgs()[2]);
        } else {
            logger.log("creat account attempt failed : invalid email");
        }
    }

    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.service.AuthService.createUser(..))",
            throwing = "ex")
    public void afterThrowingCreatAccount(JoinPoint joinPoint, Exception ex) throws Throwable {
        logger.log("creat account attempt failed : " + joinPoint.getArgs()[2]);
        logger.log("creat account attempt failed : " + ex.getMessage());
        logger.exceptionLog(ex);

    }
}
