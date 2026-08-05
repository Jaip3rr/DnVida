package ventanas;
import conexionDB.Conexion;
import java.awt.Color;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelos.*;


public class frmAdministrador extends javax.swing.JFrame {

    DefaultTableModel modelo = new DefaultTableModel();
    
    usuariosMET usuamet = new usuariosMET();
    donadoresMET donmet = new donadoresMET();
    hospitalesMET hosmet = new hospitalesMET();
    pacientesMET pacmet = new pacientesMET();
    solicitantesMET solimet = new solicitantesMET();
    citasMET citmet = new citasMET();
            
            
    TableroMET tabmet = new TableroMET();
    
            
    
    
    
    
    String porPagina = "25";
    
    public frmAdministrador(usuariosModelo usua) {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        txtIdUsua.setText("" + usua.getId());
        jLabel9.setText(usua.getNombre());
        jLabel10.setText(usua.getCorreo());
        tabmet.reporteGraficoPacie(jPanel10);
        tabmet.reporteGraficoDona(jPanel11);
        tabmet.reporteGraficoSoli(jPanel13);
        jLabel39.setText("" + tabmet.totalDatos("Usuarios"));
        jLabel38.setText("" + tabmet.totalDatos("Pacientes"));
        jLabel40.setText("" + tabmet.totalDatos("Solicitantes"));
        jLabel44.setText("" + tabmet.totalDatos("Hospitales"));
        jLabel41.setText("" + tabmet.totalDatos("Donadores"));
        jLabel42.setText("" + tabmet.totalDatos("Citas"));
        page.setVisible(false);
        txtIdUsua.setVisible(false);
        txtIdHosp.setVisible(false);
        txtIdDon.setVisible(false);
        txtIdPacie.setVisible(false);
        txtIdSoli.setVisible(false);
        txtIdCita.setVisible(false);
        
        txtIdUser.setVisible(false);
        menuActivo(TableroLabel);
        
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MMMM-YYYY-hh:mm:ss");
        
        ZonedDateTime.now(ZoneId.of("America/Mexico_City"));
        
        Runnable runnable = () -> {
            while(true){
                try {
                    Thread.sleep(500);
                    EtReloj.setText(formateador.format(LocalDateTime.now()));
                }catch(InterruptedException e){
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }

    private frmAdministrador() {

    }
    
    private void ListarUsuario(String valorBusqueda, int pagina, String cantidad){
       double totalRegistro = usuamet.total(valorBusqueda);
       int desde = (pagina - 1) * Integer.parseInt(cantidad);
       int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));
       
       List<usuariosModelo> Listar = usuamet.listar(valorBusqueda, desde, Integer.parseInt(cantidad));
       modelo = (DefaultTableModel) tblUsuarios.getModel();
       Object[] ob = new Object[6];
            for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getApellido();
            ob[3] = Listar.get(i).getCorreo();
            ob[4] = Listar.get(i).getDireccion();
            ob[5] = Listar.get(i).getTipo();
            modelo.addRow(ob);
        }
            tblUsuarios.setModel(modelo);
            colorTable(tblUsuarios);
            pagination(btnAntUsua, btnSigUsua, pageUsuarios, pagina, totalPage);
    }
    
       private void ListarHospital(String valorBusqueda, int pagina, String cantidad){
       double totalRegistro = hosmet.total(valorBusqueda);
       int desde = (pagina - 1) * Integer.parseInt(cantidad);
       int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));
       
       List<hospitalesModelo> Listar = hosmet.listar(valorBusqueda, desde, Integer.parseInt(cantidad));
       modelo = (DefaultTableModel) tblHospitales.getModel();
       Object[] ob = new Object[6];
            for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getDireccion();
            ob[3] = Listar.get(i).getColonia();
            ob[4] = Listar.get(i).getCorreo();
            ob[5] = Listar.get(i).getTelefono();
            modelo.addRow(ob);
        }
            tblUsuarios.setModel(modelo);
            colorTable(tblHospitales);
            pagination(btnAntHosp, btnSigHosp, pageHospitales, pagina, totalPage);
    }
       
       private void ListarPaciente(String valorBusqueda, int pagina, String cantidad){
       double totalRegistro = pacmet.total(valorBusqueda);
       int desde = (pagina - 1) * Integer.parseInt(cantidad);
       int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));
       
       List<pacientesModelo> Listar = pacmet.listar(valorBusqueda, desde, Integer.parseInt(cantidad));
//       List<hospitalesModelo> Listar2 = hosmet.listar(valorBusqueda, desde, Integer.parseInt(cantidad));
       modelo = (DefaultTableModel) tblPacientes.getModel();
       Object[] ob = new Object[10];
            for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNomHospital();
            ob[2] = Listar.get(i).getNombre();
            ob[3] = Listar.get(i).getApellido();
            ob[4] = Listar.get(i).getSexo();
            ob[5] = Listar.get(i).getDireccion();
            ob[6] = Listar.get(i).getColonia();
            ob[7] = Listar.get(i).getTipoSangre();
            ob[8] = Listar.get(i).getTelefono();
            ob[9] = Listar.get(i).getFechaRegistro();

            modelo.addRow(ob);
        }
            tblPacientes.setModel(modelo);
            colorTable(tblPacientes);
            pagination(btnAntPacie, btnSigPacie, pagePacientes, pagina, totalPage);
    }

       
       private void ListarSolicitante(String valorBusqueda, int pagina, String cantidad){
       double totalRegistro = solimet.total(valorBusqueda);
       int desde = (pagina - 1) * Integer.parseInt(cantidad);
       int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));
       
       List<solicitantesModelo> Listar = solimet.listar(valorBusqueda, desde, Integer.parseInt(cantidad));
       modelo = (DefaultTableModel) tblSolicitantes.getModel();
       Object[] ob = new Object[10];
            for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNomHospital();
            ob[2] = Listar.get(i).getNombre();
            ob[3] = Listar.get(i).getApellido();
            ob[4] = Listar.get(i).getSexo();
            ob[5] = Listar.get(i).getDireccion();
            ob[6] = Listar.get(i).getColonia();
            ob[7] = Listar.get(i).getTipoSangre();
            ob[8] = Listar.get(i).getTelefono();
            ob[9] = Listar.get(i).getFechaRegistro();
            modelo.addRow(ob);
        }
            tblUsuarios.setModel(modelo);
            colorTable(tblSolicitantes);
            pagination(btnAntSoli, btnSigSoli, pageSolicitantes, pagina, totalPage);
    }
    
    private void ListarDonante(String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = donmet.total(valorBusqueda);
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro/Double.parseDouble(cantidad));
        
        List<donadoresModelo> Listar = donmet.listar(valorBusqueda, desde, Integer.parseInt(cantidad));
        modelo = (DefaultTableModel) tblDonantes.getModel();
        Object[] ob = new Object[11];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getApellido();
            ob[3] = Listar.get(i).getCorreo();
            ob[4] = Listar.get(i).getSexo();
            ob[5] = Listar.get(i).getColonia();
            ob[6] = Listar.get(i).getTelefono();
            ob[7] = Listar.get(i).getModiCorpo();
            ob[8] = Listar.get(i).getTipoSangre();
            ob[9] = Listar.get(i).getUltimaDon();
            ob[10] = Listar.get(i).getFechaRegistro();
            modelo.addRow(ob);
        }
       
        tblDonantes.setModel(modelo);
        colorTable(tblDonantes);
        pagination(btnAntDon, btnSigDon, pageDonantes, pagina, totalPage);
    }
    
        private void ListarCita(String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = citmet.total(valorBusqueda);
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro/Double.parseDouble(cantidad));
        
        List<citasModelo> Listar = citmet.listar(valorBusqueda, desde, Integer.parseInt(cantidad));
        modelo = (DefaultTableModel) tblCitas.getModel();
        Object[] ob = new Object[9];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNomHospital();
            ob[2] = Listar.get(i).getNomSolicitante();
            ob[3] = Listar.get(i).getApellSoli();
            ob[4] = Listar.get(i).getNomDonante();
            ob[5] = Listar.get(i).getApellDon();
            ob[6] = Listar.get(i).getCorreoDon();
            ob[7] = Listar.get(i).getFechaCita();
            ob[8] = Listar.get(i).getStatusCita();
            modelo.addRow(ob);
        }
       
        tblCitas.setModel(modelo);
        colorTable(tblCitas);
        pagination(btnAntCitas, btnSigCitas, pageCitas, pagina, totalPage);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        eliminarUsuario = new javax.swing.JMenuItem();
        editarUsuario = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        eliminarHospital = new javax.swing.JMenuItem();
        editarHospital = new javax.swing.JMenuItem();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        eliminarPaciente = new javax.swing.JMenuItem();
        editarPaciente = new javax.swing.JMenuItem();
        jPopupMenu4 = new javax.swing.JPopupMenu();
        eliminarDonadores = new javax.swing.JMenuItem();
        editarDonadores = new javax.swing.JMenuItem();
        notificarDonadores = new javax.swing.JMenuItem();
        jPopupMenu5 = new javax.swing.JPopupMenu();
        eliminarSolicitante = new javax.swing.JMenuItem();
        editarSolicitante = new javax.swing.JMenuItem();
        jPopupMenu6 = new javax.swing.JPopupMenu();
        eliminarCitas = new javax.swing.JMenuItem();
        editarCitas = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        UsuariosLabel = new javax.swing.JLabel();
        TableroLabel = new javax.swing.JLabel();
        CitasLabel = new javax.swing.JLabel();
        PacientesLabel = new javax.swing.JLabel();
        DonadoresLabel = new javax.swing.JLabel();
        SolicitantesLabel = new javax.swing.JLabel();
        HospitalesLabel = new javax.swing.JLabel();
        btSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        EtReloj = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtNomUsua = new javax.swing.JTextField();
        txtApellUsuario = new javax.swing.JTextField();
        txtCorrUsua = new javax.swing.JTextField();
        txtDireUsua = new javax.swing.JTextField();
        txtClaUsua = new javax.swing.JTextField();
        txtIdUsua = new javax.swing.JTextField();
        btnSaveUsua = new javax.swing.JButton();
        btnNewUsua = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        pageUsuarios = new javax.swing.JLabel();
        btnSigUsua = new javax.swing.JButton();
        btnAntUsua = new javax.swing.JButton();
        txtBuscarUsuarios = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        txtNomHosp = new javax.swing.JTextField();
        txtDirecHosp = new javax.swing.JTextField();
        txtCorrHosp = new javax.swing.JTextField();
        txtColoHosp = new javax.swing.JTextField();
        txtTelHosp = new javax.swing.JTextField();
        txtIdHosp = new javax.swing.JTextField();
        btnSaveHosp = new javax.swing.JButton();
        btnNewHosp = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblHospitales = new javax.swing.JTable();
        pageHospitales = new javax.swing.JLabel();
        btnSigHosp = new javax.swing.JButton();
        btnAntHosp = new javax.swing.JButton();
        txtBuscarHospitales = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txtNomPacie = new javax.swing.JTextField();
        txtApellPacie = new javax.swing.JTextField();
        txtSexoPacie = new javax.swing.JTextField();
        txtDirePacie = new javax.swing.JTextField();
        txtTipoPacie = new javax.swing.JTextField();
        txtIdHospPacie = new javax.swing.JTextField();
        txtColoPacie = new javax.swing.JTextField();
        txtTelPacie = new javax.swing.JTextField();
        txtFechRePacie = new javax.swing.JTextField();
        btnSavePacie = new javax.swing.JButton();
        btnNewPacie = new javax.swing.JButton();
        txtIdPacie = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPacientes = new javax.swing.JTable();
        pagePacientes = new javax.swing.JLabel();
        btnSigPacie = new javax.swing.JButton();
        btnAntPacie = new javax.swing.JButton();
        txtBuscarPacientes = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        txtSexoDon = new javax.swing.JTextField();
        txtIdDon = new javax.swing.JTextField();
        txtFecRegDon = new javax.swing.JTextField();
        txtNomDon = new javax.swing.JTextField();
        txtApellDon = new javax.swing.JTextField();
        txtCorrDon = new javax.swing.JTextField();
        txtColDon = new javax.swing.JTextField();
        txtTelDon = new javax.swing.JTextField();
        txtModifiDon = new javax.swing.JTextField();
        txtTipoDon = new javax.swing.JTextField();
        txtUltimaDon = new javax.swing.JTextField();
        btnSaveDon = new javax.swing.JButton();
        btnNewDon = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDonantes = new javax.swing.JTable();
        txtBuscarDonante = new javax.swing.JTextField();
        pageDonantes = new javax.swing.JLabel();
        btnAntDon = new javax.swing.JButton();
        btnSigDon = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        txtNomSoli = new javax.swing.JTextField();
        txtApellSoli = new javax.swing.JTextField();
        txtSexoSoli = new javax.swing.JTextField();
        txtDireSoli = new javax.swing.JTextField();
        txtTipoSoli = new javax.swing.JTextField();
        txtIdHospSoli = new javax.swing.JTextField();
        txtColoSoli = new javax.swing.JTextField();
        txtTelSoli = new javax.swing.JTextField();
        txtFechReSoli = new javax.swing.JTextField();
        btnSaveSoli = new javax.swing.JButton();
        btnNewSoli = new javax.swing.JButton();
        txtIdSoli = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSolicitantes = new javax.swing.JTable();
        pageSolicitantes = new javax.swing.JLabel();
        btnSigSoli = new javax.swing.JButton();
        btnAntSoli = new javax.swing.JButton();
        txtBuscarSolicitante = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        txtIdHosCita = new javax.swing.JTextField();
        txtIdSolCita = new javax.swing.JTextField();
        txtIdDonCita = new javax.swing.JTextField();
        txtFechaCita = new javax.swing.JTextField();
        txtStatusCita = new javax.swing.JTextField();
        txtIdCita = new javax.swing.JTextField();
        btnSaveCitas = new javax.swing.JButton();
        btnNewCitas = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCitas = new javax.swing.JTable();
        pageCitas = new javax.swing.JLabel();
        btnSigCitas = new javax.swing.JButton();
        btnAntCitas = new javax.swing.JButton();
        txtBuscarCitas = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtIdUser = new javax.swing.JTextField();
        page = new javax.swing.JTextField();

        eliminarUsuario.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\eliminar.png")); // NOI18N
        eliminarUsuario.setText("Eliminar");
        eliminarUsuario.setToolTipText("");
        eliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarUsuarioActionPerformed(evt);
            }
        });
        jPopupMenu1.add(eliminarUsuario);

        editarUsuario.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\editar.png")); // NOI18N
        editarUsuario.setText("Editar");
        editarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarUsuarioActionPerformed(evt);
            }
        });
        jPopupMenu1.add(editarUsuario);

        eliminarHospital.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\eliminar.png")); // NOI18N
        eliminarHospital.setText("Eliminar");
        eliminarHospital.setToolTipText("");
        eliminarHospital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarHospitalActionPerformed(evt);
            }
        });
        jPopupMenu2.add(eliminarHospital);

        editarHospital.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\editar.png")); // NOI18N
        editarHospital.setText("Editar");
        editarHospital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarHospitalActionPerformed(evt);
            }
        });
        jPopupMenu2.add(editarHospital);

        eliminarPaciente.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\eliminar.png")); // NOI18N
        eliminarPaciente.setText("Eliminar");
        eliminarPaciente.setToolTipText("");
        eliminarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarPacienteActionPerformed(evt);
            }
        });
        jPopupMenu3.add(eliminarPaciente);

        editarPaciente.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\editar.png")); // NOI18N
        editarPaciente.setText("Editar");
        editarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarPacienteActionPerformed(evt);
            }
        });
        jPopupMenu3.add(editarPaciente);

        eliminarDonadores.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\eliminar.png")); // NOI18N
        eliminarDonadores.setText("Eliminar");
        eliminarDonadores.setToolTipText("");
        eliminarDonadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarDonadoresActionPerformed(evt);
            }
        });
        jPopupMenu4.add(eliminarDonadores);

        editarDonadores.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\editar.png")); // NOI18N
        editarDonadores.setText("Editar");
        editarDonadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarDonadoresActionPerformed(evt);
            }
        });
        jPopupMenu4.add(editarDonadores);

        notificarDonadores.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\editar.png")); // NOI18N
        notificarDonadores.setText("Notificar");
        notificarDonadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificarDonadoresActionPerformed(evt);
            }
        });
        jPopupMenu4.add(notificarDonadores);

        eliminarSolicitante.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\eliminar.png")); // NOI18N
        eliminarSolicitante.setText("Eliminar");
        eliminarSolicitante.setToolTipText("");
        eliminarSolicitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarSolicitanteActionPerformed(evt);
            }
        });
        jPopupMenu5.add(eliminarSolicitante);

        editarSolicitante.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\editar.png")); // NOI18N
        editarSolicitante.setText("Editar");
        editarSolicitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarSolicitanteActionPerformed(evt);
            }
        });
        jPopupMenu5.add(editarSolicitante);

        eliminarCitas.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\eliminar.png")); // NOI18N
        eliminarCitas.setText("Eliminar");
        eliminarCitas.setToolTipText("");
        eliminarCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarCitasActionPerformed(evt);
            }
        });
        jPopupMenu6.add(eliminarCitas);

        editarCitas.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\editar.png")); // NOI18N
        editarCitas.setText("Editar");
        editarCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarCitasActionPerformed(evt);
            }
        });
        jPopupMenu6.add(editarCitas);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        UsuariosLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        UsuariosLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imageEmp.png")); // NOI18N
        UsuariosLabel.setText("Usuarios");
        UsuariosLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsuariosLabelMouseClicked(evt);
            }
        });
        jPanel1.add(UsuariosLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 130, 50));

        TableroLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        TableroLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\tablero.png")); // NOI18N
        TableroLabel.setText(" Agendar");
        TableroLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableroLabelMouseClicked(evt);
            }
        });
        jPanel1.add(TableroLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 50));

        CitasLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        CitasLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\corazon.png")); // NOI18N
        CitasLabel.setText("Citas");
        CitasLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CitasLabelMouseClicked(evt);
            }
        });
        jPanel1.add(CitasLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 140, 50));

        PacientesLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        PacientesLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\clientes.png")); // NOI18N
        PacientesLabel.setText("Pacientes");
        PacientesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PacientesLabelMouseClicked(evt);
            }
        });
        jPanel1.add(PacientesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 140, 50));

        DonadoresLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        DonadoresLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\clientes.png")); // NOI18N
        DonadoresLabel.setText("Donadores");
        DonadoresLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DonadoresLabelMouseClicked(evt);
            }
        });
        jPanel1.add(DonadoresLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 140, 50));

        SolicitantesLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        SolicitantesLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\clientes.png")); // NOI18N
        SolicitantesLabel.setText("Solicitantes");
        SolicitantesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SolicitantesLabelMouseClicked(evt);
            }
        });
        jPanel1.add(SolicitantesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 140, 60));

        HospitalesLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        HospitalesLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\nombre.png")); // NOI18N
        HospitalesLabel.setText("Hospitales");
        HospitalesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HospitalesLabelMouseClicked(evt);
            }
        });
        jPanel1.add(HospitalesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 130, 50));

        btSalir.setBackground(new java.awt.Color(153, 153, 255));
        btSalir.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btSalir.setText("Cerrar Sesion");
        btSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 120, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 150, 520));

        jLabel20.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\logoEmpresaurio.png")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 120));

        EtReloj.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        EtReloj.setText("fecha");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addComponent(EtReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(EtReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 770, 120));

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(204, 204, 204));
        jLabel41.setText("0");
        jPanel9.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 90, -1));

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Citas programadas");
        jPanel9.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 60, 130, -1));

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Total Pacientes");
        jPanel9.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 110, -1));

        jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Total Donadores");
        jPanel9.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 110, -1));

        jLabel42.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(204, 204, 204));
        jLabel42.setText("0");
        jPanel9.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 90, -1));

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Total Solicitantes");
        jPanel9.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 110, -1));

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Total Usuarios");
        jPanel9.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 110, -1));

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(204, 204, 204));
        jLabel40.setText("0");
        jPanel9.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 90, -1));

        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(204, 204, 204));
        jLabel38.setText("0");
        jPanel9.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 90, -1));

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(204, 204, 204));
        jLabel39.setText("0");
        jPanel9.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 90, -1));

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Total Hospitales");
        jPanel9.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, 110, -1));

        jLabel1.setBackground(new java.awt.Color(204, 102, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setOpaque(true);
        jPanel9.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, 150, 82));

        jLabel6.setBackground(new java.awt.Color(0, 153, 51));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setOpaque(true);
        jPanel9.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 0, 150, 82));

        jLabel8.setBackground(new java.awt.Color(255, 51, 51));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setOpaque(true);
        jPanel9.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 150, 82));

        jLabel11.setBackground(new java.awt.Color(153, 0, 255));
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setOpaque(true);
        jPanel9.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 150, 82));

        jLabel12.setBackground(new java.awt.Color(51, 51, 255));
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setOpaque(true);
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 150, 82));

        jLabel44.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(204, 204, 204));
        jLabel44.setText("0");
        jPanel9.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 90, -1));

        jLabel18.setBackground(new java.awt.Color(153, 0, 153));
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setOpaque(true);
        jPanel9.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 150, 82));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 330, 290));

        jPanel11.setPreferredSize(new java.awt.Dimension(330, 290));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 370, 290));

        jPanel13.setMinimumSize(new java.awt.Dimension(330, 290));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 160, -1, -1));

        jTabbedPane1.addTab("tab2", jPanel9);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtNomUsua.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre"));
        txtNomUsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomUsuaActionPerformed(evt);
            }
        });

        txtApellUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Apellido"));
        txtApellUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellUsuarioActionPerformed(evt);
            }
        });

        txtCorrUsua.setBorder(javax.swing.BorderFactory.createTitledBorder("Correo"));

        txtDireUsua.setBorder(javax.swing.BorderFactory.createTitledBorder("Direccion"));

        txtClaUsua.setBorder(javax.swing.BorderFactory.createTitledBorder("Contraseña"));

        btnSaveUsua.setBackground(new java.awt.Color(204, 204, 204));
        btnSaveUsua.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\guardar.png")); // NOI18N
        btnSaveUsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveUsuaActionPerformed(evt);
            }
        });

        btnNewUsua.setBackground(new java.awt.Color(204, 204, 204));
        btnNewUsua.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\nuevo.png")); // NOI18N
        btnNewUsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewUsuaActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgName.png")); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgApell.png")); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgCorr.png")); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgDirec.png")); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgClav.png")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdUsua, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNomUsua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtApellUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCorrUsua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDireUsua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtClaUsua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnSaveUsua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNewUsua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(46, 46, 46))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomUsua, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtApellUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCorrUsua))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDireUsua))
                .addGap(55, 55, 55)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtClaUsua))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIdUsua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveUsua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewUsua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 430));

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellido", "Correo", "Direccion", "Tipo"
            }
        ));
        tblUsuarios.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tblUsuarios);
        if (tblUsuarios.getColumnModel().getColumnCount() > 0) {
            tblUsuarios.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 850, 290));

        pageUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pageUsuarios.setText("Página 1 de un total de 20 Paginas");
        jPanel4.add(pageUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, -1, -1));

        btnSigUsua.setText("Siguiente");
        jPanel4.add(btnSigUsua, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 360, -1, -1));

        btnAntUsua.setText("Anterior");
        jPanel4.add(btnAntUsua, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 360, -1, -1));

        txtBuscarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarUsuariosActionPerformed(evt);
            }
        });
        txtBuscarUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarUsuariosKeyReleased(evt);
            }
        });
        jPanel4.add(txtBuscarUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 660, 30));

        jTabbedPane1.addTab("tab1", jPanel4);

        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hospitales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtNomHosp.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre"));
        txtNomHosp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomHospActionPerformed(evt);
            }
        });

        txtDirecHosp.setBorder(javax.swing.BorderFactory.createTitledBorder("Direccion"));
        txtDirecHosp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirecHospActionPerformed(evt);
            }
        });

        txtCorrHosp.setBorder(javax.swing.BorderFactory.createTitledBorder("Correo"));

        txtColoHosp.setBorder(javax.swing.BorderFactory.createTitledBorder("Colonia"));

        txtTelHosp.setBorder(javax.swing.BorderFactory.createTitledBorder("Telefono"));

        btnSaveHosp.setBackground(new java.awt.Color(204, 204, 204));
        btnSaveHosp.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\guardar.png")); // NOI18N
        btnSaveHosp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveHospActionPerformed(evt);
            }
        });

        btnNewHosp.setBackground(new java.awt.Color(204, 204, 204));
        btnNewHosp.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\nuevo.png")); // NOI18N
        btnNewHosp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewHospActionPerformed(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgName.png")); // NOI18N

        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgApell.png")); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgCorr.png")); // NOI18N

        jLabel16.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgDirec.png")); // NOI18N

        jLabel17.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\telefono.png")); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtIdHosp, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNomHosp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDirecHosp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtColoHosp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCorrHosp, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnSaveHosp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNewHosp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(46, 46, 46))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel17)
                        .addGap(26, 26, 26)
                        .addComponent(txtTelHosp, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomHosp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDirecHosp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtColoHosp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorrHosp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTelHosp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(txtIdHosp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveHosp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewHosp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel16.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 430));

        tblHospitales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Direccion", "Colonia", "Correo", "Telefono"
            }
        ));
        tblHospitales.setComponentPopupMenu(jPopupMenu2);
        jScrollPane6.setViewportView(tblHospitales);
        if (tblHospitales.getColumnModel().getColumnCount() > 0) {
            tblHospitales.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel16.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 850, 290));

        pageHospitales.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pageHospitales.setText("Página 1 de un total de 20 Paginas");
        jPanel16.add(pageHospitales, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, -1, -1));

        btnSigHosp.setText("Siguiente");
        jPanel16.add(btnSigHosp, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 360, -1, -1));

        btnAntHosp.setText("Anterior");
        jPanel16.add(btnAntHosp, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 360, -1, -1));

        txtBuscarHospitales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarHospitalesActionPerformed(evt);
            }
        });
        txtBuscarHospitales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarHospitalesKeyReleased(evt);
            }
        });
        jPanel16.add(txtBuscarHospitales, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 660, 30));

        jTabbedPane1.addTab("tab1", jPanel16);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pacientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtNomPacie.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre"));
        txtNomPacie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPacieActionPerformed(evt);
            }
        });

        txtApellPacie.setBorder(javax.swing.BorderFactory.createTitledBorder("Apellido"));
        txtApellPacie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellPacieActionPerformed(evt);
            }
        });

        txtSexoPacie.setBorder(javax.swing.BorderFactory.createTitledBorder("Sexo"));

        txtDirePacie.setBorder(javax.swing.BorderFactory.createTitledBorder("Direccion"));

        txtTipoPacie.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Sangre"));

        txtIdHospPacie.setBorder(javax.swing.BorderFactory.createTitledBorder("Id Hospital"));
        txtIdHospPacie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHospPacieActionPerformed(evt);
            }
        });

        txtColoPacie.setBorder(javax.swing.BorderFactory.createTitledBorder("Colonia"));

        txtTelPacie.setBorder(javax.swing.BorderFactory.createTitledBorder("Telefono"));

        txtFechRePacie.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha Registrado"));

        btnSavePacie.setBackground(new java.awt.Color(204, 204, 204));
        btnSavePacie.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\guardar.png")); // NOI18N
        btnSavePacie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePacieActionPerformed(evt);
            }
        });

        btnNewPacie.setBackground(new java.awt.Color(204, 204, 204));
        btnNewPacie.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\nuevo.png")); // NOI18N
        btnNewPacie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPacieActionPerformed(evt);
            }
        });

        jLabel21.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgName.png")); // NOI18N

        jLabel22.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgApell.png")); // NOI18N

        jLabel24.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\telefono.png")); // NOI18N

        jLabel25.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\direccion.png")); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\Tipo de sangre.png")); // NOI18N

        jLabel27.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgColo.png")); // NOI18N

        jLabel33.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\Genero.png")); // NOI18N

        jLabel46.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\id.png")); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel24)
                                        .addComponent(txtIdPacie, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel25)
                                        .addComponent(jLabel26)
                                        .addComponent(jLabel27)
                                        .addComponent(jLabel33))
                                    .addComponent(jLabel22)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel46)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDirePacie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTipoPacie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtColoPacie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNomPacie, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdHospPacie, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellPacie, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSexoPacie, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelPacie, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechRePacie, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnSavePacie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNewPacie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtIdHospPacie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomPacie, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApellPacie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSexoPacie)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDirePacie)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtColoPacie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTipoPacie)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(txtTelPacie)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtFechRePacie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdPacie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSavePacie, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNewPacie, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(179, 179, 179))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel6.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 470));

        tblPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre Hosp", "Nombre", "Apellido", "Sexo", "Direccion", "Colonia", "tipoSangre", "Telefono", "fechaRegistro"
            }
        ));
        tblPacientes.setComponentPopupMenu(jPopupMenu3);
        jScrollPane3.setViewportView(tblPacientes);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 860, 290));

        pagePacientes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pagePacientes.setText("Página 1 de un total de 20 Paginas");
        jPanel6.add(pagePacientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, -1, -1));

        btnSigPacie.setText("Siguiente");
        jPanel6.add(btnSigPacie, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 360, -1, -1));

        btnAntPacie.setText("Anterior");
        jPanel6.add(btnAntPacie, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 360, -1, -1));

        txtBuscarPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarPacientesActionPerformed(evt);
            }
        });
        txtBuscarPacientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPacientesKeyReleased(evt);
            }
        });
        jPanel6.add(txtBuscarPacientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 660, 30));

        jLabel23.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\telefono.png")); // NOI18N
        jPanel6.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("tab1", jPanel6);

        jPanel7.setForeground(new java.awt.Color(255, 51, 102));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Donadores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtSexoDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Sexo"));

        txtIdDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdDonActionPerformed(evt);
            }
        });

        txtFecRegDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha Registro"));

        txtNomDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre"));

        txtApellDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Apellido"));

        txtCorrDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Correo"));

        txtColDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Colonia"));

        txtTelDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Telefono"));

        txtModifiDon.setBorder(javax.swing.BorderFactory.createTitledBorder("¿Modificacion?"));

        txtTipoDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Sangre"));

        txtUltimaDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Ultima donacion"));

        btnSaveDon.setBackground(new java.awt.Color(204, 204, 204));
        btnSaveDon.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\guardar.png")); // NOI18N
        btnSaveDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveDonActionPerformed(evt);
            }
        });

        btnNewDon.setBackground(new java.awt.Color(204, 204, 204));
        btnNewDon.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\nuevo.png")); // NOI18N
        btnNewDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewDonActionPerformed(evt);
            }
        });

        jLabel63.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\modificaRRR.png")); // NOI18N

        jLabel62.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgColo.png")); // NOI18N

        jLabel64.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\telefono.png")); // NOI18N

        jLabel58.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgName.png")); // NOI18N

        jLabel60.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\Genero.png")); // NOI18N

        jLabel59.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgApell.png")); // NOI18N

        jLabel65.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgCorr.png")); // NOI18N

        jLabel66.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\Tipo de sangre.png")); // NOI18N

        jLabel67.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\calendario.png")); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel59)
                            .addComponent(jLabel58)
                            .addComponent(jLabel65)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel60))
                            .addComponent(jLabel62)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel63)
                            .addComponent(jLabel64)
                            .addComponent(jLabel66)
                            .addComponent(jLabel67))))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnSaveDon, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNewDon, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(txtIdDon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCorrDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtColDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSexoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtModifiDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUltimaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFecRegDon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApellDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel60)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(txtCorrDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSexoDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62)
                    .addComponent(txtColDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64)
                    .addComponent(txtTelDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel63))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtModifiDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTipoDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUltimaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(txtFecRegDon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tblDonantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "nombre", "apellido", "correo", "sexo", "colonia", "telefono", "modificacion Corporal", "tipo sangre", "ultima donacion", "fecha registro"
            }
        ));
        tblDonantes.setComponentPopupMenu(jPopupMenu4);
        jScrollPane2.setViewportView(tblDonantes);

        txtBuscarDonante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarDonanteActionPerformed(evt);
            }
        });
        txtBuscarDonante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarDonanteKeyReleased(evt);
            }
        });

        pageDonantes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pageDonantes.setText("Página 1 de un total de 20 Paginas");

        btnAntDon.setText("Anterior");

        btnSigDon.setText("Siguiente");

        jLabel19.setText("Tipo de sangre:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(pageDonantes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarDonante, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(btnAntDon)
                        .addGap(106, 106, 106)
                        .addComponent(btnSigDon)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscarDonante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pageDonantes)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAntDon)
                    .addComponent(btnSigDon))
                .addContainerGap(148, Short.MAX_VALUE))
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", jPanel7);

        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Solicitantes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtNomSoli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre"));
        txtNomSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomSoliActionPerformed(evt);
            }
        });

        txtApellSoli.setBorder(javax.swing.BorderFactory.createTitledBorder("Apellido"));
        txtApellSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellSoliActionPerformed(evt);
            }
        });

        txtSexoSoli.setBorder(javax.swing.BorderFactory.createTitledBorder("Sexo"));

        txtDireSoli.setBorder(javax.swing.BorderFactory.createTitledBorder("Direccion"));

        txtTipoSoli.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Sangre"));

        txtIdHospSoli.setBorder(javax.swing.BorderFactory.createTitledBorder("Id Hospital"));
        txtIdHospSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHospSoliActionPerformed(evt);
            }
        });

        txtColoSoli.setBorder(javax.swing.BorderFactory.createTitledBorder("Colonia"));
        txtColoSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtColoSoliActionPerformed(evt);
            }
        });

        txtTelSoli.setBorder(javax.swing.BorderFactory.createTitledBorder("Telefono"));

        txtFechReSoli.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha de Registro"));

        btnSaveSoli.setBackground(new java.awt.Color(204, 204, 204));
        btnSaveSoli.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\guardar.png")); // NOI18N
        btnSaveSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveSoliActionPerformed(evt);
            }
        });

        btnNewSoli.setBackground(new java.awt.Color(204, 204, 204));
        btnNewSoli.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\nuevo.png")); // NOI18N
        btnNewSoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSoliActionPerformed(evt);
            }
        });

        jLabel47.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\id.png")); // NOI18N

        jLabel48.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgApell.png")); // NOI18N

        jLabel49.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgName.png")); // NOI18N

        jLabel50.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\Genero.png")); // NOI18N

        jLabel51.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\direccion.png")); // NOI18N

        jLabel52.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgColo.png")); // NOI18N

        jLabel53.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\Tipo de sangre.png")); // NOI18N

        jLabel54.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\telefono.png")); // NOI18N

        jLabel57.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgCorr.png")); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel47))
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtIdSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel57))
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel50)
                                        .addComponent(jLabel51))))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel54)
                                    .addComponent(jLabel53))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtDireSoli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTipoSoli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtColoSoli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtNomSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdHospSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSexoSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 9, Short.MAX_VALUE))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelSoli)
                                    .addComponent(txtFechReSoli, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addContainerGap())))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(btnSaveSoli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNewSoli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIdHospSoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApellSoli, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSexoSoli, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel50)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDireSoli, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel51)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtColoSoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(txtTipoSoli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel53)
                        .addGap(13, 13, 13)))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTelSoli, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addGap(14, 14, 14)
                        .addComponent(txtIdSoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtFechReSoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewSoli, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel18.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 500));

        tblSolicitantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre Hosp", "Nombre", "Apellido", "Sexo", "Direccion", "Colonia", "tipoSangre", "Telefono", "fechaRegistro"
            }
        ));
        tblSolicitantes.setComponentPopupMenu(jPopupMenu5);
        jScrollPane7.setViewportView(tblSolicitantes);
        if (tblSolicitantes.getColumnModel().getColumnCount() > 0) {
            tblSolicitantes.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel18.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 860, 290));

        pageSolicitantes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pageSolicitantes.setText("Página 1 de un total de 20 Paginas");
        jPanel18.add(pageSolicitantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, -1, -1));

        btnSigSoli.setText("Siguiente");
        jPanel18.add(btnSigSoli, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 360, -1, -1));

        btnAntSoli.setText("Anterior");
        jPanel18.add(btnAntSoli, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 360, -1, -1));

        txtBuscarSolicitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarSolicitanteActionPerformed(evt);
            }
        });
        txtBuscarSolicitante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarSolicitanteKeyReleased(evt);
            }
        });
        jPanel18.add(txtBuscarSolicitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 660, 30));

        jTabbedPane1.addTab("tab1", jPanel18);

        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Citas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtIdHosCita.setBorder(javax.swing.BorderFactory.createTitledBorder("Id hospital"));
        txtIdHosCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHosCitaActionPerformed(evt);
            }
        });

        txtIdSolCita.setBorder(javax.swing.BorderFactory.createTitledBorder("Id solicitante"));
        txtIdSolCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdSolCitaActionPerformed(evt);
            }
        });

        txtIdDonCita.setBorder(javax.swing.BorderFactory.createTitledBorder("Id donador"));

        txtFechaCita.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha cita"));

        txtStatusCita.setBorder(javax.swing.BorderFactory.createTitledBorder("Status(Completado/Pendiente)"));

        btnSaveCitas.setBackground(new java.awt.Color(204, 204, 204));
        btnSaveCitas.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\guardar.png")); // NOI18N
        btnSaveCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveCitasActionPerformed(evt);
            }
        });

        btnNewCitas.setBackground(new java.awt.Color(204, 204, 204));
        btnNewCitas.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\nuevo.png")); // NOI18N
        btnNewCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewCitasActionPerformed(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgName.png")); // NOI18N

        jLabel29.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgApell.png")); // NOI18N

        jLabel30.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgCorr.png")); // NOI18N

        jLabel31.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\imgDirec.png")); // NOI18N

        jLabel32.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\nombre.png")); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnSaveCitas)
                        .addGap(15, 15, 15)
                        .addComponent(btnNewCitas)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtIdHosCita, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtIdSolCita, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtIdDonCita, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtFechaCita, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtStatusCita, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdHosCita, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtIdSolCita))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtIdDonCita))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFechaCita))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStatusCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addComponent(txtIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNewCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnSaveCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel14.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 480));

        tblCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre Hospital", "Nombre Solicitante", "Apellido Solicitante", "Nombre Donador", "Apelldo Donador", "Correo Donador", "Fecha Cita", "Status"
            }
        ));
        tblCitas.setComponentPopupMenu(jPopupMenu6);
        jScrollPane5.setViewportView(tblCitas);

        jPanel14.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 850, 290));

        pageCitas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pageCitas.setText("Página 1 de un total de 20 Paginas");
        jPanel14.add(pageCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, -1, -1));

        btnSigCitas.setText("Siguiente");
        jPanel14.add(btnSigCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 360, -1, -1));

        btnAntCitas.setText("Anterior");
        jPanel14.add(btnAntCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 360, -1, -1));

        txtBuscarCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarCitasActionPerformed(evt);
            }
        });
        txtBuscarCitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCitasKeyReleased(evt);
            }
        });
        jPanel14.add(txtBuscarCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 660, 30));

        jTabbedPane1.addTab("tab1", jPanel14);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 1140, 540));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("Admin User");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, -1, -1));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setText("email");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, -1, -1));
        getContentPane().add(txtIdUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 20, 29, -1));

        page.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pageActionPerformed(evt);
            }
        });
        getContentPane().add(page, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 50, 29, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomUsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomUsuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomUsuaActionPerformed

    private void txtApellUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellUsuarioActionPerformed

    private void pageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pageActionPerformed

    private void txtBuscarUsuariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarUsuariosKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarUsuario(txtBuscarUsuarios.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarUsuariosKeyReleased

    private void txtBuscarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarUsuariosActionPerformed
        txtBuscarUsuariosActionPerformed(evt);
    }//GEN-LAST:event_txtBuscarUsuariosActionPerformed

    private void btnSaveUsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveUsuaActionPerformed
        if(!"".equals(txtNomUsua.getText()) && !"".equals(txtApellUsuario.getText()) && !"".equals(txtCorrUsua.getText()) && !"".equals(txtDireUsua.getText()) && !"".equals(txtClaUsua.getText())){
            usuariosModelo emp = new usuariosModelo();
            emp.setNombre(txtNomUsua.getText());
            emp.setApellido(txtApellUsuario.getText());
            emp.setCorreo(txtCorrUsua.getText());
            emp.setDireecion(txtDireUsua.getText());
            emp.setClave(txtClaUsua.getText());
            if(txtIdUsua.getText().length() > 0){
                emp.setId(Integer.parseInt(txtIdUsua.getText()));
                String respuesta = usuamet.modificar(emp);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "EL CORREO DEL USUARIO YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO MODIFICADO");
                        limpiarUsuario();
                        LimpiarTable();
                        ListarUsuario(txtBuscarUsuarios.getText(), Integer.parseInt(page.getText()), porPagina);
                     }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
            }
        } else {
                String respuesta = usuamet.registrar(emp);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "CORREO DEL USUARIO YA EXISTENTE");
                    case "registrado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO");
                        limpiarUsuario();
                        LimpiarTable();
                        ListarUsuario(txtBuscarUsuarios.getText(), Integer.parseInt(page.getText()), porPagina);
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR USUARIO");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnSaveUsuaActionPerformed

    private void btnNewUsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewUsuaActionPerformed
        limpiarUsuario();
    }//GEN-LAST:event_btnNewUsuaActionPerformed

    private void eliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarUsuarioActionPerformed
        if(tblUsuarios.getSelectedRow() >= 0) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar", "ADVERTENCIA", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (pregunta == 0 ){
            txtIdUsua.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0).toString());
            int id = Integer.parseInt(txtIdUsua.getText());
            usuamet.eliminar(id);
            LimpiarTable();
            limpiarUsuario();
            ListarUsuario(txtBuscarUsuarios.getText(), Integer.parseInt(page.getText()), porPagina);
                } 
        } else {
                JOptionPane.showMessageDialog(null, "SELECCIONA UN USUARIO");
            }
    }//GEN-LAST:event_eliminarUsuarioActionPerformed

    private void editarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarUsuarioActionPerformed
        if(tblUsuarios.getSelectedRow() >= 0){
            txtIdUsua.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0).toString());
            txtNomUsua.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 1).toString());
            txtApellUsuario.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 2).toString());
            txtCorrUsua.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 3).toString());
            txtDireUsua.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 4).toString());
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
        } 
    }//GEN-LAST:event_editarUsuarioActionPerformed

    private void UsuariosLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsuariosLabelMouseClicked
        LimpiarTable();
        page.setText("1");
        ListarUsuario(txtBuscarUsuarios.getText(), Integer.parseInt(page.getText()), porPagina);
        limpiarUsuario();
        jTabbedPane1.setSelectedIndex(1);
        menuInactivo();
        menuActivo(UsuariosLabel);
    }//GEN-LAST:event_UsuariosLabelMouseClicked

    private void TableroLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableroLabelMouseClicked
        jPanel10.removeAll();
        tabmet.reporteGraficoPacie(jPanel10);
        tabmet.reporteGraficoDona(jPanel11);
        tabmet.reporteGraficoSoli(jPanel13);
        jLabel39.setText("" + tabmet.totalDatos("Usuarios"));
        jLabel38.setText("" + tabmet.totalDatos("Pacientes"));
        jLabel40.setText("" + tabmet.totalDatos("Solicitantes"));
        jLabel44.setText("" + tabmet.totalDatos("Hospitales"));
        jLabel41.setText("" + tabmet.totalDatos("Donadores"));
        jLabel42.setText("" + tabmet.totalDatos("Citas"));
        jTabbedPane1.setSelectedIndex(0);
        menuInactivo();
        menuActivo(TableroLabel);
    }//GEN-LAST:event_TableroLabelMouseClicked

    private void CitasLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CitasLabelMouseClicked
        LimpiarTable();
        page.setText("1");
        ListarCita(txtBuscarCitas.getText(), Integer.parseInt(page.getText()), porPagina);
        LimpiarCita();
        jTabbedPane1.setSelectedIndex(6);
        menuInactivo();
        menuActivo(CitasLabel);
    }//GEN-LAST:event_CitasLabelMouseClicked

    private void PacientesLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PacientesLabelMouseClicked
        LimpiarTable();
        page.setText("1");
        ListarPaciente(txtBuscarPacientes.getText(), Integer.parseInt(page.getText()), porPagina);
        LimpiarPaciente();
        jTabbedPane1.setSelectedIndex(3);
        menuInactivo();
        menuActivo(PacientesLabel);        
    }//GEN-LAST:event_PacientesLabelMouseClicked

    private void DonadoresLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DonadoresLabelMouseClicked
        LimpiarTable();
        page.setText("1");
        ListarDonante(txtBuscarDonante.getText(), Integer.parseInt(page.getText()), porPagina);
        LimpiarDonante();
        jTabbedPane1.setSelectedIndex(4);
        menuInactivo();
        menuActivo(DonadoresLabel);
    }//GEN-LAST:event_DonadoresLabelMouseClicked

    private void SolicitantesLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SolicitantesLabelMouseClicked
        LimpiarTable();
        page.setText("1");
        ListarSolicitante(txtBuscarSolicitante.getText(), Integer.parseInt(page.getText()), porPagina);
        LimpiarSolicitante();
        jTabbedPane1.setSelectedIndex(5);
        menuInactivo();
        menuActivo(SolicitantesLabel);
    }//GEN-LAST:event_SolicitantesLabelMouseClicked

    private void txtIdDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdDonActionPerformed

    private void txtBuscarDonanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarDonanteActionPerformed
        txtBuscarDonanteActionPerformed(evt);
    }//GEN-LAST:event_txtBuscarDonanteActionPerformed

    private void btnSaveDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveDonActionPerformed
            if(!"".equals(txtNomDon.getText()) && !"".equals(txtApellDon.getText()) && !"".equals(txtCorrDon.getText()) && !"".equals(txtSexoDon.getText()) && !"".equals(txtColDon.getText()) && !"".equals(txtTelDon.getText()) && !"".equals(txtModifiDon.getText()) && !"".equals(txtTipoDon.getText()) && !"".equals(txtUltimaDon.getText()) && !"".equals(txtFecRegDon)){
            donadoresModelo don = new donadoresModelo();
            don.setNombre(txtNomDon.getText());
            don.setApellido(txtApellDon.getText());
            don.setCorreo(txtCorrDon.getText());
            don.setSexo(txtSexoDon.getText());
            don.setColonia(txtColDon.getText());
            don.setTelefono(txtTelDon.getText());
            don.setModiCorpo(txtModifiDon.getText());
            don.setTipoSangre(txtTipoDon.getText());
            don.setUltimaDon(txtUltimaDon.getText());
            don.setFechaRegistro(txtFecRegDon.getText());
            if(txtIdDon.getText().length() > 0){
                don.setId(Integer.parseInt(txtIdDon.getText()));
                String respuesta = donmet.modificar(don);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "EL CORREO DEL USUARIO YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO MODIFICADO");
                        LimpiarDonante();
                        LimpiarTable();
                        ListarDonante(txtBuscarDonante.getText(), Integer.parseInt(page.getText()), porPagina);
                     }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
            }
        } else {
                String respuesta = donmet.registrar(don);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "CORREO DEL USUARIO YA EXISTENTE");
                    case "registrado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO");
                        LimpiarDonante();
                        LimpiarTable();
                        ListarDonante(txtBuscarDonante.getText(), Integer.parseInt(page.getText()), porPagina);
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR USUARIO");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnSaveDonActionPerformed

    private void btnNewDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewDonActionPerformed
        LimpiarDonante();
    }//GEN-LAST:event_btnNewDonActionPerformed

    private void HospitalesLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HospitalesLabelMouseClicked
        LimpiarTable();
        page.setText("1");
        ListarHospital(txtBuscarHospitales.getText(), Integer.parseInt(page.getText()), porPagina);
        LimpiarHospital();
        jTabbedPane1.setSelectedIndex(2);
        menuInactivo();
        menuActivo(HospitalesLabel);
    }//GEN-LAST:event_HospitalesLabelMouseClicked

    private void txtNomHospActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomHospActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomHospActionPerformed

    private void txtDirecHospActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirecHospActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDirecHospActionPerformed

    private void btnSaveHospActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveHospActionPerformed
           if(!"".equals(txtNomHosp.getText()) && !"".equals(txtDirecHosp.getText()) && !"".equals(txtColoHosp.getText()) && !"".equals(txtCorrHosp.getText()) && !"".equals(txtTelHosp.getText())){
            hospitalesModelo hos = new hospitalesModelo();
            hos.setNombre(txtNomHosp.getText());
            hos.setDireccion(txtDirecHosp.getText());
            hos.setColonia(txtColoHosp.getText());
            hos.setCorreo(txtCorrHosp.getText());
            hos.setTelefono(txtTelHosp.getText());
            if(txtIdHosp.getText().length() > 0){
                hos.setId(Integer.parseInt(txtIdHosp.getText()));
                String respuesta = hosmet.modificar(hos);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "EL CORREO DEL USUARIO YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO MODIFICADO");
                        LimpiarHospital();
                        LimpiarTable();
                        ListarHospital(txtBuscarHospitales.getText(), Integer.parseInt(page.getText()), porPagina);
                     }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
            }
        } else {
                String respuesta = hosmet.registrar(hos);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "CORREO DEL USUARIO YA EXISTENTE");
                    case "registrado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO");
                        LimpiarHospital();
                        LimpiarTable();
                        ListarHospital(txtBuscarHospitales.getText(), Integer.parseInt(page.getText()), porPagina);
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR USUARIO");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnSaveHospActionPerformed

    private void btnNewHospActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewHospActionPerformed
        LimpiarHospital();
    }//GEN-LAST:event_btnNewHospActionPerformed

    private void txtBuscarHospitalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarHospitalesActionPerformed
        txtBuscarHospitalesActionPerformed(evt);
    }//GEN-LAST:event_txtBuscarHospitalesActionPerformed

    private void txtBuscarHospitalesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarHospitalesKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarHospital(txtBuscarHospitales.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarHospitalesKeyReleased

    private void txtNomPacieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPacieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomPacieActionPerformed

    private void txtApellPacieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellPacieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellPacieActionPerformed

    private void txtBuscarPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarPacientesActionPerformed
        txtBuscarPacientesActionPerformed(evt);
    }//GEN-LAST:event_txtBuscarPacientesActionPerformed

    private void txtBuscarPacientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPacientesKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarPaciente(txtBuscarPacientes.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarPacientesKeyReleased

    private void txtIdHosCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHosCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHosCitaActionPerformed

    private void txtIdSolCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdSolCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdSolCitaActionPerformed

    private void btnSaveCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveCitasActionPerformed
            if(!"".equals(txtIdHosCita.getText()) && !"".equals(txtIdSolCita.getText()) && !"".equals(txtIdDonCita.getText()) && !"".equals(txtFechaCita.getText()) && !"".equals(txtStatusCita.getText())){
            citasModelo citas = new citasModelo();
            citas.setIdHospital(Integer.parseInt(txtIdHosCita.getText()));
            citas.setIdSolicitante(Integer.parseInt(txtIdSolCita.getText()));
            citas.setIdDonante(Integer.parseInt(txtIdDonCita.getText()));
            citas.setFechaCita(txtFechaCita.getText());
            citas.setStatusCita(txtStatusCita.getText());
            if(txtIdCita.getText().length() > 0){
                citas.setId(Integer.parseInt(txtIdCita.getText()));
                String respuesta = citmet.modificar(citas);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "EL CORREO DEL USUARIO YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO MODIFICADO");
                        LimpiarCita();
                        LimpiarTable();
                        ListarCita(txtBuscarCitas.getText(), Integer.parseInt(page.getText()), porPagina);
                     }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
            }
        } else {
                String respuesta = citmet.registrar(citas);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "CORREO DEL USUARIO YA EXISTENTE");
                    case "registrado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO");
                        LimpiarCita();
                        LimpiarTable();
                        ListarCita(txtBuscarCitas.getText(), Integer.parseInt(page.getText()), porPagina);
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR USUARIO");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnSaveCitasActionPerformed

    private void btnNewCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewCitasActionPerformed
        LimpiarCita();
    }//GEN-LAST:event_btnNewCitasActionPerformed

    private void txtBuscarCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarCitasActionPerformed
        txtBuscarCitasActionPerformed(evt);
    }//GEN-LAST:event_txtBuscarCitasActionPerformed

    private void txtBuscarCitasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCitasKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarCita(txtBuscarCitas.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarCitasKeyReleased

    private void txtIdHospPacieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHospPacieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHospPacieActionPerformed

    private void btnSavePacieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePacieActionPerformed
            if(!"".equals(txtIdHospPacie.getText()) && !"".equals(txtNomPacie.getText()) && !"".equals(txtApellPacie.getText()) && !"".equals(txtSexoPacie.getText()) && !"".equals(txtDirePacie.getText()) && !"".equals(txtColoPacie.getText()) && !"".equals(txtTipoPacie.getText()) && !"".equals(txtTelPacie.getText()) && !"".equals(txtFechRePacie.getText())){
            pacientesModelo pac = new pacientesModelo();
            pac.setIdHos(Integer.parseInt(txtIdHospPacie.getText()));
            pac.setNombre(txtNomPacie.getText());
            pac.setApellido(txtApellPacie.getText());
            pac.setSexo(txtSexoPacie.getText());
            pac.setDireccion(txtDirePacie.getText());
            pac.setColonia(txtColoPacie.getText());
            pac.setTipoSangre(txtTipoPacie.getText());
            pac.setTelefono(txtTelPacie.getText());
            pac.setFechaRegistro(txtFechRePacie.getText());
            if(txtIdPacie.getText().length() > 0){
                pac.setId(Integer.parseInt(txtIdPacie.getText()));
                String respuesta = pacmet.modificar(pac);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "EL CORREO DEL USUARIO YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO MODIFICADO");
                        LimpiarPaciente();
                        LimpiarTable();
                        ListarPaciente(txtBuscarPacientes.getText(), Integer.parseInt(page.getText()), porPagina);
                     }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
            }
        } else {
                String respuesta = pacmet.registrar(pac);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "CORREO DEL USUARIO YA EXISTENTE");
                    case "registrado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO");
                        LimpiarPaciente();
                        LimpiarTable();
                        ListarPaciente(txtBuscarPacientes.getText(), Integer.parseInt(page.getText()), porPagina);
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR USUARIO");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnSavePacieActionPerformed

    private void btnNewPacieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPacieActionPerformed
        LimpiarPaciente();
    }//GEN-LAST:event_btnNewPacieActionPerformed

    private void txtNomSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomSoliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomSoliActionPerformed

    private void txtApellSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellSoliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellSoliActionPerformed

    private void txtIdHospSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHospSoliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHospSoliActionPerformed

    private void btnSaveSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveSoliActionPerformed
                 if(!"".equals(txtIdHospSoli.getText()) && !"".equals(txtNomSoli.getText()) && !"".equals(txtApellSoli.getText()) && !"".equals(txtSexoSoli.getText()) && !"".equals(txtDireSoli.getText()) && !"".equals(txtColoSoli.getText()) && !"".equals(txtTipoSoli.getText()) && !"".equals(txtTelSoli.getText()) && !"".equals(txtFechReSoli.getText())){
            solicitantesModelo sol = new solicitantesModelo();
            sol.setIdHos(Integer.parseInt(txtIdHospSoli.getText()));
            sol.setNombre(txtNomSoli.getText());
            sol.setApellido(txtApellSoli.getText());
            sol.setSexo(txtSexoSoli.getText());
            sol.setDireccion(txtDireSoli.getText());
            sol.setColonia(txtColoSoli.getText());
            sol.setTipoSangre(txtTipoSoli.getText());
            sol.setTelefono(txtTelSoli.getText());
            sol.setFechaRegistro(txtFechReSoli.getText());
            if(txtIdSoli.getText().length() > 0){
                sol.setId(Integer.parseInt(txtIdSoli.getText()));
                String respuesta = solimet.modificar(sol);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "EL CORREO DEL USUARIO YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO MODIFICADO");
                        LimpiarSolicitante();
                        LimpiarTable();
                        ListarSolicitante(txtBuscarSolicitante.getText(), Integer.parseInt(page.getText()), porPagina);
                     }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
            }
        } else {
                String respuesta = solimet.registrar(sol);
                switch(respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "CORREO DEL USUARIO YA EXISTENTE");
                    case "registrado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO");
                        LimpiarSolicitante();
                        LimpiarTable();
                        ListarSolicitante(txtBuscarSolicitante.getText(), Integer.parseInt(page.getText()), porPagina);
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR USUARIO");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnSaveSoliActionPerformed

    private void btnNewSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSoliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNewSoliActionPerformed

    private void txtBuscarSolicitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarSolicitanteActionPerformed
        txtBuscarSolicitanteActionPerformed(evt);
    }//GEN-LAST:event_txtBuscarSolicitanteActionPerformed

    private void txtBuscarSolicitanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarSolicitanteKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarSolicitante(txtBuscarSolicitante.getText(), Integer.parseInt(page.getText()), porPagina);
        
    }//GEN-LAST:event_txtBuscarSolicitanteKeyReleased

    private void txtColoSoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtColoSoliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtColoSoliActionPerformed

    private void txtBuscarDonanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarDonanteKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarDonante(txtBuscarDonante.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarDonanteKeyReleased

    private void eliminarHospitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarHospitalActionPerformed
        if(tblHospitales.getSelectedRow() >= 0) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar", "ADVERTENCIA", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (pregunta == 0 ){
            txtIdHosp.setText(tblHospitales.getValueAt(tblHospitales.getSelectedRow(), 0).toString());
            int id = Integer.parseInt(txtIdHosp.getText());
            hosmet.eliminar(id);
            LimpiarTable();
            LimpiarHospital();
            ListarHospital(txtBuscarHospitales.getText(), Integer.parseInt(page.getText()), porPagina);
                } 
        } else {
                JOptionPane.showMessageDialog(null, "SELECCIONA UN HOSPITAL");
            }
    }//GEN-LAST:event_eliminarHospitalActionPerformed

    private void editarHospitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarHospitalActionPerformed
          if(tblHospitales.getSelectedRow() >= 0){
            txtIdHosp.setText(tblHospitales.getValueAt(tblHospitales.getSelectedRow(), 0).toString());
            txtNomHosp.setText(tblHospitales.getValueAt(tblHospitales.getSelectedRow(), 1).toString());
            txtDirecHosp.setText(tblHospitales.getValueAt(tblHospitales.getSelectedRow(), 2).toString());
            txtColoHosp.setText(tblHospitales.getValueAt(tblHospitales.getSelectedRow(), 3).toString());
            txtCorrHosp.setText(tblHospitales.getValueAt(tblHospitales.getSelectedRow(), 4).toString());
            txtTelHosp.setText(tblHospitales.getValueAt(tblHospitales.getSelectedRow(), 5).toString());
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
        } 
    }//GEN-LAST:event_editarHospitalActionPerformed

    private void eliminarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarPacienteActionPerformed
            if(tblPacientes.getSelectedRow() >= 0) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar", "ADVERTENCIA", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (pregunta == 0 ){
            txtIdPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 0).toString());
            int id = Integer.parseInt(txtIdPacie.getText());
            pacmet.eliminar(id);
            LimpiarTable();
            LimpiarPaciente();
            ListarPaciente(txtBuscarPacientes.getText(), Integer.parseInt(page.getText()), porPagina);
                } 
        } else {
                JOptionPane.showMessageDialog(null, "SELECCIONA UN HOSPITAL");
            }
    }//GEN-LAST:event_eliminarPacienteActionPerformed

    private void editarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarPacienteActionPerformed
        String idPac = tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 0).toString();
        if(tblPacientes.getSelectedRow() >= 0){
            txtIdPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 0).toString());
            String idHospital = pacmet.obtenerIdHospitalDeSoli(idPac);
            if (idHospital != null) {
            // Asignar el ID del hospital al campo de texto correspondiente
            txtIdHospPacie.setText(idHospital);
            
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo obtener el ID del hospital");
        }
            txtNomPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 2).toString());
            txtApellPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 3).toString());
            txtSexoPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 4).toString());
            txtDirePacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 5).toString());
            txtColoPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 6).toString());
            txtTipoPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 7).toString());
            txtTelPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 8).toString());
            txtFechRePacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 9).toString());
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
        } 
    }//GEN-LAST:event_editarPacienteActionPerformed

    private void eliminarDonadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarDonadoresActionPerformed
            if(tblDonantes.getSelectedRow() >= 0) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar", "ADVERTENCIA", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (pregunta == 0 ){
            txtIdDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 0).toString());
            int id = Integer.parseInt(txtIdDon.getText());
            donmet.eliminar(id);
            LimpiarTable();
            LimpiarDonante();
            ListarDonante(txtBuscarDonante.getText(), Integer.parseInt(page.getText()), porPagina);
                } 
        } else {
                JOptionPane.showMessageDialog(null, "SELECCIONA UN DONANTE");
            }
    }//GEN-LAST:event_eliminarDonadoresActionPerformed

    private void editarDonadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarDonadoresActionPerformed
        if (tblDonantes.getSelectedRow() >= 0) {
        txtIdDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 0).toString());
        txtNomDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 1).toString());
        txtApellDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 2).toString());
        txtCorrDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 3).toString());
        txtSexoDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 4).toString());
        txtColDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 5).toString());
        txtTelDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 6).toString());
        txtModifiDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 7).toString());
        txtTipoDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 8).toString());
        txtUltimaDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 9).toString());
        txtFecRegDon.setText(tblDonantes.getValueAt(tblDonantes.getSelectedRow(), 10).toString());
    } else {
        JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
    }
    }//GEN-LAST:event_editarDonadoresActionPerformed

    private void eliminarSolicitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarSolicitanteActionPerformed
            if(tblSolicitantes.getSelectedRow() >= 0) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar", "ADVERTENCIA", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (pregunta == 0 ){
            txtIdSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 0).toString());
            int id = Integer.parseInt(txtIdSoli.getText());
            solimet.eliminar(id);
            LimpiarTable();
            LimpiarSolicitante();
            ListarSolicitante(txtBuscarSolicitante.getText(), Integer.parseInt(page.getText()), porPagina);
                } 
        } else {
                JOptionPane.showMessageDialog(null, "SELECCIONA UN HOSPITAL");
            }
    }//GEN-LAST:event_eliminarSolicitanteActionPerformed

    private void editarSolicitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarSolicitanteActionPerformed
         String idSoli = tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 0).toString();
        if(tblSolicitantes.getSelectedRow() >= 0){
            txtIdSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 0).toString());
            String idHospital = solimet.obtenerIdHospitalDeSoli(idSoli);
            if (idHospital != null) {
            // Asignar el ID del hospital al campo de texto correspondiente
            txtIdHospSoli.setText(idHospital);
            
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo obtener el ID del hospital");
        }
            txtNomSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 2).toString());
            txtApellSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 3).toString());
            txtSexoSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 4).toString());
            txtDireSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 5).toString());
            txtColoSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 6).toString());
            txtTipoSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 7).toString());
            txtTelSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 8).toString());
            txtFechReSoli.setText(tblSolicitantes.getValueAt(tblSolicitantes.getSelectedRow(), 9).toString());
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
        } 
    }//GEN-LAST:event_editarSolicitanteActionPerformed

    private void eliminarCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarCitasActionPerformed
         if(tblCitas.getSelectedRow() >= 0) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar", "ADVERTENCIA", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (pregunta == 0 ){
            txtIdCita.setText(tblCitas.getValueAt(tblCitas.getSelectedRow(), 0).toString());
            int id = Integer.parseInt(txtIdCita.getText());
            citmet.eliminar(id);
            LimpiarTable();
            LimpiarCita();
            ListarCita(txtBuscarCitas.getText(), Integer.parseInt(page.getText()), porPagina);
                } 
        } else {
                JOptionPane.showMessageDialog(null, "SELECCIONA UN HOSPITAL");
            }
    }//GEN-LAST:event_eliminarCitasActionPerformed

    private void editarCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarCitasActionPerformed
        String idCita = tblCitas.getValueAt(tblCitas.getSelectedRow(), 0).toString();
        if(tblCitas.getSelectedRow() >= 0){
            txtIdCita.setText(tblCitas.getValueAt(tblCitas.getSelectedRow(), 0).toString());
            String idHospital = citmet.obtenerIdHospitalDeCita(idCita);
            String idSolicitante = citmet.obtenerIdSolicitanteDeCita(idCita);
            String idDonante = citmet.obtenerIdDonadorDeCita(idCita);
        if (idHospital != null) {
            // Asignar el ID del hospital al campo de texto correspondiente
            txtIdHosCita.setText(idHospital);
            txtIdSolCita.setText(idSolicitante);
            txtIdDonCita.setText(idDonante);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo obtener el ID del hospital");
        }
            txtFechaCita.setText(tblCitas.getValueAt(tblCitas.getSelectedRow(), 7).toString());
            txtStatusCita.setText(tblCitas.getValueAt(tblCitas.getSelectedRow(), 8).toString());
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONA UNA FILA");
        } 
    }//GEN-LAST:event_editarCitasActionPerformed

    private void notificarDonadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificarDonadoresActionPerformed
        String contraseña = "kgewkvfwpkyyovzb";
    }//GEN-LAST:event_notificarDonadoresActionPerformed

    private void btSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalirActionPerformed
        frmInicio reg = new frmInicio();
        dispose();
        reg.setVisible(true);
    }//GEN-LAST:event_btSalirActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmAdministrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CitasLabel;
    private javax.swing.JLabel DonadoresLabel;
    private javax.swing.JLabel EtReloj;
    private javax.swing.JLabel HospitalesLabel;
    private javax.swing.JLabel PacientesLabel;
    private javax.swing.JLabel SolicitantesLabel;
    private javax.swing.JLabel TableroLabel;
    private javax.swing.JLabel UsuariosLabel;
    private javax.swing.JButton btSalir;
    private javax.swing.JButton btnAntCitas;
    private javax.swing.JButton btnAntDon;
    private javax.swing.JButton btnAntHosp;
    private javax.swing.JButton btnAntPacie;
    private javax.swing.JButton btnAntSoli;
    private javax.swing.JButton btnAntUsua;
    private javax.swing.JButton btnNewCitas;
    private javax.swing.JButton btnNewDon;
    private javax.swing.JButton btnNewHosp;
    private javax.swing.JButton btnNewPacie;
    private javax.swing.JButton btnNewSoli;
    private javax.swing.JButton btnNewUsua;
    private javax.swing.JButton btnSaveCitas;
    private javax.swing.JButton btnSaveDon;
    private javax.swing.JButton btnSaveHosp;
    private javax.swing.JButton btnSavePacie;
    private javax.swing.JButton btnSaveSoli;
    private javax.swing.JButton btnSaveUsua;
    private javax.swing.JButton btnSigCitas;
    private javax.swing.JButton btnSigDon;
    private javax.swing.JButton btnSigHosp;
    private javax.swing.JButton btnSigPacie;
    private javax.swing.JButton btnSigSoli;
    private javax.swing.JButton btnSigUsua;
    private javax.swing.JMenuItem editarCitas;
    private javax.swing.JMenuItem editarDonadores;
    private javax.swing.JMenuItem editarHospital;
    private javax.swing.JMenuItem editarPaciente;
    private javax.swing.JMenuItem editarSolicitante;
    private javax.swing.JMenuItem editarUsuario;
    private javax.swing.JMenuItem eliminarCitas;
    private javax.swing.JMenuItem eliminarDonadores;
    private javax.swing.JMenuItem eliminarHospital;
    private javax.swing.JMenuItem eliminarPaciente;
    private javax.swing.JMenuItem eliminarSolicitante;
    private javax.swing.JMenuItem eliminarUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JPopupMenu jPopupMenu5;
    private javax.swing.JPopupMenu jPopupMenu6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem notificarDonadores;
    private javax.swing.JTextField page;
    private javax.swing.JLabel pageCitas;
    private javax.swing.JLabel pageDonantes;
    private javax.swing.JLabel pageHospitales;
    private javax.swing.JLabel pagePacientes;
    private javax.swing.JLabel pageSolicitantes;
    private javax.swing.JLabel pageUsuarios;
    private javax.swing.JTable tblCitas;
    private javax.swing.JTable tblDonantes;
    private javax.swing.JTable tblHospitales;
    private javax.swing.JTable tblPacientes;
    private javax.swing.JTable tblSolicitantes;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtApellDon;
    private javax.swing.JTextField txtApellPacie;
    private javax.swing.JTextField txtApellSoli;
    private javax.swing.JTextField txtApellUsuario;
    private javax.swing.JTextField txtBuscarCitas;
    private javax.swing.JTextField txtBuscarDonante;
    private javax.swing.JTextField txtBuscarHospitales;
    private javax.swing.JTextField txtBuscarPacientes;
    private javax.swing.JTextField txtBuscarSolicitante;
    private javax.swing.JTextField txtBuscarUsuarios;
    private javax.swing.JTextField txtClaUsua;
    private javax.swing.JTextField txtColDon;
    private javax.swing.JTextField txtColoHosp;
    private javax.swing.JTextField txtColoPacie;
    private javax.swing.JTextField txtColoSoli;
    private javax.swing.JTextField txtCorrDon;
    private javax.swing.JTextField txtCorrHosp;
    private javax.swing.JTextField txtCorrUsua;
    private javax.swing.JTextField txtDirePacie;
    private javax.swing.JTextField txtDireSoli;
    private javax.swing.JTextField txtDireUsua;
    private javax.swing.JTextField txtDirecHosp;
    private javax.swing.JTextField txtFecRegDon;
    private javax.swing.JTextField txtFechRePacie;
    private javax.swing.JTextField txtFechReSoli;
    private javax.swing.JTextField txtFechaCita;
    private javax.swing.JTextField txtIdCita;
    private javax.swing.JTextField txtIdDon;
    private javax.swing.JTextField txtIdDonCita;
    private javax.swing.JTextField txtIdHosCita;
    private javax.swing.JTextField txtIdHosp;
    private javax.swing.JTextField txtIdHospPacie;
    private javax.swing.JTextField txtIdHospSoli;
    private javax.swing.JTextField txtIdPacie;
    private javax.swing.JTextField txtIdSolCita;
    private javax.swing.JTextField txtIdSoli;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JTextField txtIdUsua;
    private javax.swing.JTextField txtModifiDon;
    private javax.swing.JTextField txtNomDon;
    private javax.swing.JTextField txtNomHosp;
    private javax.swing.JTextField txtNomPacie;
    private javax.swing.JTextField txtNomSoli;
    private javax.swing.JTextField txtNomUsua;
    private javax.swing.JTextField txtSexoDon;
    private javax.swing.JTextField txtSexoPacie;
    private javax.swing.JTextField txtSexoSoli;
    private javax.swing.JTextField txtStatusCita;
    private javax.swing.JTextField txtTelDon;
    private javax.swing.JTextField txtTelHosp;
    private javax.swing.JTextField txtTelPacie;
    private javax.swing.JTextField txtTelSoli;
    private javax.swing.JTextField txtTipoDon;
    private javax.swing.JTextField txtTipoPacie;
    private javax.swing.JTextField txtTipoSoli;
    private javax.swing.JTextField txtUltimaDon;
    // End of variables declaration//GEN-END:variables
   
    
    private void colorTable(JTable tabla) {
        JTableHeader header = tabla.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 0, 0));
        header.setForeground(Color.white);
    }

    private void pagination(JButton anterior, JButton siguiente, JLabel info, int pagina, int totalPage) {
        anterior.setEnabled(true);
        siguiente.setEnabled(true);
        if(pagina <= 1) {
            anterior.setEnabled(false);
        }
        if(pagina >= totalPage ){
            siguiente.setEnabled(false);
        }
        info.setText("Página " + pagina + " de un Total " + totalPage + " Páginas");
    }

    private void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
                modelo.removeRow(i);
                i = i - 1;
        }
    }

    private void limpiarUsuario() {
        txtIdUsua.setText("");
        txtNomUsua.setText("");
        txtApellUsuario.setText("");
        txtCorrUsua.setText("");
        txtDireUsua.setText("");
        txtClaUsua.setText("");
    }

    private void LimpiarHospital() {
        txtIdHosp.setText("");
        txtNomHosp.setText("");
        txtDirecHosp.setText("");
        txtColoHosp.setText("");
        txtCorrHosp.setText("");
        txtCorrHosp.setText("");
        txtTelHosp.setText("");
    }
    
    private void LimpiarDonante() {
        txtIdDon.setText("");
        txtNomDon.setText("");
        txtApellDon.setText("");
        txtCorrDon.setText("");
        txtSexoDon.setText("");
        txtColDon.setText("");
        txtTelDon.setText("");
        txtModifiDon.setText("");
        txtTipoDon.setText("");
        txtUltimaDon.setText("");
        txtFecRegDon.setText("");
    }
    
    private void LimpiarPaciente() {
         txtIdPacie.setText("");
         txtIdHospPacie.setText("");
         txtNomPacie.setText("");
         txtApellPacie.setText("");
         txtSexoPacie.setText("");
         txtDirePacie.setText("");
         txtColoPacie.setText("");
         txtTipoPacie.setText("");
         txtTelPacie.setText("");
         txtFechRePacie.setText("");
    }

    private void LimpiarSolicitante() {
         txtIdSoli.setText("");
         txtIdHospSoli.setText("");
         txtApellSoli.setText("");
         txtSexoSoli.setText("");
         txtDireSoli.setText("");
         txtColoSoli.setText("");
         txtTipoSoli.setText("");
         txtTelSoli.setText("");
         txtFechReSoli.setText("");
    }
    
    private void LimpiarCita(){
        txtIdCita.setText("");
        txtIdHosCita.setText("");
        txtIdSolCita.setText("");
        txtIdDonCita.setText("");
        txtFechaCita.setText("");
        txtStatusCita.setText("");
    }
    
    
    private void menuInactivo() {
        UsuariosLabel.setForeground(new Color(0,0,0));
        TableroLabel.setForeground(new Color(0,0,0));
        DonadoresLabel.setForeground(new Color(0,0,0));
        HospitalesLabel.setForeground(new Color(0,0,0));
        PacientesLabel.setForeground(new Color(0,0,0));
        CitasLabel.setForeground(new Color(0,0,0));
        SolicitantesLabel.setForeground(new Color(0,0,0));
        
        
        TableroLabel.setEnabled(true);
        UsuariosLabel.setEnabled(true);
        DonadoresLabel.setEnabled(true);
        HospitalesLabel.setEnabled(true);
        PacientesLabel.setEnabled(true);
        CitasLabel.setEnabled(true);
        SolicitantesLabel.setEnabled(true);
    }

    private void menuActivo(JLabel label){
    label.setForeground(new Color(102,52,255));
    label.setEnabled(false);
}

}