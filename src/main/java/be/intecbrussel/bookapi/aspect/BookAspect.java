package be.intecbrussel.bookapi.aspect;

import be.intecbrussel.bookapi.model.Book;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Aspect
public class BookAspect {
    private final Logger logger = new Logger();

    //add book
    @Before("execution(* be.intecbrussel.bookapi.service.BookService.addBook(..))")
    public void beforeAddBook(JoinPoint joinPoint) {
        logger.log("attempting to add book : " + ((Book) joinPoint.getArgs()[0]).getTitle());
    }

    @After("execution(* be.intecbrussel.bookapi.service.BookService.addBook(..))")
    public void afterRegisterServiceMethods(JoinPoint joinPoint) {
        logger.log(joinPoint.getSignature().getName() + "add book attempt has ended : " + ((Book) joinPoint.getArgs()[0]).getTitle());
    }

    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.service.BookService.addBook(..))",
            returning = "methodResult"
    )
    public void afterReturningAddBook(JoinPoint joinPoint, Optional<Book> methodResult) {
        if (methodResult.isPresent()) {
            logger.log("add book attempt successful : " + ((Book) joinPoint.getArgs()[0]).getTitle());
        } else {
            logger.log("to add book attempt failed ");
        }
    }

    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.service.BookService.addBook(..))",
            throwing = "ex")
    public void afterThrowingAddBook(JoinPoint joinPoint, Exception ex) {
        logger.log("add book attempt failed : " + ((Book) joinPoint.getArgs()[0]).getTitle());
        logger.log("add book attempt failed : " + ex.getMessage());
        logger.exceptionLog(ex);
    }

    //update
    @Before("execution(* be.intecbrussel.bookapi.service.BookService.update(..))")
    public void beforeUpdate(JoinPoint joinPoint) {
        logger.log("attempting to update : " + ((Book) joinPoint.getArgs()[1]).getTitle());
    }

    @After("execution(* be.intecbrussel.bookapi.service.BookService.update(..))")
    public void afterUpdate(JoinPoint joinPoint) {
        logger.log(joinPoint.getSignature().getName() + "update attempt  has ended : " + ((Book) joinPoint.getArgs()[1]).getTitle());
    }

    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.service.BookService.update(..))",
            returning = "methodResult"
    )
    public void afterReturningUpdate(JoinPoint joinPoint, Optional<Book> methodResult) {
        if (methodResult.isPresent()) {
            logger.log("update attempt successful : " + ((Book) joinPoint.getArgs()[1]).getTitle());
        } else {
            logger.log("update attempt failed ");
        }
    }

    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.service.BookService.update(..))",
            throwing = "ex")
    public void afterThrowingUpdate(JoinPoint joinPoint, Exception ex) {
        logger.log("update attempt failed : " + ((Book) joinPoint.getArgs()[1]).getTitle());
        logger.log("update attempt failed : " + ex.getMessage());
        logger.exceptionLog(ex);
    }

    //delete book
    @Before("execution(* be.intecbrussel.bookapi.service.BookService.deleteBookById(..))")
    public void beforeDeleteBook(JoinPoint joinPoint) {
        logger.log("attempting to delete book : " + joinPoint.getArgs()[0]);
    }

    @After("execution(* be.intecbrussel.bookapi.service.BookService.deleteBookById(..))")
    public void afterDeleteBook(JoinPoint joinPoint) {
        logger.log(joinPoint.getSignature().getName() + "delete book attempt has ended : " + joinPoint.getArgs()[0]);
    }

    @AfterReturning(
            pointcut = "execution(* be.intecbrussel.bookapi.service.BookService.deleteBookById(..))",
            returning = "methodResult"
    )
    public void afterReturningDeleteBook(JoinPoint joinPoint, Optional<Book> methodResult) {
        if (methodResult.isPresent()) {
            logger.log("delete book attempt successful : " + joinPoint.getArgs()[0]);
        } else {
            logger.log("delete book attempt failed " + joinPoint.getArgs()[0]);
        }
    }

    @AfterThrowing(value = "execution(* be.intecbrussel.bookapi.service.BookService.deleteBookById(..))",
            throwing = "ex")
    public void afterThrowingDeleteBook(JoinPoint joinPoint, Exception ex) {
        logger.log("delete book attempt failed : " + joinPoint.getArgs()[0]);
        logger.log("delete book attempt failed : " + ex.getMessage());
        logger.exceptionLog(ex);
    }
}
