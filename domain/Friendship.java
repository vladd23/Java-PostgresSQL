package ro.ubbcluj.map.domain;

import ro.ubbcluj.map.domain.Entity;

public class Friendship extends Entity<Long> {
    private Long id_friendship;
    private Long id_user1;
    private Long id_user2;


    public Friendship(Long id_friendship, Long id_user1, Long id_user2) {
        this.id_friendship = id_friendship;
        this.id_user1 = id_user1;
        this.id_user2 = id_user2;
    }

    public Long getId_friendship() {
        return id_friendship;
    }

    public void setId_friendship(Long id_friendship) {
        this.id_friendship = id_friendship;
    }

    public Long getId_user1() {
        return id_user1;
    }

    public void setId_user1(Long id_user1) {
        this.id_user1 = id_user1;
    }

    public Long getId_user2() {
        return id_user2;
    }

    public void setId_user2(Long id_user2) {
        this.id_user2 = id_user2;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id_friendship=" + id_friendship +
                ", id_user1=" + id_user1 +
                ", id_user2=" + id_user2 +
                '}';
    }
}
