package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.EmprestimoDAO;
import br.cesjf.lpwsd.dao.ExemplarDAO;
import br.cesjf.lpwsd.dao.UsuarioDAO;
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
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ViewScoped
@ManagedBean(name = "emprestimoBean")
public class emprestimoBean extends crudBean<Emprestimo, EmprestimoDAO> {

    //DAOs
    private EmprestimoDAO emprestimoDAO;
    private UsuarioDAO usuarioDAO;
    private ExemplarDAO exemplarDAO;
    
    //Listas
    private List<Usuario> usuarios;
    private List<Exemplar> exemplares;
    private List<Emprestimo> emprestimos;
    
    //Variáveis
    private boolean debit;
    private int index;
    private Integer idUsuario;
    private Integer idExemplar;
    
    //Construtor
    public emprestimoBean() {
        usuarioDAO = new UsuarioDAO();
        exemplarDAO = new ExemplarDAO();
        usuarios = usuarioDAO.buscarTodas();
        exemplares = exemplarDAO.buscarTodas();
    }

    //Verifica se existe débito
    public void checkDebit() {
        getEntidade().setIdUsuario(usuarioDAO.buscarId(idUsuario));
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

    //Verifica se exemplar está disponível
    public void checkExemplar() {
        boolean available = getDao().available(idExemplar);
        if (!available)
            debit = true;
        else {
            getEntidade().setIdExemplar(exemplarDAO.buscarId(idExemplar));
            generateDate(getEntidade().getIdExemplar(), getEntidade().getIdUsuario());
            debit = false;
            nextTab();
        }
    }

    //Grava os dados no banco
    public void persist(ActionEvent actionEvent) {
        record(actionEvent);
        reset();
    }

    //Calcula a data
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

    //Calcula próximo dia útil
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
    
    //Retorna ao estado inicial
    public void reset(){
        index = 0;
        debit = false;
        novo();
    }
    
    //Registra data de devolução
    public void returned(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            getEntidade().setDataDevolucao(new Date());
            getDao().persistir(getEntidade());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Empréstimo", "Devolvido com sucesso!"));
        }
    }
    
    //Incrementa índice da tab
    public void nextTab() {
        index++;
    }
    
    //Instancia um novo empréstimo
    @Override
    public Emprestimo novo() {
        idUsuario = null;
        idExemplar = null;
        return new Emprestimo();
    }
    
    //Instancia ou retorna o DAO
    @Override
    public EmprestimoDAO getDao() {
        if (emprestimoDAO == null)
            emprestimoDAO = new EmprestimoDAO();
        return emprestimoDAO;
    }
    
    //Adiciona mensagem de status de usuário
    public void errorUser() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "O usuário não está apto a efetuar empréstimo."));
    }
    
    //Adiciona mensagem de status de exemplar
    public void errorBook() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Exemplar indisponível ou já emprestado."));
    }

    //Getters and Setters
    public EmprestimoDAO getEmprestimoDAO() {
        return emprestimoDAO;
    }

    public void setEmprestimoDAO(EmprestimoDAO emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public ExemplarDAO getExemplarDAO() {
        return exemplarDAO;
    }

    public void setExemplarDAO(ExemplarDAO exemplarDAO) {
        this.exemplarDAO = exemplarDAO;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public void setExemplares(List<Exemplar> exemplares) {
        this.exemplares = exemplares;
    }

    public boolean isDebit() {
        return debit;
    }

    public void setDebit(boolean debit) {
        this.debit = debit;
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
}
