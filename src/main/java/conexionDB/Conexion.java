package conexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Conexion {
    
    
    private Connection conexion;
    private String db = "DonaVida";
    private String usuario = "adminEmpresaurios";
    private String contraseña = "pa$$word1";
    
    public Connection conectando(){
        try {
            String con = "jdbc:sqlserver://localhost:1433;databaseName="+db;
            conexion = DriverManager.getConnection(con, usuario, contraseña);
            return conexion;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No hubo conexion");
        } 
        return null;
    }
    
}
