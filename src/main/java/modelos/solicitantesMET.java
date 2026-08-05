
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
public class solicitantesMET {
    
    conexionDB.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.conectando();

public List listar( String valorBusqueda, int desde, int porPagina){
            List<solicitantesModelo> Lista = new ArrayList();
            String sql = "SELECT S.id, H.nombre AS nombre_hospital, S.nombre, S.apellido, S.sexo, S.direccion, S.colonia, S.tipoSangre, S.telefono, S.fechaRegistro "
            + "FROM Solicitantes S "
            + "INNER JOIN Hospitales H ON S.id_hospital = H.id "
            + "ORDER BY S.id "
            + "OFFSET " + desde + " ROWS "
            + "FETCH NEXT " + porPagina + " ROWS ONLY";

            String busqueda = "SELECT S.id, H.nombre AS nombre_hospital, S.nombre, S.apellido, S.sexo, S.direccion, S.colonia, S.tipoSangre, S.telefono, S.fechaRegistro "
            + "FROM Solicitantes S "
            + "INNER JOIN Hospitales H ON S.id_hospital = H.id "
            + "WHERE S.nombre LIKE '%" + valorBusqueda + "%' OR S.apellido LIKE '%" + valorBusqueda + "%' "
            + "ORDER BY S.id OFFSET " + desde + " ROWS FETCH NEXT " + porPagina + " ROWS ONLY";
            
            try {
                if(valorBusqueda.equalsIgnoreCase("")) {
                    ps = con.prepareStatement(sql);
                } else {
                    ps = con.prepareStatement(busqueda);
                }
                rs = ps.executeQuery();
                while(rs.next()) {
                    solicitantesModelo solici = new solicitantesModelo();
                    solici.setId(rs.getInt("id"));
                    solici.setNomHospital(rs.getString("nombre_hospital"));
                    solici.setNombre(rs.getString("nombre"));
                    solici.setApellido(rs.getString("apellido"));
                    solici.setSexo(rs.getString("sexo"));
                    solici.setDireccion(rs.getString("direccion"));
                    solici.setColonia(rs.getString("colonia"));
                    solici.setTipoSangre(rs.getString("tipoSangre"));
                    solici.setTelefono(rs.getString("telefono"));
                    solici.setFechaRegistro(rs.getString("fechaRegistro"));       
                    Lista.add(solici);
                }
            } catch ( SQLException e){
                System.out.println(e.toString());
            }
            return Lista;
        }//Fin del metodo listar
    
        public double total(String valorBusqueda){
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM Solicitantes";
        String busqueda = "SELECT * FROM Solicitantes WHERE nombre LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%'"; 
        
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
        
     public String modificar(solicitantesModelo cl) {
        String consulta = "SELECT * FROM Solicitantes WHERE nombre = ? AND id != ?";
        String sql = "UPDATE Solicitantes SET id_hospital=?, nombre=?, apellido=?, sexo=?, direccion=?, colonia=?, tipoSangre=?, telefono=?, fechaRegistro=? WHERE id=?";
        try{
            ps = con.prepareStatement(consulta);
            ps.setString(1, cl.getNombre());
            ps.setInt(2, cl.getId());
            rs = ps.executeQuery();
            if(rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setInt(1, cl.getIdHos());
                ps.setString(2, cl.getNombre());
                ps.setString(3, cl.getApellido());
                ps.setString(4, cl.getSexo());
                ps.setString(5, cl.getDireccion());
                ps.setString(6, cl.getColonia());
                ps.setString(7, cl.getTipoSangre());
                ps.setString(8, cl.getTelefono());
                ps.setString(9, cl.getFechaRegistro());
                ps.setInt(10, cl.getId());
                ps.execute();
                return "modificado";
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
            return "error";
        }
    }

   public String registrar(solicitantesModelo cl) {
    String consulta = "SELECT * FROM Solicitantes WHERE nombre = ?";
    String sql = "INSERT INTO Solicitantes (id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono, fechaRegistro) VALUES (?,?,?,?,?,?,?,?,?)";
    try {
        ps = con.prepareStatement(consulta);
        ps.setString(1, cl.getNombre());
        rs = ps.executeQuery();
        if (rs.next()) {
            return "existe";
        } else {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cl.getIdHos());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getApellido());
            ps.setString(4, cl.getSexo());
            ps.setString(5, cl.getDireccion());
            ps.setString(6, cl.getColonia());
            ps.setString(7, cl.getTipoSangre());
            ps.setString(8, cl.getTelefono());
            ps.setString(9, cl.getFechaRegistro());
            ps.execute();
            return "registrado";
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
        return "error";
    }
}
   
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Solicitantes WHERE id = ?";
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
    
    public String obtenerIdHospitalDeSoli(String idSoli) {
    String idSolicitante = null;
    // Realizar la consulta a la base de datos para obtener el ID del hospital
    String consulta = "SELECT id_hospital FROM Solicitantes WHERE id = ?";
    try {
        ps = con.prepareStatement(consulta);
        ps.setString(1, idSoli);
        rs = ps.executeQuery();
        if (rs.next()) {
            idSolicitante = rs.getString("id_hospital");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
    }
    return idSolicitante;
    }

}