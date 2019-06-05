/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.report.Relatorio;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.charts.bar.BarChartModel;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class graficoBean implements Serializable{
    
    private BarChartModel bookReserved;
    private BarChartModel bookEmp;
    //Indica o nome do relatório
    private String report;
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
                report = "gLivrosReservados";
                break;
            case 1:
                report = "gLivrosEmprestados";
                break;
            case 2:
                report = "gLivrosReservadosCategoria";
                break;
            case 3:
                report = "gLivrosEmprestadosCategoria";
                break;
            case 4:
                report = "gLivrosTotal";
                break;
        }
    }

    public BarChartModel getBookReserved() {
        return bookReserved;
    }

    public void setBookReserved(BarChartModel bookReserved) {
        this.bookReserved = bookReserved;
    }

    public BarChartModel getBookEmp() {
        return bookEmp;
    }

    public void setBookEmp(BarChartModel bookEmp) {
        this.bookEmp = bookEmp;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
}
