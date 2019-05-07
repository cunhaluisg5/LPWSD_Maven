/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.converter;

import br.cesjf.lpwsd.dao.EditoraDAO;
import br.cesjf.lpwsd.model.Editora;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author luisg
 */
@FacesConverter(forClass = Editora.class, value = "editoraConverter")
public class EditoraConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
        if (codigo != null && !codigo.isEmpty()) {
            int cod = Integer.parseInt(codigo);
            return (Editora) new EditoraDAO().buscarId(cod);
        }
        return codigo;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
        if (objeto != null) {
            Editora editora = (Editora) objeto;
            return editora.getId() != null && editora.getId() > 0 ? editora.getId().toString() : null;
        }
        return null;
    }

}

