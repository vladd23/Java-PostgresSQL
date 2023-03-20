package ro.ubbcluj.map.ui;
import ro.ubbcluj.map.domain.Friendship;
import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.validators.ValidationException;
import ro.ubbcluj.map.service.Service;

import java.util.Scanner;
import java.util.Set;

public class UI {

    public static <E> void printEntity(Set<E> s){
        s.forEach(x -> System.out.println(x));
    }


    private Service service;
    public UI(Service service){

        this.service = service;
    }

    public void optionsMenu(){
        System.out.println("\n1. Adaugare utilizator in baza de date");
        System.out.println("2. Afisarea utilizatorilor din baza de date");
        System.out.println("3. Stergerea unui utilizator din baza de date");
        System.out.println("4. Afisarea unui utilizator dupa id");
        System.out.println("5. Crearea unei prietenii intre 2 utilizatori");
        System.out.println("6. Afisarea prietenilor unui utilizator");
        System.out.println("7. Afisarea tuturor prieteniilor din baza de date");
        System.out.println("8. Stergere prietenie pentru un user");
        System.out.println("100. Iesire");
    }

    public void runMenu(){

        Scanner scanner = new Scanner(System.in);

        while(true){
            optionsMenu();
            System.out.print("\nDati optiune: ");
            int optiune = scanner.nextInt();
            if(optiune == 1){
                try{
                    //Scanner scanner = new Scanner(System.in);
                    System.out.println("\nCititi un utilizator pentru a-l adauga in baza de date:");
                    System.out.print("Dati id: ");
                    Long id = scanner.nextLong();
                    System.out.print("Dati nume: ");
                    String fname = scanner.next();
                    System.out.print("Dati prenume: ");
                    String lname = scanner.next();

                    Utilizator u = new Utilizator(id,fname, lname, null);

                    service.saveUser(u);
                } catch (ValidationException e){
                    e.printStackTrace();
                }
            }
            else if(optiune == 2){
                Set<Utilizator> users = (Set<Utilizator>) service.findAllUsers();
                printEntity(users);
            }
            else if(optiune == 3){
                System.out.print("\nId-ul utilizatorului pe care doriti sa-l stergeti:");
                Long id = scanner.nextLong();
                service.deleteUser(id);
            }
            else if(optiune == 4){
                System.out.print("\nId-ul utilizatorului pe care doriti sa-l afisati:");
                Long id = scanner.nextLong();
                Utilizator u = service.findOneUser(id);
                if(u.getId2().equals(0L)) System.out.println("Utilizatorul cu id-ul "+ id + " nu exista");
                else System.out.println(u);
            } else if (optiune == 5) {
                System.out.print("\nId-ul prieteniei: ");
                Long idPr = scanner.nextLong();
                System.out.print("\nId-ul primului utilizator: ");
                Long id1 = scanner.nextLong();
                System.out.print("\nId-ul celui de-al doilea utilizator: ");
                Long id2 = scanner.nextLong();

                Friendship fr = new Friendship(idPr, id1, id2);
                service.addFriendshipInDataBase(fr);

            } else if (optiune == 6) {
                System.out.print("\nId-ul utilizatorului pentru care doriti sa afisati prietenii: ");
                Long id=scanner.nextLong();
                Set<Utilizator> friendsList = (Set<Utilizator>) service.friendsForOneUser(id);
                printEntity(friendsList);
            }
            else if(optiune == 7) {
                Set<Friendship> s = (Set<Friendship>) service.getAllFriendships();
                printEntity(s);
            }
            else if(optiune == 8){
                System.out.print("\nId-ul utilizatorului pentru care doriti sa stergeti un prieten: ");
                Long id = scanner.nextLong();
                Set<Utilizator> friendsList = (Set<Utilizator>) service.friendsForOneUser(id);
                printEntity(friendsList);

                System.out.print("\nAlegeti id-ul unui prieten de mai sus: ");
                Long idDeSters = scanner.nextLong();
                service.deleteFriendshipUser(id, idDeSters);
            }
            else if(optiune == 100) break;
            else System.out.println("Optiune invalida. Introduceti din nou: ");
        }


    }


}
