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


public class frmNavegante extends javax.swing.JFrame {

    DefaultTableModel modelo = new DefaultTableModel();
    
    usuariosMET usuamet = new usuariosMET();
    donadoresMET donmet = new donadoresMET();
    hospitalesMET hosmet = new hospitalesMET();
    pacientesMET pacmet = new pacientesMET();
    solicitantesMET solimet = new solicitantesMET();
    citasMET citmet = new citasMET();
            
            
    TableroMET tabmet = new TableroMET();
    
            
    
    
    
    
    String porPagina = "25";
    
    public frmNavegante(usuariosModelo usua) {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
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
        txtIdPacie.setVisible(false);
        
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

    private frmNavegante() {

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
        notificarDonadores = new javax.swing.JMenuItem();
        jPopupMenu5 = new javax.swing.JPopupMenu();
        eliminarSolicitante = new javax.swing.JMenuItem();
        editarSolicitante = new javax.swing.JMenuItem();
        jPopupMenu6 = new javax.swing.JPopupMenu();
        eliminarCitas = new javax.swing.JMenuItem();
        editarCitas = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        TableroLabel = new javax.swing.JLabel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        pageUsuarios = new javax.swing.JLabel();
        btnSigUsua = new javax.swing.JButton();
        btnAntUsua = new javax.swing.JButton();
        txtBuscarUsuarios = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDonantes = new javax.swing.JTable();
        txtBuscarDonante = new javax.swing.JTextField();
        pageDonantes = new javax.swing.JLabel();
        btnAntDon = new javax.swing.JButton();
        btnSigDon = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSolicitantes = new javax.swing.JTable();
        pageSolicitantes = new javax.swing.JLabel();
        btnSigSoli = new javax.swing.JButton();
        btnAntSoli = new javax.swing.JButton();
        txtBuscarSolicitante = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
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

        TableroLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        TableroLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\tablero.png")); // NOI18N
        TableroLabel.setText(" Agendar");
        TableroLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableroLabelMouseClicked(evt);
            }
        });
        jPanel1.add(TableroLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, 60));

        DonadoresLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        DonadoresLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\clientes.png")); // NOI18N
        DonadoresLabel.setText("Donadores");
        DonadoresLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DonadoresLabelMouseClicked(evt);
            }
        });
        jPanel1.add(DonadoresLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 140, 50));

        SolicitantesLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SolicitantesLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\clientes.png")); // NOI18N
        SolicitantesLabel.setText("Solicitantes");
        SolicitantesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SolicitantesLabelMouseClicked(evt);
            }
        });
        jPanel1.add(SolicitantesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 150, 60));

        HospitalesLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        HospitalesLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jadri\\Documents\\NetBeansProjects\\DNvida\\src\\main\\java\\imagenes\\nombre.png")); // NOI18N
        HospitalesLabel.setText("Hospitales");
        HospitalesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HospitalesLabelMouseClicked(evt);
            }
        });
        jPanel1.add(HospitalesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, 50));

        btSalir.setBackground(new java.awt.Color(153, 153, 255));
        btSalir.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btSalir.setText("Cerrar Sesion");
        btSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 120, -1));

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

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 330, 290));

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

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 370, 290));

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

        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 170, -1, -1));

        jTabbedPane1.addTab("tab2", jPanel9);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellido", "Correo", "Direccion", "Tipo"
            }
        ));
        jScrollPane1.setViewportView(tblUsuarios);
        if (tblUsuarios.getColumnModel().getColumnCount() > 0) {
            tblUsuarios.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1120, 390));

        pageUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pageUsuarios.setText("Página 1 de un total de 20 Paginas");
        jPanel4.add(pageUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 470, -1, -1));

        btnSigUsua.setText("Siguiente");
        jPanel4.add(btnSigUsua, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 470, -1, -1));

        btnAntUsua.setText("Anterior");
        jPanel4.add(btnAntUsua, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 470, -1, -1));

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
        jPanel4.add(txtBuscarUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 940, 30));

        jTabbedPane1.addTab("tab1", jPanel4);

        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHospitales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Direccion", "Colonia", "Correo", "Telefono"
            }
        ));
        jScrollPane6.setViewportView(tblHospitales);
        if (tblHospitales.getColumnModel().getColumnCount() > 0) {
            tblHospitales.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel16.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1120, 390));

        pageHospitales.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pageHospitales.setText("Página 1 de un total de 20 Paginas");
        jPanel16.add(pageHospitales, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, -1, -1));

        btnSigHosp.setText("Siguiente");
        jPanel16.add(btnSigHosp, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 470, -1, -1));

        btnAntHosp.setText("Anterior");
        jPanel16.add(btnAntHosp, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 470, -1, -1));

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
        jPanel16.add(txtBuscarHospitales, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 970, 30));

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

        jPanel6.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 450));

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

        tblDonantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "nombre", "apellido", "correo", "sexo", "colonia", "telefono", "modificacion Corporal", "tipo sangre", "ultima donacion", "fecha registro"
            }
        ));
        tblDonantes.setColumnSelectionAllowed(true);
        tblDonantes.setComponentPopupMenu(jPopupMenu4);
        jScrollPane2.setViewportView(tblDonantes);

        txtBuscarDonante.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
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
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(pageDonantes)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarDonante, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(btnAntDon)
                        .addGap(45, 45, 45)
                        .addComponent(btnSigDon)
                        .addContainerGap(280, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pageDonantes)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscarDonante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAntDon)
                    .addComponent(btnSigDon))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab3", jPanel7);

        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblSolicitantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre Hosp", "Nombre", "Apellido", "Sexo", "Direccion", "Colonia", "tipoSangre", "Telefono", "fechaRegistro"
            }
        ));
        jScrollPane7.setViewportView(tblSolicitantes);
        if (tblSolicitantes.getColumnModel().getColumnCount() > 0) {
            tblSolicitantes.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel18.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 1110, 390));

        pageSolicitantes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pageSolicitantes.setText("Página 1 de un total de 20 Paginas");
        jPanel18.add(pageSolicitantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        btnSigSoli.setText("Siguiente");
        jPanel18.add(btnSigSoli, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 470, -1, -1));

        btnAntSoli.setText("Anterior");
        jPanel18.add(btnAntSoli, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 470, -1, -1));

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
        jPanel18.add(txtBuscarSolicitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 1000, 30));

        jTabbedPane1.addTab("tab1", jPanel18);

        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre Hospital", "Nombre Solicitante", "Apellido Solicitante", "Nombre Donador", "Apelldo Donador", "Correo Donador", "Fecha Cita", "Status"
            }
        ));
        tblCitas.setComponentPopupMenu(jPopupMenu6);
        jScrollPane5.setViewportView(tblCitas);

        jPanel14.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1120, 380));

        pageCitas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pageCitas.setText("Página 1 de un total de 20 Paginas");
        jPanel14.add(pageCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, -1, -1));

        btnSigCitas.setText("Siguiente");
        jPanel14.add(btnSigCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 470, -1, -1));

        btnAntCitas.setText("Anterior");
        jPanel14.add(btnAntCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 470, -1, -1));

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
        jPanel14.add(txtBuscarCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 980, 30));

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

    private void eliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarUsuarioActionPerformed
    }//GEN-LAST:event_eliminarUsuarioActionPerformed

    private void editarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarUsuarioActionPerformed
    }//GEN-LAST:event_editarUsuarioActionPerformed

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

    private void DonadoresLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DonadoresLabelMouseClicked
        LimpiarTable();
        page.setText("1");
        ListarDonante(txtBuscarDonante.getText(), Integer.parseInt(page.getText()), porPagina);
        jTabbedPane1.setSelectedIndex(4);
        menuInactivo();
        menuActivo(DonadoresLabel);
    }//GEN-LAST:event_DonadoresLabelMouseClicked

    private void SolicitantesLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SolicitantesLabelMouseClicked
        LimpiarTable();
        page.setText("1");
        ListarSolicitante(txtBuscarSolicitante.getText(), Integer.parseInt(page.getText()), porPagina);
        jTabbedPane1.setSelectedIndex(5);
        menuInactivo();
        menuActivo(SolicitantesLabel);
    }//GEN-LAST:event_SolicitantesLabelMouseClicked

    private void txtBuscarDonanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarDonanteActionPerformed
        txtBuscarDonanteActionPerformed(evt);
    }//GEN-LAST:event_txtBuscarDonanteActionPerformed

    private void HospitalesLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HospitalesLabelMouseClicked
        LimpiarTable();
        page.setText("1");
        ListarHospital(txtBuscarHospitales.getText(), Integer.parseInt(page.getText()), porPagina);
        jTabbedPane1.setSelectedIndex(2);
        menuInactivo();
        menuActivo(HospitalesLabel);
    }//GEN-LAST:event_HospitalesLabelMouseClicked

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

    private void txtBuscarSolicitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarSolicitanteActionPerformed
        txtBuscarSolicitanteActionPerformed(evt);
    }//GEN-LAST:event_txtBuscarSolicitanteActionPerformed

    private void txtBuscarSolicitanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarSolicitanteKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarSolicitante(txtBuscarSolicitante.getText(), Integer.parseInt(page.getText()), porPagina);
        
    }//GEN-LAST:event_txtBuscarSolicitanteKeyReleased

    private void txtBuscarDonanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarDonanteKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarDonante(txtBuscarDonante.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarDonanteKeyReleased

    private void eliminarHospitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarHospitalActionPerformed
    }//GEN-LAST:event_eliminarHospitalActionPerformed

    private void editarHospitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarHospitalActionPerformed
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
        if(tblPacientes.getSelectedRow() >= 0){
            txtIdPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 0).toString());
            txtIdHospPacie.setText(tblPacientes.getValueAt(tblPacientes.getSelectedRow(), 1).toString());
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

    private void eliminarSolicitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarSolicitanteActionPerformed
    }//GEN-LAST:event_eliminarSolicitanteActionPerformed

    private void editarSolicitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarSolicitanteActionPerformed
    }//GEN-LAST:event_editarSolicitanteActionPerformed

    private void eliminarCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarCitasActionPerformed
       
    }//GEN-LAST:event_eliminarCitasActionPerformed

    private void editarCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarCitasActionPerformed
        
    }//GEN-LAST:event_editarCitasActionPerformed

    private void notificarDonadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificarDonadoresActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(frmNavegante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmNavegante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmNavegante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmNavegante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmNavegante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DonadoresLabel;
    private javax.swing.JLabel EtReloj;
    private javax.swing.JLabel HospitalesLabel;
    private javax.swing.JLabel SolicitantesLabel;
    private javax.swing.JLabel TableroLabel;
    private javax.swing.JButton btSalir;
    private javax.swing.JButton btnAntCitas;
    private javax.swing.JButton btnAntDon;
    private javax.swing.JButton btnAntHosp;
    private javax.swing.JButton btnAntPacie;
    private javax.swing.JButton btnAntSoli;
    private javax.swing.JButton btnAntUsua;
    private javax.swing.JButton btnNewPacie;
    private javax.swing.JButton btnSavePacie;
    private javax.swing.JButton btnSigCitas;
    private javax.swing.JButton btnSigDon;
    private javax.swing.JButton btnSigHosp;
    private javax.swing.JButton btnSigPacie;
    private javax.swing.JButton btnSigSoli;
    private javax.swing.JButton btnSigUsua;
    private javax.swing.JMenuItem editarCitas;
    private javax.swing.JMenuItem editarHospital;
    private javax.swing.JMenuItem editarPaciente;
    private javax.swing.JMenuItem editarSolicitante;
    private javax.swing.JMenuItem editarUsuario;
    private javax.swing.JMenuItem eliminarCitas;
    private javax.swing.JMenuItem eliminarHospital;
    private javax.swing.JMenuItem eliminarPaciente;
    private javax.swing.JMenuItem eliminarSolicitante;
    private javax.swing.JMenuItem eliminarUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JTextField txtApellPacie;
    private javax.swing.JTextField txtBuscarCitas;
    private javax.swing.JTextField txtBuscarDonante;
    private javax.swing.JTextField txtBuscarHospitales;
    private javax.swing.JTextField txtBuscarPacientes;
    private javax.swing.JTextField txtBuscarSolicitante;
    private javax.swing.JTextField txtBuscarUsuarios;
    private javax.swing.JTextField txtColoPacie;
    private javax.swing.JTextField txtDirePacie;
    private javax.swing.JTextField txtFechRePacie;
    private javax.swing.JTextField txtIdHospPacie;
    private javax.swing.JTextField txtIdPacie;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JTextField txtNomPacie;
    private javax.swing.JTextField txtSexoPacie;
    private javax.swing.JTextField txtTelPacie;
    private javax.swing.JTextField txtTipoPacie;
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

    
  
    
    
    private void menuInactivo() {
        TableroLabel.setForeground(new Color(0,0,0));
        DonadoresLabel.setForeground(new Color(0,0,0));
        HospitalesLabel.setForeground(new Color(0,0,0));
        SolicitantesLabel.setForeground(new Color(0,0,0));
        
        
        TableroLabel.setEnabled(true);
        DonadoresLabel.setEnabled(true);
        HospitalesLabel.setEnabled(true);
        SolicitantesLabel.setEnabled(true);
    }

    private void menuActivo(JLabel label){
    label.setForeground(new Color(102,52,255));
    label.setEnabled(false);
}

}