/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package activity5_8;

/**
 *
 * @author WiNDows
 */
public class Faculty {
    private Integer facultyID;
    private String firstname;
    private String middlename;
    private String lastname;
    private String suffix;
    private String sex;
    private String contact;
    private String gmail;
    private String address;
    private boolean status;


    public Faculty(Integer facultyID, String firstname, String middlename, String lastname, String suffix, String sex, String contact, String gmail, String address) {
        this.facultyID = facultyID;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.suffix = suffix;
        this.sex = sex;
        this.contact = contact;
        this.gmail = gmail;
        this.address = address;
    }

    public Integer getFacultyID() {
        return facultyID;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getSex() {
        return sex;
    }

    public String getContact() {
        return contact;
    }

    public String getGmail() {
        return gmail;
    }

    public String getAddress() {
        return address;
    }
    
    
}
