package Services;

import Models.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetRepository implements Repository<Pet>{

    private Creator petCreator;
    private Statement sqlST;
    private ResultSet resultSet;
    private String SQLstr;

    public PetRepository(){
        this.petCreator = new PetCreator();
    };

    @Override
    public List<Pet> getAll(){
        List<Pet> farm = new ArrayList<Pet>();
        Pet pet;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){
                sqlST = dbConnection.createStatement();
                SQLstr = "SELECT Kind_Id, Id, Name, Birthday FROM Home_Animals_List ORDER BY Id";
                resultSet = sqlST.executeQuery(SQLstr);
                while (resultSet.next()) {

                    PetTipe tipe = PetTipe.getTipe(resultSet.getInt(1));
                    int id = resultSet.getInt(2);
                    String name = resultSet.getString(3);
                    LocalDate birthday = resultSet.getDate(4).toLocalDate();

                    pet = petCreator.createPet(tipe, name, birthday);
                    pet.setPetId(id);
                    farm.add(pet);

                }
                return farm;
            }
        } catch (ClassNotFoundException | IOException | SQLException ex){
            Logger.getLogger(PetRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public Pet getById(int petId){
        Pet pet = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){

                SQLstr = "SELECT Kind_Id, Id, Name, Birtday FROM Home_Animals_List WHERE Id = ?";
                PreparedStatement preparedStatement = dbConnection.prepareStatement(SQLstr);
                preparedStatement.setInt(1, petId);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()){

                    PetTipe tipe = PetTipe.getTipe(resultSet.getInt(1));
                    int id = resultSet.getInt(2);
                    String name = resultSet.getString(3);
                    LocalDate birthday = resultSet.getDate(4).toLocalDate();

                    pet = petCreator.createPet(tipe, name, birthday);
                    pet.setPetId(id);
                }
                return pet;
            }
        }catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(PetRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public int create(Pet pet){

        int rows;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){

                SQLstr = "INSERT INTO Home_Animals_List (Name, Birthday, Kind_Id) SELECT ?, ?, (SELECT Id FROM Home_Animals_List WHERE Kind_animal = ?)";
                PreparedStatement preparedStatement = dbConnection.prepareStatement(SQLstr);
                preparedStatement.setString(1, pet.getName());
                preparedStatement.setDate(2, Date.valueOf(pet.getBirthday()));
                preparedStatement.setString(3, pet.getClass().getSimpleName());

                rows = preparedStatement.executeUpdate();
                return rows;
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(PetRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void train (int id, String command){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){
                String SQLstr = "INSERT INTO pet_command (PetId, CommandId) SELECT ?, (SELECT Id FROM commands WHERE Command_name = ?)";
                PreparedStatement preparedStatement = dbConnection.prepareStatement(SQLstr);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, command);

                preparedStatement.executeUpdate();
            }
        }catch (ClassNotFoundException | IOException | SQLException ex){
            Logger.getLogger(PetRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<String> getComandsById (int petId, int commands_tipe) {

        List <String> commands = new ArrayList <>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                if (commands_tipe == 1) {
                    SQLstr = "SELECT Command_name FROM pet_command pc JOIN commands c ON pc.CommandId = c.Id WHERE pc.PetId = ?";
                } else {
                    SQLstr = "SELECT Command_name FROM commands c JOIN Tipe_command gc ON c.Id = gc.CommandId WHERE gc.Kind_Id = (SELECT Kind_Id FROM pet_list WHERE Id = ?)";
                }
                PreparedStatement preparedStatement = dbConnection.prepareStatement(SQLstr);
                preparedStatement.setInt(1, petId);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    commands.add(resultSet.getString(1));
                }
                return commands;
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(PetRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public int update (Pet pet){
        int rows;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){
                SQLstr = "UPDATE petomec_list SET Name = ?, Birthday = ? WHERE Id = ?";
                PreparedStatement preparedStatement = dbConnection.prepareStatement(SQLstr);

                preparedStatement.setString(1, pet.getName());
                preparedStatement.setDate(2, Date.valueOf(pet.getBirthdayDate()));
                preparedStatement.setInt(3,pet.getPetId());

                rows = preparedStatement.executeUpdate();
                return rows;
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(PetRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void delete (int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                SQLstr = "DELETE FROM animals WHERE Id = ?";
                PreparedStatement preparedStatement = dbConnection.prepareStatement(SQLstr);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(PetRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException, IOException{

        Properties properties = new Properties();
        try(FileInputStream fil = new FileInputStream("src/main/java/Resources/database.properties")) {

            properties.load(fil);
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            return DriverManager.getConnection(url, username, password);

        }
    }
}
