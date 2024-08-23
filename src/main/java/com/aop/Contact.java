package com.aop;

public class Contact {

    private String _name;
    private String _street;
    private String _city;
    private String _state;
    private String _zip;
    private String _phoneNumber;

    /*
     * CONSTRUCTORS
     */

    // For loading from file using JACKSON JSON
    public Contact() { }

    // For creating new Contact
    public Contact(String name, String street, String city, String state, String zip, String phoneNumber) {
        _name = name;
        _street = street;
        _city = city;
        _state = state;
        _zip = zip;
        _phoneNumber = phoneNumber;
    }

    /*
     * GETTERS
     */

     public String getName() {
        return _name;
     }

     public String getStreet() {
        return _street;
     }

     public String getCity() {
        return _city;
     }

     public String getState() {
        return _state;
     }

     public String getZip() {
        return _zip;
     }

     public String getPhoneNumber() {
        return _phoneNumber;
     }


     /*
      * SETTERS
      */

      public void setName(String name) {
        _name = name;
     }

      public void setStreet(String street) {
        _street = street;
     }
     
      public void setCity(String city) {
        _city = city;
     }

      public void setState(String state) {
        _state = state;
     }

      public void setZip(String zip) {
        _zip = zip;
     }

      public void setPhoneNumber(String phoneNumber) {
        _phoneNumber = phoneNumber;
     }

     @Override
     public String toString() {
       return String.format("Name: %s\n City: %s\n State: %s\n Zip: %s\n Phone Number: %s\n", _name, _city, _state, _zip, _phoneNumber);
     }
}
