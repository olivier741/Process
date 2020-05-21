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
import com.tatsinktechnologic.beans_entity.Mo_Hist;
import com.tatsinktechnologic.beans_entity.Product;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author olivier.tatsinkou
 */
public class Mo_HistJpaController implements Serializable {

    public Mo_HistJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mo_Hist mo_Hist) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Command command = mo_Hist.getCommand();
            if (command != null) {
                command = em.getReference(command.getClass(), command.getCommand_id());
                mo_Hist.setCommand(command);
            }
            Product product = mo_Hist.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProduct_id());
                mo_Hist.setProduct(product);
            }
            em.persist(mo_Hist);
            if (command != null) {
                command.getListMo_Hist().add(mo_Hist);
                command = em.merge(command);
            }
            if (product != null) {
                product.getListMo_Hist().add(mo_Hist);
                product = em.merge(product);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mo_Hist mo_Hist) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mo_Hist persistentMo_Hist = em.find(Mo_Hist.class, mo_Hist.getMo_his_id());
            Command commandOld = persistentMo_Hist.getCommand();
            Command commandNew = mo_Hist.getCommand();
            Product productOld = persistentMo_Hist.getProduct();
            Product productNew = mo_Hist.getProduct();
            if (commandNew != null) {
                commandNew = em.getReference(commandNew.getClass(), commandNew.getCommand_id());
                mo_Hist.setCommand(commandNew);
            }
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProduct_id());
                mo_Hist.setProduct(productNew);
            }
            mo_Hist = em.merge(mo_Hist);
            if (commandOld != null && !commandOld.equals(commandNew)) {
                commandOld.getListMo_Hist().remove(mo_Hist);
                commandOld = em.merge(commandOld);
            }
            if (commandNew != null && !commandNew.equals(commandOld)) {
                commandNew.getListMo_Hist().add(mo_Hist);
                commandNew = em.merge(commandNew);
            }
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getListMo_Hist().remove(mo_Hist);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getListMo_Hist().add(mo_Hist);
                productNew = em.merge(productNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = mo_Hist.getMo_his_id();
                if (findMo_Hist(id) == null) {
                    throw new NonexistentEntityException("The mo_Hist with id " + id + " no longer exists.");
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
            Mo_Hist mo_Hist;
            try {
                mo_Hist = em.getReference(Mo_Hist.class, id);
                mo_Hist.getMo_his_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mo_Hist with id " + id + " no longer exists.", enfe);
            }
            Command command = mo_Hist.getCommand();
            if (command != null) {
                command.getListMo_Hist().remove(mo_Hist);
                command = em.merge(command);
            }
            Product product = mo_Hist.getProduct();
            if (product != null) {
                product.getListMo_Hist().remove(mo_Hist);
                product = em.merge(product);
            }
            em.remove(mo_Hist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mo_Hist> findMo_HistEntities() {
        return findMo_HistEntities(true, -1, -1);
    }

    public List<Mo_Hist> findMo_HistEntities(int maxResults, int firstResult) {
        return findMo_HistEntities(false, maxResults, firstResult);
    }

    private List<Mo_Hist> findMo_HistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mo_Hist.class));
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

    public Mo_Hist findMo_Hist(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mo_Hist.class, id);
        } finally {
            em.close();
        }
    }

    public int getMo_HistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mo_Hist> rt = cq.from(Mo_Hist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
