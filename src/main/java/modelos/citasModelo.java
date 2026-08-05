
package modelos;

/**
 *
 * @author Jadri
 */
public class citasModelo {
    
    private int id;
    private int id_hospital;
    private int id_solicitante;
    private int id_donante;
    private String fechaCita;
    private String statusCita;
    private String nomHospital;
    private String nomSolicitante;
    private String apellSolicitante;
    private String nomDonante;
    private String apellDonante;
    private String correoDonador;
    
    
    public citasModelo(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHospital() {
        return id_hospital;
    }

    public void setIdHospital(int id_hospital) {
        this.id_hospital = id_hospital;
    }

    public int getIdSolicitante() {
        return id_solicitante;
    }

    public void setIdSolicitante(int id_paciente) {
        this.id_solicitante = id_paciente;
    }

    public int getIdDonante() {
        return id_donante;
    }

    public void setIdDonante(int id_donante) {
        this.id_donante = id_donante;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getStatusCita() {
        return statusCita;
    }

    public void setStatusCita(String statusCita) {
        this.statusCita = statusCita;
    }
    
     public String getNomHospital() {
        return nomHospital;
    }

    public void setNomHospital(String nomHospital) {
        this.nomHospital = nomHospital;
    }

    public String getNomSolicitante() {
        return nomSolicitante;
    }

    public void setNomSolicitante(String nomPaciente) {
        this.nomSolicitante = nomPaciente;
    }

    public String getNomDonante() {
        return nomDonante;
    }

    public void setNomDonante(String nomDonante) {
        this.nomDonante = nomDonante;
    }
 
    public String getCorreoDon(){
        return correoDonador;
    }
    
    public void setCorreoDon(String correoDonador){
        this.correoDonador=correoDonador;
    }
    
    public String getApellSoli(){
        return apellSolicitante;
    }
    
    public void setApellSoli(String apellSolicitante){
        this.apellSolicitante=apellSolicitante;
    }
    
    public String getApellDon(){
        return apellDonante;
    }
    
    public void setApellDon(String apellDonante){
        this.apellDonante=apellDonante;
    }
}