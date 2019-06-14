/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.AssuntoDAO;
import br.cesjf.lpwsd.dao.EmprestimoDAO;
import br.cesjf.lpwsd.dao.ReservaDAO;
import br.cesjf.lpwsd.model.Assunto;
import br.cesjf.lpwsd.model.Emprestimo;
import br.cesjf.lpwsd.model.Reserva;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class dashBoardBean implements Serializable{
    
    //Gráficos
    private BarChartModel bookRes;
    private BarChartModel bookEmp;
    private BarChartModel bookResCat;
    private BarChartModel bookEmpCat;
    private BarChartModel total;
    
    //Listas
    private final List<Reserva> res;
    private final List<Emprestimo> emp;
    private final List<Assunto> ast;
    
    //Datas para controle
    private final LocalDate date1;
    private final LocalDate date2;
    private final LocalDate date3;
        
    //Meses para controle
    private int month1;
    private int month2;
    private int month3;
    
    //Controlador da tab
    private int index;
    
    //Construtor
    public dashBoardBean() {
        date1 = LocalDate.now().minusMonths(2);
        date2 = LocalDate.now().minusMonths(3); 
        date3 = LocalDate.now().minusMonths(4); 
        res = new ReservaDAO().buscarTodas();
        emp = new EmprestimoDAO().buscarTodas();
        ast = new AssuntoDAO().buscarTodas();
    }
    
    @PostConstruct
    public void init() {
        createBookReserved();
        createBookBorrowed();
        createBookReservedCategory();
        createBookBorrowedCategory();
        createTotal();
    }
    
    //Cria o gráfico de livros reservados
    public void createBookReserved(){
        reset();
        
        for(Reserva r : res){
            incrementMonth(r.getDataReserva());
        }  
        
        List<Number> values = new ArrayList<>();
        values.add(month3);
        values.add(month2);
        values.add(month1);
        
        List<String> labels = new ArrayList<>();
        labels.add(returnMonth(date3.getMonthValue()));
        labels.add(returnMonth(date2.getMonthValue()));
        labels.add(returnMonth(date1.getMonthValue()));
        
        bookRes = createBar(values, labels, "Livros reservados nos últimos 3 meses");
    }
    
    //Cria o gráfico de livros emprestados
    public void createBookBorrowed(){
        reset();
        
        for(Emprestimo e : emp){
            incrementMonth(e.getDataEmprestimo());
        }
        
        List<Number> values = new ArrayList<>();
        values.add(month3);
        values.add(month2);
        values.add(month1);
        
        List<String> labels = new ArrayList<>();
        labels.add(returnMonth(date3.getMonthValue()));
        labels.add(returnMonth(date2.getMonthValue()));
        labels.add(returnMonth(date1.getMonthValue()));
        
        bookEmp = createBar(values, labels, "Livros emprestados nos últimos 3 meses");
    }
    
    //Cria o gráfico de livros reservados por assunto
    public void createBookReservedCategory(){
        reset();
        
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        for(Assunto a: ast) {
            map.put(a.getAssunto(), 0);
        }
        
        map = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        for(Reserva r: res){
            if(isInterval(r.getDataReserva())){
                map.computeIfPresent(r.getIdExemplar().getIdLivro().getAssuntoid().getNome(), (k, v) -> v + 1);
            }
        }
        
        List<Number> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            values.add(entry.getValue());
        }
        
        List<String> labels = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            labels.add(entry.getKey());
        }
        
        bookResCat = createBar(values, labels, "Livros reservados por assunto nos últimos 3 meses");
    }
    
    //Cria o gráfico de livros emprestados por assunto
    public void createBookBorrowedCategory(){
        reset();
        
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        for(Assunto a: ast) {
            map.put(a.getAssunto(), 0);
        }
        
        map = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        for(Emprestimo e: emp){
            if(isInterval(e.getDataEmprestimo())){
                map.computeIfPresent(e.getIdExemplar().getIdLivro().getAssuntoid().getNome(), (k, v) -> v + 1);
            }
        }
        
        List<Number> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            values.add(entry.getValue());
        }
        
        List<String> labels = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            labels.add(entry.getKey());
        }
        
        bookEmpCat = createBar(values, labels, "Livros emprestados por assunto nos últimos 3 meses");
    }
    
    //Cria o gráfico de livros reservados e emprestados
    public void createTotal(){
        reset();
        int totRes = 0;
        int totEmp = 0;
        
        for(Reserva r : res){
            if(isEqual(r.getDataReserva()))
                totRes++;
        }
        
        for(Emprestimo e : emp){
            if(isEqual(e.getDataEmprestimo()))
                totEmp++;
        }
        
        List<Number> values = new ArrayList<>();
        values.add(totRes);
        values.add(totEmp);
        
        List<String> labels = new ArrayList<>();
        labels.add("Reservados");
        labels.add("Emprestados");
        
        total = createBar(values, labels, "Livros reservados e emprestados no último mês");
    }
    
    //Reseta os valores dos meses
    public void reset(){
        month1 = 0;
        month2 = 0;
        month3 = 0;
    }
       
    //Cria um gráfico
    public BarChartModel createBar(List<Number> values, List<String> labels, String text) {
        BarChartModel barChartModel = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Livros");           
        
        barDataSet.setData(values);
         
        List<String> bgColor = insertBgColor();
        barDataSet.setBackgroundColor(bgColor);
         
        List<String> borderColor = insertBorderColor();
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
        data.setLabels(labels);
        barChartModel.setData(data);
         
        BarChartOptions options = insertOptions();
         
        Title title = insertTitle(text);
        options.setTitle(title);
 
        Legend legend = insertLegend();
        options.setLegend(legend);
 
        barChartModel.setOptions(options);
        return barChartModel;
    }

    //Métodos de ajustes go gráfico
    
    private BarChartOptions insertOptions() {
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
        return options;
    }

    private Legend insertLegend() {
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        return legend;
    }

    private Title insertTitle(String text) {
        Title title = new Title();
        title.setDisplay(true);
        title.setText(text);
        return title;
    }

    private List<String> insertBorderColor() {
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(40, 121, 193)");
        borderColor.add("rgb(45, 191, 36)");
        borderColor.add("rgb(177, 112, 29)");
        borderColor.add("rgb(206, 36, 36)");
        borderColor.add("rgb(177, 29, 137)");
        borderColor.add("rgb(173, 177, 29)");
        borderColor.add("rgb(0, 0, 0)");
        return borderColor;
    }

    private List<String> insertBgColor() {
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(40, 121, 193, 0.4)");
        bgColor.add("rgba(45, 191, 36, 0.4)");
        bgColor.add("rgba(177, 112, 29, 0.4)");
        bgColor.add("rgba(206, 36, 36, 0.4)");
        bgColor.add("rgba(177, 29, 137, 0.4)");
        bgColor.add("rgba(173, 177, 29, 0.4)");
        bgColor.add("rgba(0, 0, 0, 0.4)");
        return bgColor;
    }
    
    //Verifica se data está no intervalo
    public boolean isInterval(Date date){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        int value = calendario.get(Calendar.MONTH);
        return ((value == date1.getMonthValue()) || (value == date2.getMonthValue()) || (value == date3.getMonthValue()));
    }
    
    //Incrementa os meses
    public void incrementMonth(Date date){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        if(calendario.get(Calendar.MONTH) == date1.getMonthValue())
            month1++;
         else if(calendario.get(Calendar.MONTH) == date2.getMonthValue())
            month2++;
         else if(calendario.get(Calendar.MONTH) == date3.getMonthValue())
            month3++;
    }
    
    //Verifica data
    public boolean isEqual(Date date){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        return calendario.get(Calendar.MONTH) == date1.getMonthValue();
    }
    
    //Retorna o nome do mês
     public String returnMonth(int month) {
        switch(month) {
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7: 
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
            default:
                return "";
        }
    }

    //Getters and Setters
     
    public BarChartModel getBookRes() {
        return bookRes;
    }

    public void setBookRes(BarChartModel bookRes) {
        this.bookRes = bookRes;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BarChartModel getBookEmp() {
        return bookEmp;
    }

    public void setBookEmp(BarChartModel bookEmp) {
        this.bookEmp = bookEmp;
    }

    public BarChartModel getTotal() {
        return total;
    }

    public void setTotal(BarChartModel total) {
        this.total = total;
    }

    public BarChartModel getBookResCat() {
        return bookResCat;
    }

    public void setBookResCat(BarChartModel bookResCat) {
        this.bookResCat = bookResCat;
    }

    public BarChartModel getBookEmpCat() {
        return bookEmpCat;
    }

    public void setBookEmpCat(BarChartModel bookEmpCat) {
        this.bookEmpCat = bookEmpCat;
    }
}
