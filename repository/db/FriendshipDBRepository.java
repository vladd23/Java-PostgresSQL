package ro.ubbcluj.map.repository.db;


import ro.ubbcluj.map.domain.Friendship;
import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.validators.Validator;
import ro.ubbcluj.map.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;

public class FriendshipDBRepository implements Repository<Long, Friendship> {

    private String url;
    private String username;
    private String password;
    private Validator<Friendship> validator;

    public FriendshipDBRepository(String url, String username, String password, Validator<Friendship> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Friendship findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from friendships order by id_friendship");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id_friendship");
                Long idUser1 = resultSet.getLong("id_user1");
                Long idUser2 = resultSet.getLong("id_user2");

                Friendship friendship = new Friendship(id,idUser1,idUser2 );
                friendships.add(friendship);

            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public Friendship save(Friendship entity) {

        String sql = "insert into friendships (id_friendship, id_user1, id_user2 ) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password); // connectionu incearca sa se conecteze la baza de date

             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, entity.getId_friendship());
            ps.setLong(2, entity.getId_user1());
            ps.setLong(3, entity.getId_user2());


            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship delete(Long aLong) {
        String sqlCommand = "DELETE FROM friendships f\n" +
                "where f.id_user1 = (?) or f.id_user2 = (?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); // connectionu incearca sa se conecteze la baza de date
             PreparedStatement ps = connection.prepareStatement(sqlCommand)){

            ps.setLong(1, aLong);
            ps.setLong(2, aLong);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship update(Friendship entity) {
        return null;
    }

    @Override
    public Set<Utilizator> showFriendsForUser(Long userId) {
        return null;
    }

    @Override
    public void addFriendToSQL(Utilizator u, Long u2) {

    }

    @Override
    public void updateUsersAfterDelete(Utilizator u) {

    }


}
