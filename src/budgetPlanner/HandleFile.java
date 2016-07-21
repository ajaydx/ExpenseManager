/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetPlanner;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONArray;

import org.json.simple.parser.ParseException;

/**
 *
 * @author agurjar
 */
public class HandleFile {
//    Charset charset = Charset.forName("US-ASCII");
//    Path path = Paths.get("C:\\Users\\Agurjar\\Documents\\NetBeansProjects\\BudgetPlanner\\budgetData.json");
    Connection con;  

    public HandleFile(){
        try{
           Class.forName("org.sqlite.JDBC");
        this.con = DriverManager.getConnection("jdbc:sqlite:data.db");
        Statement s = con.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS data(date TEXT PRIMARY KEY,Travel DOUBLE,Food VARCHAR,Electricity DOUBLE,Housing DOUBLE,Entertainment DOUBLE)";
        s.execute(sql);
        System.out.println("Table created"); 
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
   
    public void writeData(String category,Double amount){
        
//        try(FileWriter wr = new FileWriter(path.toFile(), true);
//            BufferedWriter br = new BufferedWriter(wr)){
//            br.write(data);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
          try{
          
          PreparedStatement ps = con.prepareStatement("UPDATE data SET amount = amount + ? " + " WHERE category = ?");
          ps.setDouble(1, amount);
          ps.setString(2, category);
          ps.executeUpdate();
          }catch(Exception e){
          e.printStackTrace();
          }
          
    }
    
    public JSONArray getData()throws ParseException{
    
//        JSONParser parser = new JSONParser();
//        JSONArray jsonArray = new JSONArray();
//        try(BufferedReader br = Files.newBufferedReader(path, charset)){
//          jsonArray = (JSONArray)parser.parse(br);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
        return new JSONArray();
    }
}
