/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.EditoraDAO;
import br.cesjf.lpwsd.model.Editora;
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

    //DAO
    private EditoraDAO editoraDAO;
    
    //Itens
    public List<SelectItem> itens;

    //Busca editora por id
    public Editora buscarId(int id) {
        return new EditoraDAO().buscarId(id);
    }

    //Retorna o DAO
    @Override
    public EditoraDAO getDao() {
        if (editoraDAO == null)
            editoraDAO = new EditoraDAO();
        return editoraDAO;
    }

    //Instancia uma Editora
    @Override
    public Editora novo() {
        return new Editora();
    }
}