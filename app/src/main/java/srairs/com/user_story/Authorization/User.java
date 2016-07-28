package srairs.com.user_story.Authorization;

import java.io.Serializable;

/**
 * Created by Andrii on 08.04.2016.
 */
public class User implements Serializable {

    String fname;
    String lname;
    String date;
    String mail;
    String pass;

    public User(User loggedUser){}
    public User(String firstName, String lastName, String date, String mail, String pass){
        super();

        setFname(firstName);
        setLname(lastName);
        setDate(date);
        setMail(mail);
        setPass(pass);
    }



    public void setDate(String date) {
        this.date = date;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getDate() {
        return date;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }


}
