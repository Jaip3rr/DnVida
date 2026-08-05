package modelos;

import conexionDB.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class TableroMET {
    
    conexionDB.Conexion cn = new Conexion();
    ResultSet rs;
    Connection con = cn.conectando();
    
    
    public void reporteGraficoPacie(JPanel panel) {
    try {
        String sql = "SELECT tipoSangre, COUNT(*) AS cantidad FROM Pacientes GROUP BY tipoSangre";
        PreparedStatement ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        while (rs.next()) {
            String tipoSangre = rs.getString("tipoSangre");
            int cantidad = rs.getInt("cantidad");
            ds.addValue(cantidad, "Cantidad", tipoSangre);
        }
        JFreeChart jf = ChartFactory.createBarChart3D("Cantidad de Pacientes por Tipo de Sangre", "Tipo de Sangre", "Cantidad", ds, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel f = new ChartPanel(jf);
        f.setSize(330, 290);
        panel.add(f);
    } catch (SQLException e) {
        System.out.println(e.toString());
    }
}
    
      public void reporteGraficoDona(JPanel panel) {
        try {
        String sql = "SELECT tipoSangre, COUNT(*) AS cantidad FROM Donadores GROUP BY tipoSangre";
        PreparedStatement ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        while (rs.next()) {
            String tipoSangre = rs.getString("tipoSangre");
            int cantidad = rs.getInt("cantidad");
            ds.addValue(cantidad, "Cantidad", tipoSangre);
        }
        JFreeChart jf = ChartFactory.createBarChart3D("Cantidad de Donadores por Tipo de Sangre", "Tipo de Sangre", "Cantidad", ds, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel f = new ChartPanel(jf);
        f.setSize(330, 290);
        panel.add(f);
    } catch (SQLException e) {
        System.out.println(e.toString());
    }  
    }

    public void reporteGraficoSoli(JPanel panel) {
        try {
        String sql = "SELECT tipoSangre, COUNT(*) AS cantidad FROM Solicitantes GROUP BY tipoSangre";
        PreparedStatement ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        while (rs.next()) {
            String tipoSangre = rs.getString("tipoSangre");
            int cantidad = rs.getInt("cantidad");
            ds.addValue(cantidad, "Cantidad", tipoSangre);
        }
        JFreeChart jf = ChartFactory.createBarChart3D("Cantidad de Solicitantes por Tipo de Sangre", "Tipo de Sangre", "Cantidad", ds, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel f = new ChartPanel(jf);
        f.setSize(330, 290);
        panel.add(f);
    } catch (SQLException e) {
        System.out.println(e.toString());
    }  
    }
    
     public int totalDatos(String table) {
        double total = 0;
        String sql = "SELECT COUNT(*) AS total FROM " + table;
        try {
            PreparedStatement ps = con.prepareStatement(sql);        
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return (int) total;
    }

  
}