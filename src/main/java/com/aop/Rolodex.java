package com.aop;

import java.util.ArrayList;
import java.util.List;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Rolodex extends Frame implements KeyListener {
    /*
     * TERMINAL COLOR
     */
    public static final String TEXT_BRIGHT_BG_WHITE = "\u001B[47m";
    public static final String TEXT_COLOR_BLACK   = "\u001B[40m";
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
    private List<Contact> _rolodexContacts;
    private static Contact _currContact;
    private int _currActionOrdinal;

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

         _rolodexContacts = new ArrayList<>();
         _currActionOrdinal = 0;
         
    }

    public void render(){
        /*
         * RENDER ROLODEX INTERFACE
         */
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    /*
     * GETTERS
     */

     /*
      * SETTERS
      */

      public void setContacts(List<Contact> contacts) {
        _rolodexContacts = contacts;
     }

     public void setCurrentContact(Contact contact) {
        Rolodex._currContact = contact;
     }

}
