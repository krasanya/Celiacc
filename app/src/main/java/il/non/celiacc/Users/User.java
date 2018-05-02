package il.non.celiacc.Users;


import java.util.Date;

public class User {

    public String Email;
    public String Username;
    public String Firstname;
    public  String Lastname;
    public  String Password ;
    public  boolean IsMember ;
    public int memberNum;
    public  String Phone;
    public Date expireDate;

    public User(String email, String username, String firstname, String lastname, String password, boolean isMember, int memberNum, String phone, Date expireDate) {
        Email = email;
        Username = username;
        Firstname = firstname;
        Lastname = lastname;
        Password = password;
        IsMember = isMember;
        this.memberNum = memberNum;
        Phone = phone;
        this.expireDate = expireDate;
    }

    public User(String email, String username, String firstname, String lastname, String password, boolean isMember) {
        Email = email;
        Username = username;
        Firstname = firstname;
        Lastname = lastname;
        Password = password;
        IsMember = isMember;
    }



}
