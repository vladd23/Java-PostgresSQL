package ro.ubbcluj.map.service;


import ro.ubbcluj.map.domain.Friendship;
import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.validators.UtilizatorValidator;
import ro.ubbcluj.map.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Service {
    private Repository<Long, Utilizator> userRepoService = null;
    private Repository<Long, Friendship> friendshipRepoService = null;

    private UtilizatorValidator userValidator = new UtilizatorValidator();

    public Service(Repository<Long, Utilizator> userRepoServicep, Repository<Long, Friendship> friendshipRepoServicep) {
        this.userRepoService = userRepoServicep;
        this.friendshipRepoService = friendshipRepoServicep;
    }

    /**
     * Functie care salveaza un user in baza de date
     * @param u
     */
    public void saveUser(Utilizator u){
        userValidator.validate(u);
        userRepoService.save(u);
    }

    /**
     *  functie care returneaza un iterable pentru parcurgerea tuturor utilizatorilor din baza de date
     * @return
     */
    public Iterable<Utilizator> findAllUsers(){
        return userRepoService.findAll();
    }

    /**
     * Functie care returneaza un user dupa id ul citit de la tastaura
     * @param aLong
     * @return
     */
    public Utilizator findOneUser(Long aLong){
        return userRepoService.findOne(aLong);
    }

    /**
     * Functie care gaseste toti prietenii unui utilizator al carui id il citim de la tastatura
     * @param id
     * @return
     */
    public Iterable<Utilizator> friendsForOneUser(Long id){
        return userRepoService.showFriendsForUser(id);
    }

    /**
     * Functie care sterge un user din baza de date dupa id
     * @param idUser
     * @return
     */
    public Utilizator deleteUser(Long idUser){
        Utilizator u1 = findOneUser(idUser);
        if(userInFriendship(u1)) friendshipRepoService.delete(u1.getId2());

        userRepoService.updateUsersAfterDelete(u1);
        return userRepoService.delete(idUser);

    }

    /**
     * Functie care gaseste toate prieteniile existenta in baza de date
     * @return
     */
    public Iterable<Friendship> getAllFriendships() {
        return friendshipRepoService.findAll();
    }

    /**
     * Functie care sterge un prieten din lista de prieteni al altui user
     * @param u1 de unde se sterge
     * @param u2 care se sterge
     */
    public void removeUserFromFriendList(Utilizator u1, Utilizator u2){
        String sql2 = "update users set friends_list = array_remove(friends_list, ?)\n" +
                "where id = (?)";

        try (Connection connection = DriverManager.getConnection(userRepoService.getUrl(), userRepoService.getUsername(), userRepoService.getPassword()); // connectionu incearca sa se conecteze la baza de date

             PreparedStatement ps = connection.prepareStatement(sql2)){

            ps.setLong(1, u2.getId2());
            ps.setLong(2, u1.getId2());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sterge o prietenie din baza de date pentru un user
     * @param idUserSelectat userul pentru care se sterge
     * @param idUserDeSters userul pe care nu l mai vrea ca prieten
     */
    public void deleteFriendshipUser(Long idUserSelectat, Long idUserDeSters ){
        Utilizator userSelectat = findOneUser(idUserSelectat);
        Utilizator userDeSters = findOneUser(idUserDeSters);

        String sqlCommand = "DELETE FROM friendships f\n" +
                "where f.id_user1 = (?) and f.id_user2 = (?)";
        try (Connection connection = DriverManager.getConnection(userRepoService.getUrl(), userRepoService.getUsername(),userRepoService.getPassword()); // connectionu incearca sa se conecteze la baza de date
             PreparedStatement ps = connection.prepareStatement(sqlCommand)){

            ps.setLong(1, idUserSelectat);
            ps.setLong(2, idUserDeSters);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        removeUserFromFriendList(userSelectat, userDeSters);
        removeUserFromFriendList(userDeSters, userSelectat);

    }

    /**
     * Functie care adauga o prietenie in baza de date
     * @param fr
     */
    public void addFriendshipInDataBase(Friendship fr){
        friendshipRepoService.save(fr);
        Utilizator u1 = findOneUser(fr.getId_user1());
        Utilizator u2 = findOneUser(fr.getId_user2());
        userRepoService.addFriendToSQL(u1, u2.getId2());
        userRepoService.addFriendToSQL(u2, u1.getId2());
    }

    /**
     * Functie care cauta un user intr o prietenie existenta in baza de date
     * @param user
     * @return
     */
    public boolean userInFriendship(Utilizator user){


        try (Connection connection = DriverManager.getConnection(userRepoService.getUrl(), userRepoService.getUsername(),userRepoService.getPassword());
             PreparedStatement statement = connection.prepareStatement("select * from friendships order by id_friendship");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Long idUser1 = resultSet.getLong("id_user1");
                Long idUser2 = resultSet.getLong("id_user2");

                if(user.getId2().equals(idUser1) || user.getId2().equals(idUser2)) return true;


            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}




