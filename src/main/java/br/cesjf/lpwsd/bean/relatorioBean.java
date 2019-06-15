/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.report.Relatorio;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class relatorioBean {
    //Controla as tabs
    private int index;
    
    ///Gera o relatório de livros e exemplares
    public void report1(){
        Relatorio relatorio = new Relatorio();
        relatorio.setTReport("livrosExemplares");
        relatorio.getRelatorio();  
    }
    
    ///Gera o relatório de livros emprestados
    public void report2(){
        Relatorio relatorio = new Relatorio();
        relatorio.setTReport("livrosEmprestados");
        relatorio.getRelatorio();  
    }
    
    ///Gera o relatório de livros reservados
    public void report3(){
        Relatorio relatorio = new Relatorio();
        relatorio.setTReport("livrosReservados");
        relatorio.getRelatorio();  
    }
    
    ///Gera o relatório de livros atrasados
    public void report4(){
        Relatorio relatorio = new Relatorio();
        relatorio.setTReport("livrosAtrasados");
        relatorio.getRelatorio();  
    }
    
    ///Gera o relatório de usuários cadastrados
    public void report5(){
        Relatorio relatorio = new Relatorio();
        relatorio.setTReport("usuarios");
        relatorio.getRelatorio();  
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
