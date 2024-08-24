package com.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Rolodex extends Frame implements KeyListener {
    /*
     * TERMINAL COLOR
     */
    public static final String TEXT_BRIGHT_BG_CYAN   = "\u001B[106m"; 
    public static final String TEXT_RESET  = "\u001B[0m";

    // ROLODEX ACTIONS
    public static enum Action {
        ADD_CONTACT,
        DELETE_CONTACT,
        PREV_CONTACT,
        NEXT_CONTACT,
        UPDATE_CONTACT,
        EXIT
    }

     //ROLODEX SINGLETON
     private static Rolodex _rolodex = null;

     // CONTACTS
     private static List<Contact> _rolodexContacts;
     private static Contact _currContact;
     private int _currActionOrdinal;

    // Menu Command Map
    public static Map<Action, MenuCommand> menuCommands= new HashMap<Action, MenuCommand>();

    static {
        /*
         * Statically Assign all Menu Command for Key listener to execute
         * 
         */
        menuCommands.put(Action.ADD_CONTACT, ()->{ // Add contact to Rolodex
            System.out.println("Type the name, street address, city, state, zip code, and phone number.\n Separate all value using a ','.\n Example: Gary T Snail, 456 Pineapple rd, bikini bottom, AO, 89098, 454-544-0987\n Thank you.");

            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            String[] values = input.split(",");
            if(values.length < 6)
                throw new IllegalArgumentException("Incorrect values. Try again");

            Contact contact = new Contact(values[0], values[1], values[2], values[3], values[4], values[5]);
            Rolodex._rolodexContacts.add(contact); 

        });

        menuCommands.put(Action.DELETE_CONTACT, ()->{ // Delete contact from Rolodex
            System.out.println("Are you sure you want to delete contact: " + Rolodex._currContact);
            Scanner in = new Scanner(System.in);
            if(in.nextLine().equalsIgnoreCase("y"))
                Rolodex.deleteContact(Rolodex._currContact);
            else System.out.println("You did not confirm deletion. Please try again");
        });

        menuCommands.put(Action.PREV_CONTACT, ()->{ // Get Previous Contact in Rolodex
            Rolodex._currContact = Rolodex._rolodexContacts.get(((Rolodex._rolodexContacts.indexOf(Rolodex._currContact)) - 1 + Rolodex._rolodexContacts.size()) % Rolodex._rolodexContacts.size());});

        menuCommands.put(Action.NEXT_CONTACT, ()->{ // Get Next Contact in Rolodex
            Rolodex._currContact = Rolodex._rolodexContacts.get(((Rolodex._rolodexContacts.indexOf(Rolodex._currContact)) + 1) % Rolodex._rolodexContacts.size()); });

        menuCommands.put(Action.UPDATE_CONTACT, ()->{ // Update Contact in Rolodex
            System.out.println("Type the name, street address, city, state, zip code, and phone number.\n Separate all value using a ','.\n Example: Gary T Snail, 456 Pineapple rd, bikini bottom, AO, 89098, 454-544-0987\n Thank you.");

            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            String[] values = input.split(",");
            if(values.length < 6)
                throw new IllegalArgumentException("Incorrect values. Try again");
            Rolodex.updateContact(_currContact);
            _currContact.setName(values[0]);
            _currContact.setStreet(values[1]);
            _currContact.setCity(values[2]);
            _currContact.setState(values[3]);
            _currContact.setZip(values[4]);
            _currContact.setPhoneNumber(values[5]);
        });

        menuCommands.put(Action.EXIT, ()->{ //Exit the program
            System.exit(0);});
    }


   

    // GET SINGLETON
    public static Rolodex getInstance(){
        if(_rolodex == null) {
            _rolodex = new Rolodex();
        }
        return _rolodex;
    }

    public void initRolodex(){
        /*
         * INITIALIZE ROLODEX CONTACTS
         * INIT CURRENT CONTACT
         */
        addKeyListener(this);
        setVisible(true);
         _rolodexContacts = new ArrayList<>();
         _currActionOrdinal = 0;
         
    }

    public void render(){
        /*
         * RENDER ROLODEX INTERFACE
         */
        System.out.println(_currContact.toString());
        for (Action action : Action.values()){
            if(action == Action.values()[_currActionOrdinal])
                System.out.print(TEXT_BRIGHT_BG_CYAN+ action.toString() + " " + TEXT_RESET);
            else System.out.print(action + " ");
        }
    }

    /*
     * METHODS
     */

     public void menuCommand(Action action){ menuCommands.get(action).execute(); } // Executes command associated with menu action item

     public static void deleteContact(Contact contact){ _rolodexContacts.remove(contact); } // Delete contact method for JoinPoint

     public static void updateContact(Contact contact){ } //Empty method to create JoinPoint

     
     /*
      * SETTERS
      */

      public void setContacts(List<Contact> contacts) {
        /*
         * Method to set initial contacts
         */
        _rolodexContacts = contacts;
        _currContact = _rolodexContacts.get(0);
     }

    /*
    * KEY LISTENER METHODS
    * Implemented from KeyListener Interface
    */

    @Override
    public void keyPressed(KeyEvent e) {

        for(int n = 0; n < 35; n++ ) System.out.println(""); // Create space for next screen to be rendered
        int kc = e.getKeyCode();
        switch(kc) {
            case KeyEvent.VK_LEFT: // Move left in menu
                if( _currActionOrdinal == 0 ) _currActionOrdinal = 5;
                else _currActionOrdinal -= 1;
                break;
            case KeyEvent.VK_RIGHT: //Move right in menu
            if( _currActionOrdinal == 5) _currActionOrdinal = 0;
            else _currActionOrdinal += 1;
                break;
            case KeyEvent.VK_SPACE: //Select Current Menu item
                menuCommand(Action.values()[_currActionOrdinal]);
                break;
            default:
                break;
        }

        render(); // Force render on Key Event
    }

    @Override
    public void keyReleased(KeyEvent e) { }
    @Override
    public void keyTyped(KeyEvent e) { }
}
