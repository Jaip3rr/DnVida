package modelos;


public class usuariosModelo {
    
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String direccion;
    private String tipo;
    private String clave;
    
    public usuariosModelo(){
    }
    
    public usuariosModelo(int id, String nombre, String apellido, String correo, String direccion, String tipo, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.direccion = direccion;
        this.tipo = tipo;
        this.clave = clave;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId( int id ){
        this.id = id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre( String nombre ){
        this.nombre = nombre;
    }
    
    public String getApellido(){
        return apellido;
    }
    
    public void setApellido( String apellido ){
        this.apellido = apellido;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    public void setCorreo( String correo ){
        this.correo = correo;
    }
    
    public String getDireccion(){
        return direccion;
    }
    
    public void setDireecion( String direccion ){
        this.direccion = direccion;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public void setTipo( String tipo){
        this.tipo = tipo;
    }
    
    public String getClave(){
        return clave;
    }
    
    public void setClave( String clave ){
        this.clave = clave;
    }
}