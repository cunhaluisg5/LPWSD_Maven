/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.EmprestimoDAO;
import br.cesjf.lpwsd.dao.ExemplarDAO;
import br.cesjf.lpwsd.dao.ReservaDAO;
import br.cesjf.lpwsd.dao.UsuarioDAO;
import br.cesjf.lpwsd.model.Reserva;
import br.cesjf.lpwsd.util.SessionUtil;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class reservaBean extends crudBean<Reserva, ReservaDAO>{
    
    //DAO
    private ReservaDAO reservaDAO;
    
    //Variáveis
    private Integer idUsuario = null;
    private Integer idExemplar = null;

    //Beans
    @ManagedProperty(value = "#{emprestimoBean}")
    private emprestimoBean emprestimoBean;
    @ManagedProperty(value = "#{exemplarBean}")
    private exemplarBean exemplarBean;
    @ManagedProperty(value = "#{usuarioBean}")
    private usuarioBean usuarioBean;
    
    //Realiza o empréstimo
    public void lend(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            emprestimoBean.getEntidade().setIdUsuario(getEntidade().getIdUsuario());
            emprestimoBean.getEntidade().setIdExemplar(getEntidade().getIdExemplar());
            emprestimoBean.generateDate(getEntidade().getIdExemplar(), getEntidade().getIdUsuario());

            getEntidade().setIdEmprestimo(new EmprestimoDAO().persistir(emprestimoBean.getEntidade()));
            record(actionEvent);
        }
    }
    
    //Realiza o cancelamento
    public void cancel(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            getEntidade().setCancelar(SessionUtil.getUserName());
            record(actionEvent);
        }
    }
    
    //Verifica se existe débito
    public boolean isDebit() {
        getEntidade().setIdUsuario(new UsuarioDAO().buscarId(idUsuario));
        boolean isDebit = emprestimoBean.getDao().checkDebit(idUsuario);
        Long opened = emprestimoBean.getDao().checkOpened(idUsuario);
        String typeUser = getEntidade().getIdUsuario().getTipo();

        if (isDebit)
            return true;
        else if (("Professor".equals(typeUser) && opened >= 5))
            return true;
        else if ((!"Professor".equals(typeUser) && opened >= 3))
            return true;
        else {
            return false;
        }
    }
    
    //Verifica se exemplar está disponível
    public boolean available() {
        boolean available = new ReservaDAO().available(idExemplar);
        return available;
    }

    //Persiste os dados
    public void persist(ActionEvent actionEvent) {
        if((!isDebit()) && (available())){
            getEntidade().setIdUsuario(usuarioBean.buscarId(idUsuario));
            if(new ExemplarDAO().buscarId(idExemplar).getCircular()){
                getEntidade().setIdExemplar(exemplarBean.buscarId(idExemplar));
                record(actionEvent);
                idUsuario = null;
                idExemplar = null;
            }else
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                (FacesMessage.SEVERITY_WARN, "Não Circular!", "O exemplar não pode ser reservado."));
        }else{
            if(isDebit())
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                (FacesMessage.SEVERITY_WARN, "Em Débito!", "Regularize sua situação."));
            if(!available())
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                (FacesMessage.SEVERITY_WARN, "Pendente!", "Já existe uma reserva pendente."));
        }
    }
    
    public void systemCancel() {
        List<Reserva> reservations = getDao().systemCancel();
        LocalDate dateNow = LocalDate.now();

        if (reservations.size() > 0) {
            for (Reserva r : reservations) {
                LocalDate dateRes = r.getDataReserva().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long interval = ChronoUnit.DAYS.between(dateRes, dateNow);

                if (interval > 3) {
                    Reserva aux = r;
                    aux.setCancelar("Sistema");
                    getDao().persistir(aux);
                }
            }
        }
    }

    //Retorna o DAO
    @Override
    public ReservaDAO getDao() {
        if (reservaDAO == null)
            reservaDAO = new ReservaDAO();
        return reservaDAO;
    }

    //Instancia a reserva
    @Override
    public Reserva novo() {
        return new Reserva();
    }

    //Getters and Setters
    
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
}