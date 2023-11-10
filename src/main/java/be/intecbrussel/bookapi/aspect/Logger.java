package be.intecbrussel.bookapi.aspect;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Logger {
    private  final String LOG_FILE = "src/main/resources/logs/";

    private  final SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM__yyy HH:mm:ss");
    public  void log(String message){

        String formattedMessage = String.format("[%s] : %s",dateFormat.format(new Date()),message);
        writeToFile("log-",formattedMessage);
    }
    public  void exceptionLog(Throwable exception){
        for (StackTraceElement ste:exception.getStackTrace()) {
            String line = ste.toString();
            String formattedMessage = String.format("[%s] : %s",dateFormat.format(new Date()),line);
            writeToFile("exception-",formattedMessage);
        }
    }
    private  void writeToFile(String fileName, String message){
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        try {
            Path path = Paths.get(LOG_FILE+fileName+formattedDate+".txt");
            if (!Files.exists(path)){
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(path.toFile(), true)));
            printWriter.println(message);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
