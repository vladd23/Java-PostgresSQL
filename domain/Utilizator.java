package ro.ubbcluj.map.domain;

import java.sql.Array;
import java.util.*;

public class Utilizator extends Entity<Long>{

    private Long id;
    private String firstName;
    private String lastName;

    private Array friends;

    public Utilizator(Long idp,String firstName, String lastName, Array friendsP) {
        this.id = idp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = friendsP;


    }

    public Utilizator() {

    }

    public Long getId2(){
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Array getFriends() {
        return friends;
    }

    /*public void addFriendInList(Utilizator u){
        friends.a;
    }*/

    @Override
    public String toString() {
        return "Utilizator { " +
                "id ='" + id + '\'' +
                ", firstName ='" + firstName + '\'' +
                ", lastName ='" + lastName + '\'' +
                ", friends= " + friends +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator that)) return false;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName());
    }
}