package team5.risc.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSet;

public class Database {
    private Connection c;

    public Database(){
        c = null;
    }

    public void connectDB(){
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/risc?sslmode=disable","server", "1234");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            return;
            //System.exit(0);
        }
        System.out.println("Opened database successfully");
        deleteDB();
        initDB();
        insertDB();
    }

    public ResultSet SelectStatement(String query){
        try{
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            return rs;
        }
        catch (Exception e) {
            //System.err.println(e);
            return null;
        }
    }

    public String executeStatement(String query, String err){
        String out = "Success";
        try{
            Statement s = c.createStatement();
            String sql = query;
            s.executeUpdate(sql);
            s.close();
        }
        catch ( Exception e ) {
            out = err;
        }
        return out;
    } 

    public void initDB(){
        String query1 = "CREATE TABLE MAP(AREA_ID INT PRIMARY KEY NOT NULL, OWNER_ID INT NOT NULL DEFAULT -1, lVL0 INT NOT NULL DEFAULT 0, LVL1 INT NOT NULL DEFAULT 0, LVL2 INT NOT NULL DEFAULT 0, LVL3 INT NOT NULL DEFAULT 0, LVL4 INT NOT NULL DEFAULT 0, LVL5 INT NOT NULL DEFAULT 0);";
        executeStatement(query1, "failure");

        //-1 Truck id means package has been delivered
        //-1 user id means no owner
        String query2 = "CREATE TABLE REGION(REGION_ID INT PRIMARY KEY NOT NULL, AREA0 BOOLEAN NOT NULL DEFAULT FALSE, AREA1 BOOLEAN NOT NULL DEFAULT FALSE, AREA2 BOOLEAN NOT NULL DEFAULT FALSE, AREA3 BOOLEAN NOT NULL DEFAULT FALSE, AREA4 BOOLEAN NOT NULL DEFAULT FALSE, AREA5 BOOLEAN NOT NULL DEFAULT FALSE);";// status can be pickup, loading, delivering, delivered
        executeStatement(query2, "failure");

        String query3 = "CREATE TABLE USER(USER_ID INT PRIMARY KEY NOT NULL, USERNAME VARCHAR(100) NOT NULL DEFAULT \'\', PASSWORD VARCHAR(50) NOT NULL DEFAULT \'\');"; 
        executeStatement(query3, "failure");

        String query4 = "CREATE TABLE PHASE(PHASE_NO INT DEFAULT 0);";
        executeStatement(query4, "failure");
    }

    public void insertDB(){
        for(int i = 0; i < 6; i++){
            String query = "INSERT INTO MAP(AREA_ID) VALUES(" + i + ");";
            executeStatement(query, "failure");
        }

        for(int i = 0; i < 2; i++){
            String query = "INSERT INTO REGION(REGION_ID) VALUES(" + i + ");";
            String query1 = "INSERT INTO USER(USER_ID) VALUES(" + i + ");";
            executeStatement(query, "failure");
            executeStatement(query1, "failure");
        }

        /* TEST CODE
        String q = "SELECT * FROM MAP;";
        ResultSet r = SelectStatement(q);
        try{
            while(r.next()){
                System.out.println(r.getInt("AREA_ID"));
            }
        }
        catch(Exception e){}*/
    }

    public void deleteDB(){
        String query1 = "DROP TABLE IF EXISTS MAP;";
        executeStatement(query1, "failure");
        String query2 = "DROP TABLE IF EXISTS REGION;";
        executeStatement(query2, "failure");
        String query3 = "DROP TABLE IF EXISTS USER;";
        executeStatement(query3, "failure");
        String query4 = "DROP TABLE IF EXISTS PHASE;";
        executeStatement(query4, "failure");
    }  


}
