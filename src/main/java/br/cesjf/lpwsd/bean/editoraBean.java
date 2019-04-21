/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.EditoraDAO;
import br.cesjf.lpwsd.model.Editora;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class editoraBean extends crudBean<Editora, EditoraDAO> {

    private EditoraDAO editoraDAO;
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Editora> editoras = editoraDAO.buscarTodas();

        for (Editora editora : editoras) {
            list.add(new SelectItem(editora, editora.getNome())); //O que aparece no ComboBox
        }
        return list;
    }

    public Editora buscarId(int id) {
        return new EditoraDAO().buscarId(id);
    }

    @Override
    public EditoraDAO getDao() {
        if (editoraDAO == null) {
            editoraDAO = new EditoraDAO();
        }
        return editoraDAO;
    }

    @Override
    public Editora novo() {
        return new Editora();
    }

}
