/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import javafx.util.StringConverter;

/**
 *
 * @author alfar
 */
public class ProveedorStringConverter extends StringConverter<Proveedor> {
    @Override
    public String toString(Proveedor proveedor) {
        // Devuelve la representación en texto del proveedor (por ejemplo, el nombre)
        if (proveedor != null) {
            return proveedor.getNombreComercial();
        }
        return "";
    }

    @Override
    public Proveedor fromString(String string) {
        // Realiza la conversión de texto a objeto Proveedor
        // Aquí deberás obtener el objeto Proveedor correspondiente al texto ingresado
        // Puedes hacerlo consultando la base de datos o cualquier otra fuente de datos
        // y devolver el objeto Proveedor adecuado.
        // En este ejemplo, simplemente devolvemos null.
        return null;
    }
}
