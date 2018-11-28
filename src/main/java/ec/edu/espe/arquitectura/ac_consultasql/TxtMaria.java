/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ac_consultasql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class TxtMaria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        long start = System.currentTimeMillis();
        String directoryName = System.getProperty("user.home");
        String filename = "data.txt";
        String linea = null;
        String[] persona;
        String sql = "INSERT INTO persona VALUES(?,?,?,?,?,?,?)";
        String pattern = "y-m-d";
        java.util.Date fecha_nacimiento;
        try {
            SimpleDateFormat simpleDateFormat;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/registro_civil", "root", null);
            con.setAutoCommit(false);
            File directory = new File(directoryName);
            File file = new File(directory, filename);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            PreparedStatement pst = con.prepareStatement(sql);   
            simpleDateFormat = new SimpleDateFormat(pattern);
            int cont = 1;
            while ((linea = br.readLine()) != null) {
                persona = linea.split(",");
                pst.setString(1, persona[0]);
                pst.setString(2, persona[1]);
                pst.setString(3, persona[2]);
                fecha_nacimiento = simpleDateFormat.parse(persona[3]);
                pst.setDate(4, new java.sql.Date(fecha_nacimiento.getTime()));
                pst.setInt(5, Integer.parseInt(persona[4]));
                pst.setString(6, persona[5]);
                pst.setString(7, persona[6]);
                pst.execute();
                if (cont++ % 1000 == 0) {
                    con.commit();
                }
            }
            con.commit();
            pst.close();
            con.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TxtMaria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TxtMaria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TxtMaria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TxtMaria.class.getName()).log(Level.SEVERE, null, ex);
        }
        long end = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (end - start) + " milisegundos.");
    }
}