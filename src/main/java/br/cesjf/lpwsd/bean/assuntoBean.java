/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.AssuntoDAO;
import br.cesjf.lpwsd.model.Assunto;
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
public class assuntoBean extends crudBean<Assunto, AssuntoDAO>{
    
    private AssuntoDAO assuntoDAO;    
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Assunto> assuntos = assuntoDAO.buscarTodas();

        for (Assunto assunto : assuntos) {
            list.add(new SelectItem(assunto, assunto.getNome())); //O que aparece no ComboBox
        }
        return list;
    }

    @Override
    public AssuntoDAO getDao() {
        if (assuntoDAO == null) {
            assuntoDAO = new AssuntoDAO();
        }
        return assuntoDAO;
    }

    @Override
    public Assunto novo() {
        return new Assunto();
    }    
}