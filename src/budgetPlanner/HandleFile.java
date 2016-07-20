/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgetPlanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author agurjar
 */
public class HandleFile {
     Charset charset = Charset.forName("US-ASCII");
    Path path = Paths.get("C:\\Users\\Agurjar\\Documents\\NetBeansProjects\\BudgetPlanner\\budgetData.json");
        
   
    public void writeData(String data){
        
        try(FileWriter wr = new FileWriter(path.toFile(), true);
            BufferedWriter br = new BufferedWriter(wr)){
            br.write(data);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public JSONArray getData()throws ParseException{
    
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try(BufferedReader br = Files.newBufferedReader(path, charset)){
          jsonArray = (JSONArray)parser.parse(br);
        }catch(IOException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
}
