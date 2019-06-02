/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

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
    
}
