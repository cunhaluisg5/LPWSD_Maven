/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.report.Relatorio;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class relatorioBean {
    //Controla as tabs
    private int index;
    
    //Período para a geração do formulário
    private Date dateStart;
    private Date dateEnd;
    
    //Referência para o formulário
    Relatorio report;
    
    ///Gera o relatório de livros e exemplares
    public void report1(){
        report = new Relatorio();
        report.setTReport("livrosExemplares");
        report.getRelatorio(false);  
    }
    
    ///Gera o relatório de livros emprestados
    public void report2(){
        report = new Relatorio();
        report.setTReport("livrosEmprestados");
        report.getRelatorio(false);  
    }
    
    ///Gera o relatório de livros reservados
    public void report3(){
        report = new Relatorio();
        report.setTReport("livrosReservados");
        report.getRelatorio(false);  
    }
    
    ///Gera o relatório de livros atrasados
    public void report4(){
        report = new Relatorio();
        report.setTReport("livrosAtrasados");
        report.getRelatorio(false);  
    }
    
    ///Gera o relatório de usuários cadastrados
    public void report5(){
        report = new Relatorio();
        report.setTReport("usuarios");
        report.getRelatorio(false);  
    }
    
    ///Gera o relatório com base em empréstimos e reservas feitas em um período específico
    public void report6(){
        if(dateStart.after(dateEnd))
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
            (FacesMessage.SEVERITY_WARN, "Atenção!", "Período incorreto."));
         else{
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            report = new Relatorio();
            report.setDateStart(fm.format(dateStart));
            report.setDateEnd(fm.format(dateEnd));
            report.setTReport("gerencial");
            report.getRelatorio(true);  
        }
    }
    
    //Getters and Setters
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
