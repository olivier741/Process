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
import com.tatsinktechnologic.beans_entity.Product;
import com.tatsinktechnologic.beans_entity.Register;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author olivier.tatsinkou
 */
public class RegisterJpaController implements Serializable {

    public RegisterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Register register) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product = register.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProduct_id());
                register.setProduct(product);
            }
            em.persist(register);
            if (product != null) {
                product.getListRegister().add(register);
                product = em.merge(product);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Register register) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Register persistentRegister = em.find(Register.class, register.getRegister_id());
            Product productOld = persistentRegister.getProduct();
            Product productNew = register.getProduct();
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProduct_id());
                register.setProduct(productNew);
            }
            register = em.merge(register);
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getListRegister().remove(register);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getListRegister().add(register);
                productNew = em.merge(productNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = register.getRegister_id();
                if (findRegister(id) == null) {
                    throw new NonexistentEntityException("The register with id " + id + " no longer exists.");
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
            Register register;
            try {
                register = em.getReference(Register.class, id);
                register.getRegister_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The register with id " + id + " no longer exists.", enfe);
            }
            Product product = register.getProduct();
            if (product != null) {
                product.getListRegister().remove(register);
                product = em.merge(product);
            }
            em.remove(register);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Register> findRegisterEntities() {
        return findRegisterEntities(true, -1, -1);
    }

    public List<Register> findRegisterEntities(int maxResults, int firstResult) {
        return findRegisterEntities(false, maxResults, firstResult);
    }

    private List<Register> findRegisterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Register.class));
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

    public Register findRegister(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Register.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegisterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Register> rt = cq.from(Register.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
