package ro.ubbcluj.map.repository.db;
import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.validators.Validator;
import ro.ubbcluj.map.repository.Repository;


import java.util.HashSet;
import java.util.Set;
import java.sql.*;


public class UtilizatorDBRepository implements Repository<Long, Utilizator> {

    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

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
        return password;
    }

    public UtilizatorDBRepository(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    /**
     * gaseste un user in baza de date dupa id
     * @param aLong -the id of the entity to be returned
     *           id must not be null
     * @return
     */
    @Override
    public Utilizator findOne(Long aLong) {
        String sqlCommand = "Select * from users";

        try (Connection connection = DriverManager.getConnection(url, username, password); // connectionu incearca sa se conecteze la baza de date
             PreparedStatement ps = connection.prepareStatement(sqlCommand);
             ResultSet resultSet = ps.executeQuery()){

            while (resultSet.next()) {

                Long id = resultSet.getLong("id");

                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Array lista_iduri =  resultSet.getArray("friends_list");

                Utilizator utilizator = new Utilizator(id,firstName, lastName, lista_iduri);
                if (id == aLong) return utilizator;


            }
            return new Utilizator(0L,"","", null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gaseste toti userii din baza de date
     * @return
     */
    @Override
    public Iterable<Utilizator> findAll() {

        Set<Utilizator> users = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");

                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                Array lista_iduri = resultSet.getArray("friends_list");
                Utilizator utilizator = new Utilizator(id,firstName, lastName, lista_iduri);
                //utilizator.setId(id);
                users.add(utilizator);

            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Salveaza un user in baza de date
     * @param entity
     *         entity must be not null
     * @return
     */
    @Override
    public Utilizator save(Utilizator entity) {

        String sql = "insert into users (id, first_name, last_name ) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password); // connectionu incearca sa se conecteze la baza de date

             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, entity.getId2());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sterge user din baza de date
     * @param aLong
     *      id must be not null
     * @return
     */
    @Override
    public Utilizator delete(Long aLong) {
        String sqlCommand = "DELETE FROM users WHERE id=(?)";
        try (Connection connection = DriverManager.getConnection(url, username, password); // connectionu incearca sa se conecteze la baza de date
             PreparedStatement ps = connection.prepareStatement(sqlCommand)){

            ps.setLong(1, aLong);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * nimic momentan, nu s a folosit
     * @param entity
     *          entity must not be null
     * @return
     */
    @Override
    public Utilizator update(Utilizator entity) {
        return null;
    }

    /**
     * functie care returneaza prietenii pentru un user
     * @param userId
     * @return
     */
    @Override
    public Set<Utilizator> showFriendsForUser(Long userId){
        Set<Utilizator> frList = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select id_user1,id_user2 from friendships f\n" +
                     "inner join users u\n" +
                     "on u.id = f.id_user1");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id_user1");
                Long id2 = resultSet.getLong("id_user2");
                if(userId.equals(id1)){
                    Utilizator friend = findOne(id2);
                    frList.add(friend);
                }
                else if(userId.equals(id2)){
                    Utilizator f = findOne(id1);
                    frList.add(f);
                }
            }
            return frList;
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return frList;
    }

    /**
     * Functie care adauga un prieten pentru un user in baza de date
     * @param u1
     * @param idu2
     */
    @Override
    public void addFriendToSQL(Utilizator u1, Long idu2){
        String sql2 = "update users u set friends_list = array_append(friends_list, ?)\n" +
                "where u.id = (?)";

        try (Connection connection = DriverManager.getConnection(url, username, password); // connectionu incearca sa se conecteze la baza de date

             PreparedStatement ps = connection.prepareStatement(sql2)){

            ps.setLong(1, idu2);
            ps.setLong(2, u1.getId2());



            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUsersAfterDelete(Utilizator u){
        String sql2 = "update users set friends_list = array_remove(friends_list, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password); // connectionu incearca sa se conecteze la baza de date

             PreparedStatement ps = connection.prepareStatement(sql2)){

            ps.setLong(1, u.getId2());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
