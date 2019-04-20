/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.converter;

import br.cesjf.lpwsd.dao.ExemplarDAO;
import br.cesjf.lpwsd.model.Exemplar;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author luisg
 */
@FacesConverter(forClass = Exemplar.class)
public class ExemplarConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
        if (codigo != null && !codigo.isEmpty()) {
            int cod = Integer.parseInt(codigo);
            return (Exemplar) new ExemplarDAO().buscarId(cod);
        }
        return codigo;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object objeto) {
        if (objeto != null) {
            Exemplar exemplar = (Exemplar) objeto;
            return exemplar.getId() != null && exemplar.getId() > 0 ? exemplar.getId().toString() : null;
        }
        return null;
    }
}

