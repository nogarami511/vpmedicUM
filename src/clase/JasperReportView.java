/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.BorderPane;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author alfar
 */
public class JasperReportView extends BorderPane{
    private JasperPrint jasperPrint;

    public JasperReportView(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
        initUI();
    }

    private void initUI() {
        SwingNode swingNode = new SwingNode();
        JRViewer jrViewer = new JRViewer(jasperPrint);
        swingNode.setContent(jrViewer);
        
        setCenter(swingNode);
    }
}
