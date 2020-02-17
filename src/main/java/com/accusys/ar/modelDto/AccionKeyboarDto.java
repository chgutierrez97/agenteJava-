
package com.accusys.ar.modelDto;

/**
 *description
 * @author Christian Gutierrez
 */
public class AccionKeyboarDto {
    
    private  String description;
    private  String valor;

    public AccionKeyboarDto() {
    }

    public AccionKeyboarDto(String description, String valor) {
        this.description = description;
        this.valor = valor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "AccionKeyboarDto{" + "description=" + description + ", valor=" + valor + '}';
    }
}
