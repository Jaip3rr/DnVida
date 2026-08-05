package modelos;

import conexionDB.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class usuariosMET {
    conexionDB.Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.conectando();
    
    
        public List listar( String valorBusqueda, int desde, int porPagina){
            List<usuariosModelo> Lista = new ArrayList();
            String sql = "SELECT * FROM Usuarios ORDER BY id OFFSET (" + desde + ") ROWS FETCH NEXT " + porPagina + "ROWS ONLY";
            String busqueda = "SELECT * FROM Usuarios WHERE nombre LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%' ORDER BY id OFFSET (" + desde + ") ROWS FETCH NEXT " + porPagina + "ROWS ONLY";
            
            try {
                if(valorBusqueda.equalsIgnoreCase("")) {
                    ps = con.prepareStatement(sql);
                } else {
                    ps = con.prepareStatement(busqueda);
                }
                rs = ps.executeQuery();
                while(rs.next()) {
                    usuariosModelo usua = new usuariosModelo();
                    usua.setId(rs.getInt("id"));
                    usua.setNombre(rs.getString("nombre"));
                    usua.setApellido(rs.getString("apellido"));
                    usua.setCorreo(rs.getString("correo"));
                    usua.setDireecion(rs.getString("direccion"));
                    usua.setTipo(rs.getString("tipo_usuario"));
                    Lista.add(usua);
                }
            } catch ( SQLException e){
                System.out.println(e.toString());
            }
            return Lista;
        }//Fin del metodo listar
    
        public double total(String valorBusqueda){
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM Usuarios";
        String busqueda = "SELECT COUNT(*) AS total FROM Usuarios WHERE nombre LIKE '%" + valorBusqueda + "%' OR apellido LIKE '%" + valorBusqueda + "%'"; 
        
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
        
    
    public usuariosModelo validar( String correo, String pass ){
        
         usuariosModelo usua = new usuariosModelo();
         String sql = "SELECT * FROM Usuarios WHERE correo = ? AND contraseña = ?";
         
        try {
            con = cn.conectando();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()){
                usua.setId(rs.getInt("id"));
                usua.setNombre(rs.getString("nombre"));
                usua.setCorreo(rs.getString("correo"));
                usua.setClave(rs.getString("contraseña"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return usua;
    }

    public String modificar(usuariosModelo cl) {
        String consulta = "SELECT * FROM Usuarios WHERE nombre = ? AND id != ?";
        String sql = "UPDATE Usuarios SET nombre=?, apellido=?, correo=?, direccion=?, tipo_usuario=?, contraseña=? WHERE id=?";
        try{
            ps = con.prepareStatement(consulta);
            ps.setString(1, cl.getNombre());
            ps.setInt(2, cl.getId());
            rs = ps.executeQuery();
            if(rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, cl.getNombre());
                ps.setString(2, cl.getApellido());
                ps.setString(3, cl.getCorreo());
                ps.setString(4, cl.getDireccion());
                ps.setString(5, cl.getTipo());
                ps.setString(6, cl.getClave());
                ps.setInt(7, cl.getId());
                ps.execute();
                return "modificado";
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
            return "error";
        }
    }

    public String registrar(usuariosModelo c1) {
        String consulta = "SELECT * FROM Usuarios WHERE nombre = ?";
        String sql = "INSERT INTO Usuarios (nombre, apellido, correo, direccion, tipo_usuario, contraseña) VALUES (?,?,?,?,?,?)";
        try{
            ps = con.prepareStatement(consulta);
            ps.setString(1, c1.getCorreo());
            rs = ps.executeQuery();
            if(rs.next()){
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, c1.getNombre());
                ps.setString(2, c1.getApellido());
                ps.setString(3, c1.getCorreo());
                ps.setString(4, c1.getDireccion());
                ps.setString(5, c1.getTipo());
                ps.setString(6, c1.getClave());
                ps.execute();
                return "registrado";
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
            return "error";
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";
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