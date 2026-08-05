package modelos;

import java.sql.Date;


public class solicitantesModelo {
    
    private int id;
    private int id_hos;
    private String nomHospital;
    private String nombre;
    private String apellido;
    private String sexo;
    private String direccion;
    private String colonia;
    private String tipoSangre;
    private String telefono;
    private String fechaRegistro;

    public solicitantesModelo(){
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

     public int getIdHos(){
        return id_hos;
    }
    
    public void setIdHos( int id_hos ){
        this.id_hos = id_hos;
    }
    
     public String getNomHospital(){
        return nomHospital;
    }
    
    public void setNomHospital( String nomHospital ){
        this.nomHospital = nomHospital;
    } 
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
}