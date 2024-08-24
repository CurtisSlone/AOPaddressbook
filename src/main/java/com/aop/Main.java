package com.aop;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static List<Contact> contacts = Arrays.asList(
        new Contact("Jerry Garcia", "278 Rocky rd","Santa Monica", "CA", "90260", "619-786-9898"),
        new Contact("Gordon Ramsey", "567 Angry Chef Lane", "London", "NH", "65785", "245-234-4323"),
        new Contact("Chandler Bing", "56 Cup o Joe St", "New York", "NY", "89078", "718-123-9089"),
        new Contact("Barry Allen"," 549 Run Fast Way", "Baltimore", "MD", "09879", "235-345-4949"),
        new Contact("Monica Smith", "12 Main St", "Baton Rouge", "LA", "70565", "234-435-7898")
        );
    public static Rolodex rolodex;
    public static void main(String[] args) {
        rolodex = Rolodex.getInstance();
        rolodex.initRolodex();
        rolodex.setContacts(Main.contacts);
        rolodex.render();
        while(true){}
    }
}