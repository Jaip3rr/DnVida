/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class citasMET {
    
    conexionDB.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.conectando();
    
    public List listar( String valorBusqueda, int desde, int porPagina){
            List<citasModelo> Lista = new ArrayList();
            
            String sql = "SELECT C.id, H.nombre AS nombre_hospital, S.nombre AS nombre_solicitante, S.apellido AS apellido_solicitante, D.nombre AS nombre_donante, D.apellido AS apellido_donante, D.correo AS correo_donador, C.fechaCita, C.statusCita "
            + "FROM Citas C "
            + "INNER JOIN Hospitales H ON C.id_hospital = H.id "
            + "INNER JOIN Solicitantes S ON C.id_solicitante = S.id "
            + "INNER JOIN Donadores D ON C.id_donante = D.id "
            + "ORDER BY C.id "
            + "OFFSET " + desde + " ROWS "
            + "FETCH NEXT " + porPagina + " ROWS ONLY";

            String busqueda = "SELECT C.id, H.nombre AS nombre_hospital, S.nombre AS nombre_solicitante, S.apellido AS apellido_solicitante, D.nombre AS nombre_donante, D.apellido AS apellido_donante, D.correo AS correo_donador, C.fechaCita, C.statusCita "
            + "FROM Citas C "
            + "INNER JOIN Hospitales H ON C.id_hospital = H.id "
            + "INNER JOIN Solicitantes S ON C.id_solicitante = S.id "
            + "INNER JOIN Donadores D ON C.id_donante = D.id "
            + "WHERE H.nombre LIKE '%" + valorBusqueda + "%' OR S.nombre LIKE '%" + valorBusqueda + "%' OR D.nombre LIKE '%" + valorBusqueda + "%' OR S.apellido LIKE '%" + valorBusqueda + "%' OR D.apellido LIKE '%" + valorBusqueda + "%' "
            + "ORDER BY C.id OFFSET " + desde + " ROWS FETCH NEXT " + porPagina + " ROWS ONLY";
            
            try {
                if(valorBusqueda.equalsIgnoreCase("")) {
                    ps = con.prepareStatement(sql);
                } else {
                    ps = con.prepareStatement(busqueda);
                }
                rs = ps.executeQuery();
                while(rs.next()) {
                    citasModelo cita = new citasModelo();
                    cita.setId(rs.getInt("id"));
                    cita.setNomHospital(rs.getString("nombre_hospital"));
                    cita.setNomSolicitante(rs.getString("nombre_solicitante"));
                    cita.setApellSoli(rs.getString("apellido_solicitante"));
                    cita.setNomDonante(rs.getString("nombre_donante"));
                    cita.setApellDon(rs.getString("apellido_donante"));
                    cita.setCorreoDon(rs.getString("correo_donador"));
                    cita.setFechaCita(rs.getString("fechaCita"));
                    cita.setStatusCita(rs.getString("statusCita"));

                    Lista.add(cita);
                }
                
            } catch ( SQLException e){
                System.out.println("Aqui mi pana "+e.toString());
            }
            return Lista;
        }//Fin del metodo listar
    
    public double total(String valorBusqueda){
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM Citas";
        String busqueda = "SELECT COUNT(*) AS total " +
                  "FROM Citas C " +
                  "INNER JOIN Hospitales H ON C.id_hospital = H.id " +
                  "INNER JOIN Solicitantes S ON C.id_solicitante = S.id " +
                  "INNER JOIN Donadores D ON C.id_donante = D.id " +
                  "WHERE H.nombre LIKE '%" + valorBusqueda + "%' OR S.nombre LIKE '%" + valorBusqueda + "%' OR D.nombre LIKE '%" +valorBusqueda + "%' OR S.apellido LIKE '%" + valorBusqueda + "%' OR D.apellido LIKE '%" + valorBusqueda + "%' ";
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
    
    public String modificar(citasModelo cl) {
        String consulta = "SELECT * FROM Citas WHERE id_hospital = ? AND id_solicitante = ? AND id_donante = ? AND id != ?";
    String sql = "UPDATE Citas SET id_hospital=?, id_solicitante=?, id_donante=?, fechaCita=?, statusCita=? WHERE id=?";
    try {
        ps = con.prepareStatement(consulta);
        ps.setInt(1, cl.getIdHospital());
        ps.setInt(2, cl.getIdSolicitante());
        ps.setInt(3, cl.getIdDonante());
        ps.setInt(4, cl.getId());
        rs = ps.executeQuery();
        if (rs.next()) {
            return "existe";
        } else {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cl.getIdHospital());
            ps.setInt(2, cl.getIdSolicitante());
            ps.setInt(3, cl.getIdDonante());
            ps.setString(4, cl.getFechaCita());
            ps.setString(5, cl.getStatusCita());
            ps.setInt(6, cl.getId());
            ps.execute();
            return "modificado";
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.toString());
        return "error";
    }
    }
    
    public String registrar(citasModelo cl) {
    String consulta = "SELECT * FROM Citas WHERE id_hospital = ? AND id_solicitante = ? AND id_donante = ?";
    String sql = "INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita) VALUES (?,?,?,?,?)";
    try {
        ps = con.prepareStatement(consulta);
        ps.setInt(1, cl.getIdHospital());
        ps.setInt(2, cl.getIdSolicitante());
        ps.setInt(3, cl.getIdDonante());
        rs = ps.executeQuery();
        if (rs.next()) {
            return "existe";
        } else {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cl.getIdHospital());
            ps.setInt(2, cl.getIdSolicitante());
            ps.setInt(3, cl.getIdDonante());
            ps.setString(4, cl.getFechaCita());
            ps.setString(5, cl.getStatusCita());
            ps.execute();
            return "registrado";
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
        return "error";
    }
    }
    
     public boolean eliminar(int id) {
        String sql = "DELETE FROM Citas WHERE id = ?";
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
     
     public String obtenerIdHospitalDeCita(String idCita) {
    String idHospital = null;
    // Realizar la consulta a la base de datos para obtener el ID del hospital
    String consulta = "SELECT id_hospital FROM Citas WHERE id = ?";
    try {
        ps = con.prepareStatement(consulta);
        ps.setString(1, idCita);
        rs = ps.executeQuery();
        if (rs.next()) {
            idHospital = rs.getString("id_hospital");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
    }
    return idHospital;
}

    public String obtenerIdSolicitanteDeCita(String idCita) {
    String idSolicitante = null;
    // Realizar la consulta a la base de datos para obtener el ID del hospital
    String consulta = "SELECT id_solicitante FROM Citas WHERE id = ?";
    try {
        ps = con.prepareStatement(consulta);
        ps.setString(1, idCita);
        rs = ps.executeQuery();
        if (rs.next()) {
            idSolicitante = rs.getString("id_solicitante");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
    }
    return idSolicitante;
    }

    public String obtenerIdDonadorDeCita(String idCita) {
        String idDonante = null;
    // Realizar la consulta a la base de datos para obtener el ID del hospital
    String consulta = "SELECT id_donante FROM Citas WHERE id = ?";
    try {
        ps = con.prepareStatement(consulta);
        ps.setString(1, idCita);
        rs = ps.executeQuery();
        if (rs.next()) {
            idDonante = rs.getString("id_donante");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
    }
    return idDonante;
    }
    
}