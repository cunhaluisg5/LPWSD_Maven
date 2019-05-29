/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.dao;

import br.cesjf.lpwsd.model.Usuario;
import br.cesjf.lpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author luisg
 */
public class UsuarioDAO implements Serializable,CrudDAO<Usuario>{

    //DAO
    public static UsuarioDAO usuarioDAO;

    //Retorna o DAO
    public static UsuarioDAO getInstance() {
        if (usuarioDAO == null)
            usuarioDAO = new UsuarioDAO();
        return usuarioDAO;
    }

    //Busca um usuário por nome
    public Usuario buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Usuario a WHERE a.nome =:nome ");
        query.setParameter("nome", nome);

        List<Usuario> usuario = query.getResultList();
        if (usuario != null && usuario.size() > 0)
            return usuario.get(0);
        return null;
    }
    
    //Busca um usuário por id
    @Override
    public Usuario buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Usuario a WHERE a.id =:id ");
        query.setParameter("id", id);

        List<Usuario> usuario = query.getResultList();
        if (usuario != null && usuario.size() > 0)
            return usuario.get(0);
        return null;
    }

    //Busca todos os usuários
    @Override
    public List<Usuario> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("FROM Usuario As a");
        return query.getResultList();
    }

    //Busca a instância de usuários
    @Override
    public List<Usuario> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM Usuario a GROUP BY a.usuario");
        return query.getResultList();
    }

    //Remove um usuário
    @Override
    public void remover(Usuario usuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(usuario))
            usuario = em.merge(usuario);
        em.remove(usuario);
        em.getTransaction().commit();
    }

    //Persiste os dados do usuário
    @Override
    public Usuario persistir(Usuario usuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            usuario = em.merge(usuario);
            em.getTransaction().commit();
            System.out.println("Registro Usuario gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    //Remove todos os usuários
    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Usuario ");
        query.executeUpdate();
        em.getTransaction().commit();
    }

    //Valida os dados do usuário
    public Usuario validar(String login, String senha) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Usuario a WHERE a.login =:login AND a.senha=:senha ");
        query.setParameter("login", login);
        query.setParameter("senha", senha);

        List<Usuario> usuario = query.getResultList();
        if (usuario != null && usuario.size() > 0)
            return usuario.get(0);
        return null;
    }
}
