/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.ReservaDAO;
import br.cesjf.lpwsd.model.Reserva;
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
public class reservaBean extends crudBean<Reserva, ReservaDAO>{
    
    private ReservaDAO reservaDAO;    
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Reserva> reservas = reservaDAO.buscarTodas();

        for (Reserva reserva : reservas) {
            list.add(new SelectItem(reserva, reserva.toString())); //O que aparece no ComboBox
        }
        return list;
    }

    @Override
    public ReservaDAO getDao() {
        if (reservaDAO == null) {
            reservaDAO = new ReservaDAO();
        }
        return reservaDAO;
    }

    @Override
    public Reserva novo() {
        return new Reserva();
    }    
}
