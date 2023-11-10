package be.intecbrussel.bookapi.aspect;


import be.intecbrussel.bookapi.model.AuthUser;
import be.intecbrussel.bookapi.model.BorrowedBook;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Aspect
public class BorrowedBookAspect {
    private final Logger logger = new Logger();
    //Borrow book
    @Before("execution(* be.intecbrussel.bookapi.service.BorrowedBookService.borrowBook(..))")
    public void beforeBorrowBookAttempt(JoinPoint joinPoint){
        logger.log( "attempting to borrow a book : " + ((AuthUser)joinPoint.getArgs()[0]).getEmail());
    }
    @After("execution(* be.intecbrussel.bookapi.service.BorrowedBookService.borrowBook(..))")
    public void afterBorrowBookAttempt(JoinPoint joinPoint){
        logger.log(joinPoint.getSignature().getName()+"borrow book attempt has ended : "+((AuthUser)joinPoint.getArgs()[0]).getEmail());
    }
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.service.BorrowedBookService.borrowBook(..))",
            returning = "methodResult"
    )
    public void  afterReturningBorrowBook(JoinPoint joinPoint, Optional<BorrowedBook> methodResult){
        if (methodResult.isPresent()) {
            logger.log("borrow book attempt successful : " + ((AuthUser)joinPoint.getArgs()[0]).getEmail());
        }else {
            logger.log("borrow book attempt failed : no book found");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.service.BorrowedBookService.borrowBook(..))",
            throwing = "ex")
    public void afterThrowingBorrowBook(JoinPoint joinPoint,Exception ex) {
        logger.log("borrow book attempt failed : "+((AuthUser)joinPoint.getArgs()[0]).getEmail());
        logger.log("borrow book attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
    //Renew borrowed book
    @Before("execution(* be.intecbrussel.bookapi.service.BorrowedBookService.renewBorrowedBook(..))")
    public void beforeRenewBorrowedBookAttempt(JoinPoint joinPoint){
        logger.log( "attempting to renew Borrowed Book : " + ((AuthUser)joinPoint.getArgs()[0]).getEmail());
    }
    @After("execution(* be.intecbrussel.bookapi.service.BorrowedBookService.renewBorrowedBook(..))")
    public void afterRenewBorrowedBook(JoinPoint joinPoint){
        logger.log(joinPoint.getSignature().getName()+"renew borrowed book attempt has ended : "+((AuthUser)joinPoint.getArgs()[0]).getEmail());
    }
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.service.BorrowedBookService.renewBorrowedBook(..))",
            returning = "methodResult"
    )
    public void  afterReturningRenewBorrowedBook(JoinPoint joinPoint, boolean methodResult){
        if (methodResult) {
            logger.log("renew borrowed book attempt successful : " + ((AuthUser)joinPoint.getArgs()[0]).getEmail());
        }else {
            logger.log("renew borrowed book attempt failed : cannot renew");
        }
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.service.BorrowedBookService.renewBorrowedBook(..))",
            throwing = "ex")
    public void afterThrowingRenewBorrowedBook(JoinPoint joinPoint,Exception ex){
        logger.log("renew borrowed book attempt failed : "+((AuthUser)joinPoint.getArgs()[0]).getEmail());
        logger.log("renew borrowed book attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
    //Return borrowed book
    @Before("execution(* be.intecbrussel.bookapi.service.BorrowedBookService.returnBorrowedBook(..))")
    public void beforeReturnBorrowedBookAttempt(JoinPoint joinPoint){
        logger.log( "attempting to return Borrowed Book : " + joinPoint.getArgs()[0]);
    }
    @After("execution(* be.intecbrussel.bookapi.service.BorrowedBookService.returnBorrowedBook(..))")
    public void afterReturnBorrowedBook(JoinPoint joinPoint){
        logger.log(joinPoint.getSignature().getName()+"return borrowed book attempt has ended : " + joinPoint.getArgs()[0]);
    }
    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.service.BorrowedBookService.returnBorrowedBook(..))"
    )
    public void  afterReturningReturnBorrowedBook(JoinPoint joinPoint){
            logger.log("return borrowed book attempt successful " + joinPoint.getArgs()[0]);
    }
    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.service.BorrowedBookService.returnBorrowedBook(..))",
            throwing = "ex")
    public void afterThrowingReturnBorrowedBook(JoinPoint joinPoint,Exception ex){
        logger.log("return borrowed book attempt failed : " + joinPoint.getArgs()[0]);
        logger.log("return borrowed book attempt failed : "+ex.getMessage());
        logger.exceptionLog(ex);
    }
}
