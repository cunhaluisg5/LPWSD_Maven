/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.converter;

import br.cesjf.lpwsd.dao.ReservaDAO;
import br.cesjf.lpwsd.model.Reserva;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author luisg
 */
@FacesConverter(forClass = Reserva.class)
public class ReservaConverter implements Converter, Serializable {
    
    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
        if (codigo != null && !codigo.isEmpty()) {
            int cod = Integer.parseInt(codigo);
            return (Reserva) new ReservaDAO().buscarId(cod);
        }
        return codigo;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
        if (objeto != null) {
            Reserva reserva = (Reserva) objeto;
            return reserva.getId() != null && reserva.getId() > 0 ? reserva.getId().toString() : null;
        }
        return null;
    }
    
}