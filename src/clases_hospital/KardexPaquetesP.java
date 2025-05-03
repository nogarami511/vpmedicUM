/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

/**
 *
 * @author olver
 */
public class KardexPaquetesP extends PaquetesNvo {
    int id_kdxpaquetesmedico;
    double precio_anterior_kdxpaquetesmedico, precio_nvo_kdxpaquetesmedico;
    String usuario_modificacion, fecha_modificacion;

  

    public int getId_kdxpaquetesmedico() {
        return id_kdxpaquetesmedico;
    }

    public void setId_kdxpaquetesmedico(int id_kdxpaquetesmedico) {
        this.id_kdxpaquetesmedico = id_kdxpaquetesmedico;
    }

    public double getPrecio_anterior_kdxpaquetesmedico() {
        return precio_anterior_kdxpaquetesmedico;
    }

    public void setPrecio_anterior_kdxpaquetesmedico(double precio_anterior_kdxpaquetesmedico) {
        this.precio_anterior_kdxpaquetesmedico = precio_anterior_kdxpaquetesmedico;
    }

    public double getPrecio_nvo_kdxpaquetesmedico() {
        return precio_nvo_kdxpaquetesmedico;
    }

    public void setPrecio_nvo_kdxpaquetesmedico(double precio_nvo_kdxpaquetesmedico) {
        this.precio_nvo_kdxpaquetesmedico = precio_nvo_kdxpaquetesmedico;
    }
      public String getUsuario_modificacion() {
        return usuario_modificacion;
    }
      
    public void setUsuario_modificacion(String usuario_modificacion) {
        this.usuario_modificacion = usuario_modificacion;
    }
    
    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }
    
}
