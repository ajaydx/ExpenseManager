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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;


/**
 *
 * @author agurjar
 */
public class HandleFile {

    Connection con;
    
    public HandleFile() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.con = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement s = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS data1(date TEXT PRIMARY KEY,Travel DOUBLE,Food DOUBLE,Electricity DOUBLE,Housing DOUBLE,Entertainment DOUBLE)";
            s.execute(sql);
            System.out.println("Table created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeData(String category, Double amount) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            
            String sql2 = "SELECT date FROM data1 ORDER BY date DESC LIMIT 1";
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql2);
            
            String sql1;
            PreparedStatement ps1 = null;
            if (rs.next()) {
                System.out.println("In not Null");
                String l_date = rs.getString("date");
                System.out.println(l_date+"  "+sdf.format(new Date()));
                if (l_date.compareTo(sdf.format(new Date())) < 0) {
                    System.out.println("In new row");
                    sql1 = "INSERT INTO data1('date','Travel','Food','Electricity','Housing','Entertainment') Values('" + date + "',0,0,0,0,0)";
                    s.execute(sql1);
                    ps1 = con.prepareStatement("UPDATE data1 SET " + category + " = " + category + " + ? WHERE date = ?");
                ps1.setDouble(1, amount);
                ps1.setString(2, date);
                ps1.executeUpdate();
                } else {
                    System.out.println("In same row");
                    ps1 = con.prepareStatement("UPDATE data1 SET " + category + " = " + category + " + ? WHERE date = ?");
                    ps1.setDouble(1, amount);
                    ps1.setString(2, date);
                    ps1.executeUpdate();
                }
            } else {
                System.out.println("In Null");
                PreparedStatement ps2 = con.prepareStatement("INSERT INTO data1 VALUES(?,?,?,?,?,?)");
                ps2.setString(1, date);
                ps2.setDouble(2, 0);
                ps2.setDouble(3, 0);
                ps2.setDouble(4, 0);
                ps2.setDouble(5, 0);
                ps2.setDouble(6, 0);
                
                ps2.execute();
                
                ps1 = con.prepareStatement("UPDATE data1 SET " + category + " = " + category + " + ? WHERE date = ?");
                ps1.setDouble(1, amount);
                ps1.setString(2, date);
                ps1.executeUpdate();

                ps2.close();
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            
        }

    }

    public void getData(){
        String sql = "SELECT SUM(Travel),SUM(Food),SUM(Housing),SUM(Electricity),SUM(Entertainment) FROM data1";
        
        try {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()){
                int i = 1;
                while(i<=5){
                System.out.println(rs.getString(i));i++;}
            }
        } catch (SQLException ex) {
            Logger.getLogger(HandleFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
