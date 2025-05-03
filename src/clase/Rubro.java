package clase;

import java.text.NumberFormat;
import java.util.Locale;

public class Rubro {

    private NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
    private int id;
    private String nombre;
    private double monto;
    private String observaciones;
    private int ministracion;
    private String nombreMinis;
    private double gasto_mintracion;
    private String montoFormateado;

    public Rubro() {
    }

    public Rubro(String nombre, double monto, String observaciones, int ministracion) {
        this.nombre = nombre;
        this.monto = monto;
        this.observaciones = observaciones;
        this.ministracion = ministracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.toUpperCase();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        montoFormateado = formatoMoneda.format(monto);
        this.monto = monto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getMinistracion() {
        return ministracion;
    }

    public void setMinistracion(int ministracion) {
        this.ministracion = ministracion;
    }

    public String getNombreMinis() {
        return nombreMinis;
    }

    public void setNombreMinis(String nombreMinis) {
        this.nombreMinis = nombreMinis;
    }

    public double getGasto_mintracion() {
        return gasto_mintracion;
    }

    public void setGasto_mintracion(double gasto_mintracion) {
        this.gasto_mintracion = gasto_mintracion;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getMontoFormateado() {
        return montoFormateado;
    }

}
