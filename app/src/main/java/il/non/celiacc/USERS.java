package il.non.celiacc;

import java.util.Date;

public class USERS {
    private    String Email ;
    private    String Username;
    private    String FirstName;
    private    String LastName;
    private    String Password;
    private    boolean IsMember;
    private    Integer memberNum ;
    private    String Phone;
    private    int id;
    private Date expireDate;

    public USERS(String Email, String Username,String FirstName, String LastName,String Password) {
        this.Email = Email;
        this.Username = Username;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Password = Password;
        this.IsMember = false;
        this.memberNum = null;
        this.Phone = null;
        this.id = 0;
        this.expireDate = null;
    }

   /* public il.non.celiacc.USERS(String Email, String Username,String FirstName, String LastName,String Password) {
        this.Email = Email;
        this.Username = Username;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Password = Password;
        this.IsMember = true;
        this.memberNum = memberNum;
        this.Phone = Phone;
        this.id = id;
        this.expireDate = expireDate;
    }*/



    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUsername() {
        return Username;
    }
    public void setUsername(String Username) {
        this.Username = Username;
    }
    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    public String getLastName() {
        return LastName;
    }
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getmemberNum() {
        return memberNum;
    }
    public void setmemberNum(int memberNum) {
        this.memberNum = memberNum;
    }
    public boolean getIsMember() {
        return IsMember;
    }
    public void setIsMember(boolean IsMember) {
        this.IsMember = IsMember;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
    public Date getexpireDate() {
        return expireDate;
    }
    public void setexpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }




}
