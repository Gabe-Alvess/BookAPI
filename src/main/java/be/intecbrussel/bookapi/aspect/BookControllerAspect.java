package be.intecbrussel.bookapi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BookControllerAspect {
    private final Logger logger = new Logger();
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.controller.BookController.addBook(..))",
            returning = "methodResult"
    )
    public void  afterReturningAddBook(ResponseEntity methodResult){
        if (methodResult!= null) {
            logger.log("controller add book attempt successful : " +methodResult.getStatusCode());
        }else {
            logger.log("controller add book attempt failed ");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.controller.BookController.addBook(..))",
            throwing = "ex")
    public void afterThrowingAddBook(Exception ex){
        logger.log("controller add book attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.controller.BookController.addBook(..))",
            returning = "methodResult"
    )
    public void  afterReturningUpdate(ResponseEntity methodResult){
        if (methodResult!= null) {
            logger.log("controller update book attempt successful : " +methodResult.getStatusCode());
        }else {
            logger.log("controller update book attempt failed ");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.controller.BookController.addBook(..))",
            throwing = "ex")
    public void afterThrowingUpdate(Exception ex){
        logger.log("controller update book attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.controller.BookController.updateBook(..))",
            returning = "methodResult"
    )
    public void  afterReturningDelete(ResponseEntity methodResult){
        if (methodResult!= null) {
            logger.log("controller update book attempt successful : " +methodResult.getStatusCode());
        }else {
            logger.log("controller update book attempt failed ");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.controller.BookController.updateBook(..))",
            throwing = "ex")
    public void afterThrowingDelete(Exception ex){
        logger.log("controller update book attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
}
