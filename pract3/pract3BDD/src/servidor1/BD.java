/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.println;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.*;
import java.io.*;

/**
 *
 * @author hsmha
 */
public class BD {
    private static Connection con = null;
   
    public Connection conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddbase","root", "Ariadna.98");
        }catch(ClassNotFoundException | SQLException ex){
          
        }
        return con;
    }
    public boolean buscarEnBase(Connection conexion, String datos, String tabla){
        boolean existe = false;
        Statement st;
        try{
            st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT " + datos + " FROM "+ tabla);
            if (rs != null) {
                while (rs.next() == true) {
                    //if(numeroEmpleado.equals(rs.getString("Clave")) == true){
                        existe = true;
                        break;
                    //}
                }
                rs.close();
            }
            st.close();
            cerrarConexion(con);
        }catch(SQLException e){
            
        }
        return existe;
    }
    public void cerrarConexion(Connection con){
        try{
            con.close();
        }catch(SQLException e){
        }
    }
    
    
    
    /*
         boolean existe = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddbase","root", "Ariadna.98");
        }catch(ClassNotFoundException | SQLException ex){
          println("error al ingresar a bd");
        }
        Statement st;
        try{
            
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from datos");
            if (rs != null) {
                while (rs.next() == true) {
                    //if(numeroEmpleado.equals(rs.getString("Clave")) == true){
                    println(rs.getString("sucursal"));
                        existe = true;
                        break;
                    //}
                }
                rs.close();
            }
            st.close();
            
        }catch(SQLException e){
          println("error al consultar bd");
        }
        try{
            con.close();
        }catch(SQLException e){
                      println("error al cerrar a bd");
        }
        
     */
     
     
     
     public static void main (String[] argumentos)throws IOException, ClassNotFoundException{ 
	boolean existe = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddbase","root", "Ariadna.98");
             println("ok al ingresar a bd");
        }catch(SQLException e){
          println("error al ingresar a bd");
        }
     /*   Statement st;
        try{
            
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from datos");
            if (rs != null) {
                while (rs.next() == true) {
                    //if(numeroEmpleado.equals(rs.getString("Clave")) == true){
                    println(rs.getString("sucursal"));
                        existe = true;
                        break;
                    //}
                }
                rs.close();
            }
            st.close();
            
        }catch(SQLException e){
          println("error al consultar bd");
        }*/
        try{
            con.close();
        }catch(SQLException e){
                      println("error al cerrar a bd");
        }
     
     
	}
    
}



