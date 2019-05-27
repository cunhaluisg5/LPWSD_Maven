package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.EmprestimoDAO;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import br.cesjf.lpwsd.model.Emprestimo;
import br.cesjf.lpwsd.model.Exemplar;
import br.cesjf.lpwsd.model.Usuario;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.faces.bean.ManagedProperty;

@ViewScoped
@ManagedBean(name = "emprestimoBean")
public class emprestimoBean extends crudBean<Emprestimo, EmprestimoDAO> {

    private EmprestimoDAO emprestimoDAO;
    private boolean debit;
    private int index;
    private Integer idUsuario = null;
    private Integer idExemplar = null;
    @ManagedProperty(value = "#{usuarioBean}")
    private usuarioBean usuarioBean;
    @ManagedProperty(value = "#{exemplarBean}")
    private exemplarBean exemplarBean;

    public void checkDebit() {
        getEntidade().setIdUsuario(usuarioBean.buscarId(idUsuario));
        boolean isDebit = getDao().checkDebit(idUsuario);
        Long opened = getDao().checkOpened(idUsuario);
        String typeUser = getEntidade().getIdUsuario().getTipo();

        if (isDebit)
            debit = true;
        else if (("Professor".equals(typeUser) && opened >= 5))
            debit = true;
        else if ((!"Professor".equals(typeUser) && opened >= 3))
            debit = true;
        else {
            debit = false;
            nextTab();
        }
    }

    public void checkExemplar() {
        boolean available = getDao().available(idExemplar);
        if (!available)
            debit = true;
        else {
            getEntidade().setIdExemplar(exemplarBean.buscarId(idExemplar));
            generateDate(getEntidade().getIdExemplar(), getEntidade().getIdUsuario());
            debit = false;
            nextTab();
        }
    }

    public void persist(ActionEvent actionEvent) {
        record(actionEvent);
        reset();
    }

    public void generateDate(Exemplar ex, Usuario user) {
        LocalDate dateNow;
        Date dateEmprestimo = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        getEntidade().setDataEmprestimo(dateEmprestimo);
        boolean opcao = ex.getCircular();
        String typeUser = user.getTipo();

        if (opcao) {
            if ("Professor".equals(typeUser))
                dateNow = LocalDate.now().plusDays(15);
            else
                dateNow = LocalDate.now().plusDays(10);
        } else
            dateNow = nextDay();
        Date datePrevista = Date.from(dateNow.atStartOfDay(ZoneId.systemDefault()).toInstant());
        getEntidade().setDataPrevista(datePrevista);
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
    
    public void reset(){
        index = 0;
        debit = false;
        novo();
    }
    
    public void returned(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            getEntidade().setDataDevolucao(new Date());
            record(actionEvent);
        }
    }

    public boolean isDebit() {
        return debit;
    }

    public void setDebit(boolean debit) {
        this.debit = debit;
    }

    @Override
    public EmprestimoDAO getDao() {
        if (emprestimoDAO == null)
            emprestimoDAO = new EmprestimoDAO();
        return emprestimoDAO;
    }
    
    public void nextTab() {
        index++;
    }

    public EmprestimoDAO getEmprestimoDAO() {
        return emprestimoDAO;
    }

    public void setEmprestimoDAO(EmprestimoDAO emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public usuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(usuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public exemplarBean getExemplarBean() {
        return exemplarBean;
    }

    public void setExemplarBean(exemplarBean exemplarBean) {
        this.exemplarBean = exemplarBean;
    }
    
    @Override
    public Emprestimo novo() {
        idUsuario = null;
        idExemplar = null;
        return new Emprestimo();
    }
}
