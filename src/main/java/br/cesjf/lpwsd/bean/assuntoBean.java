/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.AssuntoDAO;
import br.cesjf.lpwsd.model.Assunto;
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
public class assuntoBean extends crudBean<Assunto, AssuntoDAO>{
    
    //DAO
    private AssuntoDAO assuntoDAO;  
    
    //Itens
    public List<SelectItem> itens;

    //Retorna o DAO
    @Override
    public AssuntoDAO getDao() {
        if (assuntoDAO == null)
            assuntoDAO = new AssuntoDAO();
        return assuntoDAO;
    }

    //Instancia o assunto
    @Override
    public Assunto novo() {
        return new Assunto();
    } 
}