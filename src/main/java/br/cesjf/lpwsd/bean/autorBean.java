/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import static br.cesjf.lpwsd.dao.AssuntoDAO.assuntoDAO;
import br.cesjf.lpwsd.dao.AutorDAO;
import br.cesjf.lpwsd.model.Assunto;
import br.cesjf.lpwsd.model.Autor;
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
public class autorBean extends crudBean<Autor, AutorDAO> {

    private AutorDAO autorDAO;
    public List<SelectItem> itens;
    
    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Autor> autores = autorDAO.buscarTodas();

        for (Autor autor : autores) {
            list.add(new SelectItem(autor, autor.getNome())); //O que aparece no ComboBox
        }
        return list;
    }

    @Override
    public AutorDAO getDao() {
        if (autorDAO == null) {
            autorDAO = new AutorDAO();
        }
        return autorDAO;
    }

    @Override
    public Autor novo() {
        return new Autor();
    }
}
