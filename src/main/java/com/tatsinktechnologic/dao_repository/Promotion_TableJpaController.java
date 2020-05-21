/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao_repository;

import com.tatsinktechnologic.beans_entity.Promotion_Table;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author olivier.tatsinkou
 */
public class Promotion_TableJpaController implements Serializable {

    public Promotion_TableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promotion_Table promotion_Table) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(promotion_Table);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promotion_Table promotion_Table) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            promotion_Table = em.merge(promotion_Table);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = promotion_Table.getPromotion_table_id();
                if (findPromotion_Table(id) == null) {
                    throw new NonexistentEntityException("The promotion_Table with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promotion_Table promotion_Table;
            try {
                promotion_Table = em.getReference(Promotion_Table.class, id);
                promotion_Table.getPromotion_table_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promotion_Table with id " + id + " no longer exists.", enfe);
            }
            em.remove(promotion_Table);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promotion_Table> findPromotion_TableEntities() {
        return findPromotion_TableEntities(true, -1, -1);
    }

    public List<Promotion_Table> findPromotion_TableEntities(int maxResults, int firstResult) {
        return findPromotion_TableEntities(false, maxResults, firstResult);
    }

    private List<Promotion_Table> findPromotion_TableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promotion_Table.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Promotion_Table findPromotion_Table(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promotion_Table.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromotion_TableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promotion_Table> rt = cq.from(Promotion_Table.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
