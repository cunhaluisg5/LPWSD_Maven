/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.dao;

import br.cesjf.lpwsd.model.Editora;
import br.cesjf.lpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author luisg
 */
public class EditoraDAO implements Serializable, CrudDAO<Editora>{
    
    //DAO
    public static EditoraDAO editoraDAO;

    //Retorna o DAO
    public static EditoraDAO getInstance() {
        if (editoraDAO == null)
            editoraDAO = new EditoraDAO();
        return editoraDAO;
    }    
    
    //Busca editora por id
    @Override
    public Editora buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Editora a WHERE a.id =:id ");
        query.setParameter("id", id);

        List<Editora> editora = query.getResultList();
        if (editora != null && editora.size() > 0)
            return editora.get(0);
        return null;
    }
    
    //Busca editora por nome
    public Editora buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Editora a WHERE a.nome =:nome ");
        query.setParameter("nome", nome);

        List<Editora> editora = query.getResultList();
        if (editora != null && editora.size() > 0)
            return editora.get(0);
        return null;
    }

    //Busca todas as editoras
    @Override
    public List<Editora> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("FROM Editora As a");
        return query.getResultList();
    }

    //Busca a instância de editora
    @Override
    public List<Editora> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM Editora a GROUP BY a.editora");
        return query.getResultList();
    }
    
    //Remove a editora
    @Override
    public void remover(Editora editora) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(editora))
            editora = em.merge(editora);
        em.remove(editora);
        em.getTransaction().commit();
    }

    //Persiste os dados da editora
    @Override
    public Editora persistir(Editora editora) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            editora = em.merge(editora);
            em.getTransaction().commit();
            System.out.println("Registro Editora gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editora;
    }

    //Remove todas as editoras
    @Override
    public void removeAll() {
       EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery("DELETE FROM Editora");
       query.executeUpdate();
       em.getTransaction().commit();
    }
}