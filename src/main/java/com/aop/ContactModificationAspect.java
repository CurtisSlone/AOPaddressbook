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

    // FILE ATTRIBUTES
    private final String filePath = "contactarchive.txt"; 
    File file = new File(filePath);

    // POINTCUTS TO MATCH ONLY CONTACT-MODIFICATION METHODS
    @Pointcut("execution(* com.aop.Rolodex.*Contact(Contact))")
    public void modifyContact(){}

    // AROUND ADVICE TO LOG CONTACT MODIFICATIONS TO A FILE
    @Around("modifyContact()")
    public Object logModification(ProceedingJoinPoint pjp) throws Throwable {

        
        Contact contact = (Contact) pjp.getArgs()[0]; // Get the contact object from the arguments of the join point
        String textToAppend = ""; // Prepare text to be appended to the file
        String methodName = pjp.getSignature().getName(); // Get the class name from the join point

        // Determine the type of modification (add, update, delete) and log the contact details to the file
        if ("updateContact".equals(methodName)) {
            textToAppend = "Updating contact: " + contact.toString() + "\n";
        } else if ("deleteContact".equals(methodName)) {
            textToAppend = "Deleting contact: " + contact.toString() + "\n";
        }

        // Check if the file exists, create it if it doesn't exist, and append the text to the file
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

        return pjp.proceed(); // Return
    }
}
