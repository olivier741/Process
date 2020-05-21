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
import com.tatsinktechnologic.beans_entity.ChatGroup;
import com.tatsinktechnologic.beans_entity.ChatGroup_Register;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author olivier.tatsinkou
 */
public class ChatGroup_RegisterJpaController implements Serializable {

    public ChatGroup_RegisterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ChatGroup_Register chatGroup_Register) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChatGroup chatgroup = chatGroup_Register.getChatgroup();
            if (chatgroup != null) {
                chatgroup = em.getReference(chatgroup.getClass(), chatgroup.getChatgroup_id());
                chatGroup_Register.setChatgroup(chatgroup);
            }
            em.persist(chatGroup_Register);
            if (chatgroup != null) {
                chatgroup.getListChatGroup_Register().add(chatGroup_Register);
                chatgroup = em.merge(chatgroup);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ChatGroup_Register chatGroup_Register) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChatGroup_Register persistentChatGroup_Register = em.find(ChatGroup_Register.class, chatGroup_Register.getChatgroup_reg_id());
            ChatGroup chatgroupOld = persistentChatGroup_Register.getChatgroup();
            ChatGroup chatgroupNew = chatGroup_Register.getChatgroup();
            if (chatgroupNew != null) {
                chatgroupNew = em.getReference(chatgroupNew.getClass(), chatgroupNew.getChatgroup_id());
                chatGroup_Register.setChatgroup(chatgroupNew);
            }
            chatGroup_Register = em.merge(chatGroup_Register);
            if (chatgroupOld != null && !chatgroupOld.equals(chatgroupNew)) {
                chatgroupOld.getListChatGroup_Register().remove(chatGroup_Register);
                chatgroupOld = em.merge(chatgroupOld);
            }
            if (chatgroupNew != null && !chatgroupNew.equals(chatgroupOld)) {
                chatgroupNew.getListChatGroup_Register().add(chatGroup_Register);
                chatgroupNew = em.merge(chatgroupNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = chatGroup_Register.getChatgroup_reg_id();
                if (findChatGroup_Register(id) == null) {
                    throw new NonexistentEntityException("The chatGroup_Register with id " + id + " no longer exists.");
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
            ChatGroup_Register chatGroup_Register;
            try {
                chatGroup_Register = em.getReference(ChatGroup_Register.class, id);
                chatGroup_Register.getChatgroup_reg_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chatGroup_Register with id " + id + " no longer exists.", enfe);
            }
            ChatGroup chatgroup = chatGroup_Register.getChatgroup();
            if (chatgroup != null) {
                chatgroup.getListChatGroup_Register().remove(chatGroup_Register);
                chatgroup = em.merge(chatgroup);
            }
            em.remove(chatGroup_Register);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ChatGroup_Register> findChatGroup_RegisterEntities() {
        return findChatGroup_RegisterEntities(true, -1, -1);
    }

    public List<ChatGroup_Register> findChatGroup_RegisterEntities(int maxResults, int firstResult) {
        return findChatGroup_RegisterEntities(false, maxResults, firstResult);
    }

    private List<ChatGroup_Register> findChatGroup_RegisterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ChatGroup_Register.class));
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

    public ChatGroup_Register findChatGroup_Register(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ChatGroup_Register.class, id);
        } finally {
            em.close();
        }
    }

    public int getChatGroup_RegisterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ChatGroup_Register> rt = cq.from(ChatGroup_Register.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
