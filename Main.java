package ro.ubbcluj.map;

import ro.ubbcluj.map.domain.Friendship;
import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.validators.FriendshipValidator;
import ro.ubbcluj.map.domain.validators.UtilizatorValidator;
import ro.ubbcluj.map.repository.Repository;
import ro.ubbcluj.map.repository.db.FriendshipDBRepository;
import ro.ubbcluj.map.repository.db.UtilizatorDBRepository;
import ro.ubbcluj.map.service.Service;
import ro.ubbcluj.map.ui.UI;

import java.util.Iterator;
import java.util.Set;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {



        String url = "jdbc:postgresql://localhost:25565/academic";
        String user = "postgres";
        String password = "1111";
        Repository<Long, Utilizator> repoDB = new UtilizatorDBRepository(url, user, password, new UtilizatorValidator());
        Repository<Long, Friendship> repoFR = new FriendshipDBRepository(url, user, password, new FriendshipValidator());
        Service service = new Service(repoDB, repoFR);
        UI console = new UI(service);

        console.runMenu();


    }
}