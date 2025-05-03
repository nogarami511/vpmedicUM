/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class Aseguradora {

    private int idAseguradora;
    private String nombreAseguradora;
    private double factorAseguradora;
    private boolean materialCuracionAseguradora;
    private boolean medicamentosAseguradora;
    private boolean equipoMedicoAseguradora;
    private int usuarioModificacionAseguradora;
    private Date fechaModificacionAseguradora;
    private String factorString, MedicamentoString, equipoString, matterialString;

    public String getMedicamentoString() {
        if(medicamentosAseguradora){
            MedicamentoString = "INCLUIDO";
        }else {
            MedicamentoString = "NO INCLUIDO";
        }
        return MedicamentoString;
    }

    public String getEquipoString() {
        if(equipoMedicoAseguradora){
            equipoString = "INCLUIDO";
        }else {
            equipoString = "NO INCLUIDO";
        }
        return equipoString;
    }

    public String getMatterialString() {
        if(materialCuracionAseguradora){
            matterialString = "INCLUIDO";
        }else {
            matterialString = "NO INCLUIDO";
        }
        return matterialString;
    }


    
    public Aseguradora(String nombreAseguradora, double factorAseguradora, boolean materialCuracionAseguradora, boolean medicamentosAseguradora, boolean equipoMedicoAseguradora) {
        this.nombreAseguradora = nombreAseguradora;
        this.factorAseguradora = factorAseguradora;
        this.materialCuracionAseguradora = materialCuracionAseguradora;
        this.medicamentosAseguradora = medicamentosAseguradora;
        this.equipoMedicoAseguradora = equipoMedicoAseguradora;
    }

    public int getIdAseguradora() {
        return idAseguradora;
    }

    public void setIdAseguradora(int idAseguradora) {
        this.idAseguradora = idAseguradora;
    }

    public String getNombreAseguradora() {
        return nombreAseguradora;
    }

    public void setNombreAseguradora(String nombreAseguradora) {
        this.nombreAseguradora = nombreAseguradora;
    }

    public double getFactorAseguradora() {
        return factorAseguradora;
    }

    public void setFactorAseguradora(double factorAseguradora) {
        this.factorAseguradora = factorAseguradora;
    }
        public String getFactorString() {
        return factorString;
    }

    public void setFactorString(String factorString) {
        this.factorString = factorString;
    }

    public Aseguradora() {
    }

    public boolean isMaterialCuracionAseguradora() {
        return materialCuracionAseguradora;
    }

    public void setMaterialCuracionAseguradora(boolean materialCuracionAseguradora) {
        this.materialCuracionAseguradora = materialCuracionAseguradora;
    }

    public boolean isMedicamentosAseguradora() {
        return medicamentosAseguradora;
    }

    public void setMedicamentosAseguradora(boolean medicamentosAseguradora) {
        this.medicamentosAseguradora = medicamentosAseguradora;
    }

    public boolean isEquipoMedicoAseguradora() {
        return equipoMedicoAseguradora;
    }

    public void setEquipoMedicoAseguradora(boolean equipoMedicoAseguradora) {
        this.equipoMedicoAseguradora = equipoMedicoAseguradora;
    }

    public int getUsuarioModificacionAseguradora() {
        return usuarioModificacionAseguradora;
    }

    public void setUsuarioModificacionAseguradora(int usuarioModificacionAseguradora) {
        this.usuarioModificacionAseguradora = usuarioModificacionAseguradora;
    }

    public Date getFechaModificacionAseguradora() {
        return fechaModificacionAseguradora;
    }

    public void setFechaModificacionAseguradora(Date fechaModificacionAseguradora) {
        this.fechaModificacionAseguradora = fechaModificacionAseguradora;
    }

    @Override
    public String toString() {
        return nombreAseguradora;
    }
}
