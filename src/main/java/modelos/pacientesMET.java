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
public class pacientesMET {
    
    conexionDB.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.conectando();

public List listar( String valorBusqueda, int desde, int porPagina){
            List<pacientesModelo> Lista = new ArrayList();
            String sql = "SELECT P.id, H.nombre AS nombre_hospital, P.nombre, P.apellido, P.sexo, P.direccion, P.colonia, P.tipoSangre, P.telefono, P.fechaRegistro "
            + "FROM Pacientes P "
            + "INNER JOIN Hospitales H ON P.id_hospital = H.id "
            + "ORDER BY P.id "
            + "OFFSET " + desde + " ROWS "
            + "FETCH NEXT " + porPagina + " ROWS ONLY";

            String busqueda = "SELECT P.id, H.nombre AS nombre_hospital, P.nombre, P.apellido, P.sexo, P.direccion, P.colonia, P.tipoSangre, P.telefono, P.fechaRegistro "
            + "FROM Pacientes P "
            + "INNER JOIN Hospitales H ON P.id_hospital = H.id "
            + "WHERE P.nombre LIKE '%" + valorBusqueda + "%' OR P.apellido LIKE '%" + valorBusqueda + "%' "
            + "ORDER BY P.id OFFSET " + desde + " ROWS FETCH NEXT " + porPagina + " ROWS ONLY";
            try {
                if(valorBusqueda.equalsIgnoreCase("")) {
                    ps = con.prepareStatement(sql);
                } else {
                    ps = con.prepareStatement(busqueda);
                }
                rs = ps.executeQuery();
                while(rs.next()) {
                    pacientesModelo pacie = new pacientesModelo();
                    pacie.setId(rs.getInt("id"));
                    pacie.setNomHospital(rs.getString("nombre_hospital"));
                    pacie.setNombre(rs.getString("nombre"));
                    pacie.setApellido(rs.getString("apellido"));
                    pacie.setSexo(rs.getString("sexo"));
                    pacie.setDireccion(rs.getString("direccion"));
                    pacie.setColonia(rs.getString("colonia"));
                    pacie.setTipoSangre(rs.getString("tipoSangre"));
                    pacie.setTelefono(rs.getString("telefono"));
                    pacie.setFechaRegistro(rs.getString("fechaRegistro"));       
                    Lista.add(pacie);
                }
            } catch ( SQLException e){
                System.out.println(e.toString());
            }
            return Lista;
        }//Fin del metodo listar
    
        public double total(String valorBusqueda){
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM Pacientes";
        String busqueda = "SELECT COUNT(*) AS total FROM Pacientes WHERE nombre LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%'"; 
        
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

    public String modificar(pacientesModelo cl) {
        String consulta = "SELECT * FROM Pacientes WHERE nombre = ? AND id != ?";
        String sql = "UPDATE Pacientes SET id_hospital=?, nombre=?, apellido=?, sexo=?, direccion=?, colonia=?, tipoSangre=?, telefono=?, fechaRegistro=? WHERE id=?";
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

    public String registrar(pacientesModelo cl) {
        String consulta = "SELECT * FROM Pacientes WHERE nombre = ?";
        
        String sql = "INSERT INTO Pacientes (id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono, fechaRegistro) VALUES (?,?,?,?,?,?,?,?,?)";
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
        String sql = "DELETE FROM Pacientes WHERE id = ?";
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
     
     public String obtenerIdHospitalDeSoli(String idPac) {
    String idPaciente = null;
    // Realizar la consulta a la base de datos para obtener el ID del hospital
    String consulta = "SELECT id_hospital FROM Pacientes WHERE id = ?";
    try {
        ps = con.prepareStatement(consulta);
        ps.setString(1, idPac);
        rs = ps.executeQuery();
        if (rs.next()) {
            idPaciente = rs.getString("id_hospital");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
    }
    return idPaciente;
    }
}
