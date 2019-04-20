/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.AutorDAO;
import br.cesjf.lpwsd.model.Autor;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class autorBean extends crudBean<Autor, AutorDAO> {

    private AutorDAO autorDAO;

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
