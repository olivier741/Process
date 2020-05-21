/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao_repository;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tatsinktechnologic.beans_entity.Command;
import com.tatsinktechnologic.beans_entity.Mt_Hist;
import com.tatsinktechnologic.beans_entity.Product;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author olivier.tatsinkou
 */
public class Mt_HistJpaController implements Serializable {

    public Mt_HistJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mt_Hist mt_Hist) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Command command = mt_Hist.getCommand();
            if (command != null) {
                command = em.getReference(command.getClass(), command.getCommand_id());
                mt_Hist.setCommand(command);
            }
            Product product = mt_Hist.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProduct_id());
                mt_Hist.setProduct(product);
            }
            em.persist(mt_Hist);
            if (command != null) {
                command.getListMt_Hist().add(mt_Hist);
                command = em.merge(command);
            }
            if (product != null) {
                product.getListMt_Hist().add(mt_Hist);
                product = em.merge(product);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mt_Hist mt_Hist) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mt_Hist persistentMt_Hist = em.find(Mt_Hist.class, mt_Hist.getMt_his_id());
            Command commandOld = persistentMt_Hist.getCommand();
            Command commandNew = mt_Hist.getCommand();
            Product productOld = persistentMt_Hist.getProduct();
            Product productNew = mt_Hist.getProduct();
            if (commandNew != null) {
                commandNew = em.getReference(commandNew.getClass(), commandNew.getCommand_id());
                mt_Hist.setCommand(commandNew);
            }
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProduct_id());
                mt_Hist.setProduct(productNew);
            }
            mt_Hist = em.merge(mt_Hist);
            if (commandOld != null && !commandOld.equals(commandNew)) {
                commandOld.getListMt_Hist().remove(mt_Hist);
                commandOld = em.merge(commandOld);
            }
            if (commandNew != null && !commandNew.equals(commandOld)) {
                commandNew.getListMt_Hist().add(mt_Hist);
                commandNew = em.merge(commandNew);
            }
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getListMt_Hist().remove(mt_Hist);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getListMt_Hist().add(mt_Hist);
                productNew = em.merge(productNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = mt_Hist.getMt_his_id();
                if (findMt_Hist(id) == null) {
                    throw new NonexistentEntityException("The mt_Hist with id " + id + " no longer exists.");
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
            Mt_Hist mt_Hist;
            try {
                mt_Hist = em.getReference(Mt_Hist.class, id);
                mt_Hist.getMt_his_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mt_Hist with id " + id + " no longer exists.", enfe);
            }
            Command command = mt_Hist.getCommand();
            if (command != null) {
                command.getListMt_Hist().remove(mt_Hist);
                command = em.merge(command);
            }
            Product product = mt_Hist.getProduct();
            if (product != null) {
                product.getListMt_Hist().remove(mt_Hist);
                product = em.merge(product);
            }
            em.remove(mt_Hist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mt_Hist> findMt_HistEntities() {
        return findMt_HistEntities(true, -1, -1);
    }

    public List<Mt_Hist> findMt_HistEntities(int maxResults, int firstResult) {
        return findMt_HistEntities(false, maxResults, firstResult);
    }

    private List<Mt_Hist> findMt_HistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mt_Hist.class));
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

    public Mt_Hist findMt_Hist(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mt_Hist.class, id);
        } finally {
            em.close();
        }
    }

    public int getMt_HistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mt_Hist> rt = cq.from(Mt_Hist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
