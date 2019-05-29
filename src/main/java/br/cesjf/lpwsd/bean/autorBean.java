/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.AutorDAO;
import br.cesjf.lpwsd.model.Autor;
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

    //DAO
    private AutorDAO autorDAO;
    
    //Itens
    public List<SelectItem> itens;

    //Retorna o DAO
    @Override
    public AutorDAO getDao() {
        if (autorDAO == null)
            autorDAO = new AutorDAO();
        return autorDAO;
    }

    //Instancia o autor
    @Override
    public Autor novo() {
        return new Autor();
    }
}