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

    public static Map<Action, MenuCommand> menuCommands= new HashMap<Action, MenuCommand>();

    static {
        menuCommands.put(Action.ADD_CONTACT, ()->{ 
            System.out.println("Type the name, street address, city, state, zip code, and phone number.\n Separate all value using a ','.\n Example: Gary T Snail, 456 Pineapple rd, bikini bottom, AO, 89098, 454-544-0987\n Thank you.");

            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            String[] values = input.split(",");

            if(values.length < 6)
                throw new IllegalArgumentException("Incorrect values. Try again");

            Contact contact = new Contact(values[0], values[1], values[2], values[3], values[4], values[5]);
            System.out.println("Contact: " + contact);
            Rolodex.addContact(contact);

     });
        menuCommands.put(Action.DELETE_CONTACT, ()->{});
        menuCommands.put(Action.PREV_CONTACT, ()->{ Rolodex._currContact = Rolodex._rolodexContacts.get(((Rolodex._rolodexContacts.indexOf(Rolodex._currContact)) - 1 + Rolodex._rolodexContacts.size()) % Rolodex._rolodexContacts.size());});
        menuCommands.put(Action.NEXT_CONTACT, ()->{ Rolodex._currContact = Rolodex._rolodexContacts.get(((Rolodex._rolodexContacts.indexOf(Rolodex._currContact)) + 1) % Rolodex._rolodexContacts.size()); });
        menuCommands.put(Action.UPDATE_CONTACT, ()->{});
        menuCommands.put(Action.EXIT, ()->{ System.exit(0);});
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

     public void menuCommand(Action action){
        menuCommands.get(action).execute();
     }

     public static void addContact(Contact contact){
        _rolodexContacts.add(contact);
     }
     
     /*
      * SETTERS
      */

      public void setContacts(List<Contact> contacts) {
        _rolodexContacts = contacts;
        _currContact = _rolodexContacts.get(0);
     }

    /*
    * KEY LISTENER METHODS
    * Implemented from KeyListener Interface
    */

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(int n = 0; n < 35; n++ ) System.out.println("");
        int kc = e.getKeyCode();
        switch(kc) {
            case KeyEvent.VK_LEFT:
                if( _currActionOrdinal == 0 ) _currActionOrdinal = 5;
                else _currActionOrdinal -= 1;
                break;
            case KeyEvent.VK_RIGHT:
            if( _currActionOrdinal == 5) _currActionOrdinal = 0;
            else _currActionOrdinal += 1;
                break;
            case KeyEvent.VK_SPACE:
                menuCommand(Action.values()[_currActionOrdinal]);
                break;
            default:
                break;
        }

        render(); // Force render on Key Event
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    

}
