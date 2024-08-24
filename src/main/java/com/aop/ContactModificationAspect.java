package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Aspect
public class ContactModificationAspect {
    private final String filePath = "contactarchive.txt"; 
    File file = new File(filePath);


    @Pointcut("execution(* com.aop.Rolodex.*Contact(Contact))")
    public void modifyContact(){}

    @Around("modifyContact()")
    public Object logModification(ProceedingJoinPoint pjp) throws Throwable {

        Contact contact = (Contact) pjp.getArgs()[0];
        String textToAppend = "";
        String methodName = pjp.getSignature().getName();

        if ("addContact".equals(methodName)) {
            return pjp.proceed(); // Skip this method if only adding a contact
        } else if ("updateContact".equals(methodName)) {
            textToAppend = "Updating contact: " + contact.toString() + "\n";
        } else if ("deleteContact".equals(methodName)) {
            textToAppend = "Deleting contact: " + contact.toString() + "\n";
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file: " + e.getMessage());
                return null; // Exit if file creation fails
            }
        }

        // Append text to the file
        try (FileWriter writer = new FileWriter(file, true)) { 
            writer.write(textToAppend);
            System.out.println("Pre-Modication Contact archived");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }

        return pjp.proceed();
    }
}
