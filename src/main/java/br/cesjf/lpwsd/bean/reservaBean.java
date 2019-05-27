/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.EmprestimoDAO;
import br.cesjf.lpwsd.dao.ReservaDAO;
import br.cesjf.lpwsd.model.Reserva;
import br.cesjf.lpwsd.util.SessionUtil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class reservaBean extends crudBean<Reserva, ReservaDAO>{
    
    private ReservaDAO reservaDAO;
    private Integer idUsuario = null;
    private Integer idExemplar = null;

    @ManagedProperty(value = "#{emprestimoBean}")
    private emprestimoBean emprestimoBean;
    @ManagedProperty(value = "#{exemplarBean}")
    private exemplarBean exemplarBean;
    @ManagedProperty(value = "#{usuarioBean}")
    private usuarioBean usuarioBean;

    public emprestimoBean getEmprestimoBean() {
        return emprestimoBean;
    }

    public void setEmprestimoBean(emprestimoBean emprestimoBean) {
        this.emprestimoBean = emprestimoBean;
    }

    public exemplarBean getExemplarBean() {
        return exemplarBean;
    }

    public void setExemplarBean(exemplarBean exemplarBean) {
        this.exemplarBean = exemplarBean;
    }

    public usuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(usuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdExemplar() {
        return idExemplar;
    }

    public void setIdExemplar(Integer idExemplar) {
        this.idExemplar = idExemplar;
    }

    public void lend(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            emprestimoBean.getEntidade().setIdUsuario(getEntidade().getIdUsuario());
            emprestimoBean.getEntidade().setIdExemplar(getEntidade().getIdExemplar());
            emprestimoBean.generateDate(getEntidade().getIdExemplar(), getEntidade().getIdUsuario());

            getEntidade().setIdEmprestimo(new EmprestimoDAO().persistir(emprestimoBean.getEntidade()));
            record(actionEvent);
        }
    }
    
    public void cancel(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            getEntidade().setCancelar(SessionUtil.getUserName());
            record(actionEvent);
        }
    }

    public void persist(ActionEvent actionEvent) {
        getEntidade().setIdUsuario(usuarioBean.buscarId(idUsuario));
        getEntidade().setIdExemplar(exemplarBean.buscarId(idExemplar));
        record(actionEvent);
        idUsuario = null;
        idExemplar = null;
    }

    @Override
    public ReservaDAO getDao() {
        if (reservaDAO == null)
            reservaDAO = new ReservaDAO();
        return reservaDAO;
    }

    @Override
    public Reserva novo() {
        return new Reserva();
    }
}