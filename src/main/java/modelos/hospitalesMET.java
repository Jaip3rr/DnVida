
package modelos;

import conexionDB.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class hospitalesMET {
    
    conexionDB.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.conectando();

    public List listar( String valorBusqueda, int desde, int porPagina){
            List<hospitalesModelo> Lista = new ArrayList();
            String sql = "SELECT * FROM Hospitales ORDER BY id OFFSET (" + desde + ") ROWS FETCH NEXT " + porPagina + "ROWS ONLY";
            String busqueda = "SELECT * FROM Hospitales WHERE nombre LIKE '%" + valorBusqueda + "%' ORDER BY id OFFSET (" + desde + ") ROWS FETCH NEXT " + porPagina + "ROWS ONLY";
            
            try {
                if(valorBusqueda.equalsIgnoreCase("")) {
                    ps = con.prepareStatement(sql);
                } else {
                    ps = con.prepareStatement(busqueda);
                }
                rs = ps.executeQuery();
                while(rs.next()) {
                    hospitalesModelo hosp = new hospitalesModelo();
                    hosp.setId(rs.getInt("id"));
                    hosp.setNombre(rs.getString("nombre"));
                    hosp.setDireccion(rs.getString("direccion"));
                    hosp.setColonia(rs.getString("colonia"));
                    hosp.setCorreo(rs.getString("correo"));
                    hosp.setTelefono(rs.getString("telefono"));
                    Lista.add(hosp);
                }
            } catch ( SQLException e){
                System.out.println(e.toString());
            }
            return Lista;
        }//Fin del metodo listar
    
        public double total(String valorBusqueda){
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM Hospitales";
        String busqueda = "SELECT COUNT(*) AS total FROM Hospitales WHERE nombre LIKE '%" + valorBusqueda + "%'"; 
        
        try {
        if(valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            if(rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException ex) {
            System.out.println("Error");
        }
        
        return total;
    }
        
        public String modificar(hospitalesModelo cl) {
        String consulta = "SELECT * FROM Hospitales WHERE nombre = ? AND id != ?";
        String sql = "UPDATE hospitales SET nombre=?, direccion=?, colonia=?, correo=?, telefono=? WHERE id=?";
        try{
            ps = con.prepareStatement(consulta);
            ps.setString(1, cl.getCorreo());
            ps.setInt(2, cl.getId());
            rs = ps.executeQuery();
            if(rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, cl.getNombre());
                ps.setString(2, cl.getDireccion());
                ps.setString(3, cl.getColonia());
                ps.setString(4, cl.getCorreo());
                ps.setString(5, cl.getTelefono());
                ps.setInt(6, cl.getId());
                ps.execute();
                return "modificado";
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
            return "error";
        }
    }
        
        public String registrar(hospitalesModelo c1) {
        String consulta = "SELECT * FROM Hospitales WHERE nombre = ?";
        String sql = "INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono) VALUES (?,?,?,?,?)";
        try{
            ps = con.prepareStatement(consulta);
            ps.setString(1, c1.getCorreo());
            rs = ps.executeQuery();
            if(rs.next()){
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, c1.getNombre());
                ps.setString(2, c1.getDireccion());
                ps.setString(3, c1.getColonia());
                ps.setString(4, c1.getCorreo());
                ps.setString(5, c1.getTelefono());
                ps.execute();
                return "registrado";
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
            return "error";
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Hospitales WHERE id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
}
