package be.intecbrussel.bookapi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BorrowedBookControllerAspect {
    private final Logger logger = new Logger();
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.controller.BorrowedBookController.borrowBook(..))",
            returning = "methodResult"
    )
    public void  afterReturningBorrowBook(ResponseEntity methodResult){
        if (methodResult!= null) {
            logger.log("controller borrow book attempt successful : " +methodResult.getStatusCode());
        }else {
            logger.log("controller borrow book attempt failed ");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.controller.BorrowedBookController.borrowBook(..))",
            throwing = "ex")
    public void afterThrowingBorrowBook(Exception ex){
        logger.log("controller borrow book attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.controller.BorrowedBookController.renewBorrowedBook(..))",
            returning = "methodResult"
    )
    public void  afterReturningRenewBook(ResponseEntity methodResult){
        if (methodResult!= null) {
            logger.log("controller renew book attempt successful : " +methodResult.getStatusCode());
        }else {
            logger.log("controller renew book attempt failed ");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.controller.BorrowedBookController.renewBorrowedBook(..))",
            throwing = "ex")
    public void afterThrowingRenewBook(Exception ex){
        logger.log("controller renew book attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.controller.BorrowedBookController.returnBorrowedBook(..))",
            returning = "methodResult"
    )
    public void  afterReturningReturnBook(ResponseEntity methodResult){
        if (methodResult!= null) {
            logger.log("controller return book attempt successful : " +methodResult.getStatusCode());
        }else {
            logger.log("controller return book attempt failed ");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.controller.BorrowedBookController.returnBorrowedBook(..))",
            throwing = "ex")
    public void afterThrowingReturnBook(Exception ex){
        logger.log("controller return book attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
}
