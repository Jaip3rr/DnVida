package modelos;

import conexionDB.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jadri
 */
public class donadoresMET {
    
    conexionDB.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.conectando();

    public double total( String valorBusqueda) {
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM Donadores";
        String busqueda = "SELECT COUNT(*) AS total FROM Donadores WHERE tipoSangre LIKE '%" + valorBusqueda + "%'"; 
        
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

    public List listar(String valorBusqueda, int desde, int porPagina) {
            List<donadoresModelo> Lista = new ArrayList();
            String sql = "SELECT * FROM Donadores ORDER BY id OFFSET (" + desde + ") ROWS FETCH NEXT " + porPagina + "ROWS ONLY";
            String busqueda = "SELECT * FROM Donadores WHERE tipoSangre LIKE '%" + valorBusqueda + "%' ORDER BY id OFFSET (" + desde + ") ROWS FETCH NEXT " + porPagina + "ROWS ONLY";
            
            try {
                if(valorBusqueda.equalsIgnoreCase("")) {
                    ps = con.prepareStatement(sql);
                } else {
                    ps = con.prepareStatement(busqueda);
                }
                rs = ps.executeQuery();
                while(rs.next()) {
                    donadoresModelo donad = new donadoresModelo();
                    donad.setId(rs.getInt("id"));
                    donad.setNombre(rs.getString("nombre"));
                    donad.setApellido(rs.getString("apellido"));
                    donad.setCorreo(rs.getString("correo"));
                    donad.setSexo(rs.getString("sexo"));
                    donad.setColonia(rs.getString("colonia"));
                    donad.setTelefono(rs.getString("telefono"));
                    donad.setModiCorpo(rs.getString("modiCorpo"));
                    donad.setTipoSangre(rs.getString("tipoSangre"));
                    donad.setUltimaDon(rs.getString("ultimaDon"));
                    donad.setFechaRegistro(rs.getString("fechaRegistro"));
                    Lista.add(donad);
                }
            } catch ( SQLException e){
                System.out.println(e.toString());
            }
            return Lista; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   public String modificar(donadoresModelo cl) {
    String consulta = "SELECT * FROM Donadores WHERE nombre = ? AND id != ?";
    String sql = "UPDATE Donadores SET nombre=?, apellido=?, correo=?, sexo=?, colonia=?, telefono=?, modiCorpo=?, tipoSangre=?, ultimaDon=?, fechaRegistro=? WHERE id=?";
    try {
        ps = con.prepareStatement(consulta);
        ps.setString(1, cl.getNombre());
        ps.setInt(2, cl.getId());
        rs = ps.executeQuery();
        if (rs.next()) {
            return "existe";
        } else {
            ps = con.prepareStatement(sql);
            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getApellido());
            ps.setString(3, cl.getCorreo());
            ps.setString(4, cl.getSexo());
            ps.setString(5, cl.getColonia());
            ps.setString(6, cl.getTelefono());
            ps.setString(7, cl.getModiCorpo());
            ps.setString(8, cl.getTipoSangre());
            ps.setString(9, cl.getUltimaDon());
            ps.setString(10, cl.getFechaRegistro());
            ps.setInt(11, cl.getId());
            ps.execute();
            return "modificado";
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.toString());
        return "error";
    }
}

   public String registrar(donadoresModelo cl) {
    String consulta = "SELECT * FROM Donadores WHERE nombre = ?";
    
    String sql = "INSERT INTO Donadores (nombre, apellido, correo, sexo, colonia, telefono, modiCorpo, tipoSangre, ultimaDon, fechaRegistro) VALUES (?,?,?,?,?,?,?,?,?,?)";
    try {
        ps = con.prepareStatement(consulta);
        ps.setString(1, cl.getNombre());
        rs = ps.executeQuery();
        if (rs.next()) {
            return "existe";
        } else {
            ps = con.prepareStatement(sql);
            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getApellido());
            ps.setString(3, cl.getCorreo());
            ps.setString(4, cl.getSexo());
            ps.setString(5, cl.getColonia());
            ps.setString(6, cl.getTelefono());
            ps.setString(7, cl.getModiCorpo());
            ps.setString(8, cl.getTipoSangre());
            ps.setString(9, cl.getUltimaDon());
            ps.setString(10, cl.getFechaRegistro());
            ps.execute();
            return "registrado";
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
        return "error";
    }
}
   
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Donadores WHERE id = ?";
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