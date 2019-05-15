/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.EmprestimoDAO;
import br.cesjf.lpwsd.model.Emprestimo;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class emprestimoBean extends crudBean<Emprestimo, EmprestimoDAO>{
    
    private EmprestimoDAO emprestimoDAO;
    private boolean debit = false;
    private int index = 0;
    private boolean tabUsuario;
    private boolean tabExemplar = true;
    private boolean tabFinalizar = true;

    public EmprestimoDAO getEmprestimoDAO() {
        return emprestimoDAO;
    }

    public void setEmprestimoDAO(EmprestimoDAO emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    public boolean isDebit() {
        return debit;
    }

    public void setDebit(boolean debit) {
        this.debit = debit;
    }

    @Override
    public EmprestimoDAO getDao() {
        if (emprestimoDAO == null) {
            emprestimoDAO = new EmprestimoDAO();
        }
        return emprestimoDAO;
    }

    @Override
    public Emprestimo novo() {
        return new Emprestimo();
    }
    
    public void checkDebit(){
        int idUser = getEntidade().getIdUsuario().getId();
        if(getDao().checkDebit(idUser) > 0)
            debit = true;
        else{
            int opened = getDao().checkOpened(idUser);
            String tipo = getEntidade().getIdUsuario().getTipo();
            if(("Professor".equals(tipo) && opened >= 5) || (!"Professor".equals(tipo) && opened >= 3))
                debit = true;
            else{
                debit = false;
                tabUsuario = true;
                tabExemplar = false;
                nextTab();
            }
        }
    }
    
    public void checkExemplar(){
        int idExemplar = getEntidade().getIdExemplar().getId();
        if(getDao().available(idExemplar) != null){
            debit = true;
        }
        else{
            debit = false;
            tabUsuario = true;
            tabExemplar = true;
            tabFinalizar = false;
            nextTab();
            record();
        }
    }
    
    public void record() {
        getEntidade().setDataEmprestimo(new Date());
        if (getEntidade().getIdExemplar().getCircular()) {
            if ("Professor".equals(getEntidade().getIdUsuario().getTipo()))
                generateDate(1);
            else
                generateDate(0);
        } else
            generateDate(2);
    }
    
    public void persist(ActionEvent actionEvent){
        record(actionEvent);
        reset();
    }
    
    public void reset(){
        index = 0;
        tabUsuario = false;
        tabExemplar = true;
        tabFinalizar = true;
        debit = false;
        novo();
    }
    
    public void generateDate(int opcao) {
        LocalDate dateNow = null;
        Date dateEmprestimo = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        getEntidade().setDataEmprestimo(dateEmprestimo);
        switch (opcao) {
            case 0:
                dateNow = LocalDate.now().plusDays(10);
                break;
            case 1:
                dateNow = LocalDate.now().plusDays(15);
                break;
            case 2:
                dateNow = nextDay();
                break;
            default:
                break;
        }
        Date dateDevolucao = Date.from(dateNow.atStartOfDay(ZoneId.systemDefault()).toInstant());
        getEntidade().setDataDevolucao(dateDevolucao);
    }

    private LocalDate nextDay() {
        LocalDate dateNow;
        DayOfWeek dayWeek = LocalDate.now().getDayOfWeek();
        switch (dayWeek) {
            case FRIDAY:
                dateNow = LocalDate.now().plusDays(3);
                break;
            case SATURDAY:
                dateNow = LocalDate.now().plusDays(2);
                break;
            default:
                dateNow = LocalDate.now().plusDays(1);
                break;
        }
        return dateNow;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void nextTab() {
        index++;
    }

    public boolean isTabUsuario() {
        return tabUsuario;
    }

    public void setTabUsuario(boolean tabUsuario) {
        this.tabUsuario = tabUsuario;
    }

    public boolean isTabExemplar() {
        return tabExemplar;
    }

    public void setTabExemplar(boolean tabExemplar) {
        this.tabExemplar = tabExemplar;
    }

    public boolean isTabFinalizar() {
        return tabFinalizar;
    }

    public void setTabFinalizar(boolean tabFinalizar) {
        this.tabFinalizar = tabFinalizar;
    }
}
