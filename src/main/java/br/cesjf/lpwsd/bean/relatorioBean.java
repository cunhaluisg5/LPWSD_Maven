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
    
    //Indica o nome do relatório
    private String report;
    
    //Controla as tabs
    private int index;
    
    //Gera um relatório
    public void gerarRelatorio() {
        Relatorio relatorio = new Relatorio();
        nameReport();
        relatorio.setReport(report);
        relatorio.getRelatorio();
    }
    
    //Seleciona o nome do relatório
    public void nameReport(){
        switch(index){
            case 0:
                report = "livrosExemplares";
                break;
            case 1:
                report = "livrosEmprestados";
                break;
            case 2:
                report = "livrosReservados";
                break;
            case 3:
                report = "livrosAtrasados";
                break;
            case 4:
                report = "usuarios";
                break;
        }
    }
    
    //getters and Setters
    
    public String getCaminho() {
        return report;
    }

    public void setCaminho(String caminho) {
        this.report = caminho;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
