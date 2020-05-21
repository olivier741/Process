/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao_repository;

import com.tatsinktechnologic.beans_entity.ChatGroup;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tatsinktechnologic.beans_entity.ChatGroup_Register;
import com.tatsinktechnologic.dao_repository.exceptions.IllegalOrphanException;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author olivier.tatsinkou
 */
public class ChatGroupJpaController implements Serializable {

    public ChatGroupJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ChatGroup chatGroup) {
        if (chatGroup.getListChatGroup_Register() == null) {
            chatGroup.setListChatGroup_Register(new HashSet<ChatGroup_Register>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<ChatGroup_Register> attachedListChatGroup_Register = new HashSet<ChatGroup_Register>();
            for (ChatGroup_Register listChatGroup_RegisterChatGroup_RegisterToAttach : chatGroup.getListChatGroup_Register()) {
                listChatGroup_RegisterChatGroup_RegisterToAttach = em.getReference(listChatGroup_RegisterChatGroup_RegisterToAttach.getClass(), listChatGroup_RegisterChatGroup_RegisterToAttach.getChatgroup_reg_id());
                attachedListChatGroup_Register.add(listChatGroup_RegisterChatGroup_RegisterToAttach);
            }
            chatGroup.setListChatGroup_Register(attachedListChatGroup_Register);
            em.persist(chatGroup);
            for (ChatGroup_Register listChatGroup_RegisterChatGroup_Register : chatGroup.getListChatGroup_Register()) {
                ChatGroup oldChatgroupOfListChatGroup_RegisterChatGroup_Register = listChatGroup_RegisterChatGroup_Register.getChatgroup();
                listChatGroup_RegisterChatGroup_Register.setChatgroup(chatGroup);
                listChatGroup_RegisterChatGroup_Register = em.merge(listChatGroup_RegisterChatGroup_Register);
                if (oldChatgroupOfListChatGroup_RegisterChatGroup_Register != null) {
                    oldChatgroupOfListChatGroup_RegisterChatGroup_Register.getListChatGroup_Register().remove(listChatGroup_RegisterChatGroup_Register);
                    oldChatgroupOfListChatGroup_RegisterChatGroup_Register = em.merge(oldChatgroupOfListChatGroup_RegisterChatGroup_Register);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ChatGroup chatGroup) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChatGroup persistentChatGroup = em.find(ChatGroup.class, chatGroup.getChatgroup_id());
            Set<ChatGroup_Register> listChatGroup_RegisterOld = persistentChatGroup.getListChatGroup_Register();
            Set<ChatGroup_Register> listChatGroup_RegisterNew = chatGroup.getListChatGroup_Register();
            List<String> illegalOrphanMessages = null;
            for (ChatGroup_Register listChatGroup_RegisterOldChatGroup_Register : listChatGroup_RegisterOld) {
                if (!listChatGroup_RegisterNew.contains(listChatGroup_RegisterOldChatGroup_Register)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ChatGroup_Register " + listChatGroup_RegisterOldChatGroup_Register + " since its chatgroup field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<ChatGroup_Register> attachedListChatGroup_RegisterNew = new HashSet<ChatGroup_Register>();
            for (ChatGroup_Register listChatGroup_RegisterNewChatGroup_RegisterToAttach : listChatGroup_RegisterNew) {
                listChatGroup_RegisterNewChatGroup_RegisterToAttach = em.getReference(listChatGroup_RegisterNewChatGroup_RegisterToAttach.getClass(), listChatGroup_RegisterNewChatGroup_RegisterToAttach.getChatgroup_reg_id());
                attachedListChatGroup_RegisterNew.add(listChatGroup_RegisterNewChatGroup_RegisterToAttach);
            }
            listChatGroup_RegisterNew = attachedListChatGroup_RegisterNew;
            chatGroup.setListChatGroup_Register(listChatGroup_RegisterNew);
            chatGroup = em.merge(chatGroup);
            for (ChatGroup_Register listChatGroup_RegisterNewChatGroup_Register : listChatGroup_RegisterNew) {
                if (!listChatGroup_RegisterOld.contains(listChatGroup_RegisterNewChatGroup_Register)) {
                    ChatGroup oldChatgroupOfListChatGroup_RegisterNewChatGroup_Register = listChatGroup_RegisterNewChatGroup_Register.getChatgroup();
                    listChatGroup_RegisterNewChatGroup_Register.setChatgroup(chatGroup);
                    listChatGroup_RegisterNewChatGroup_Register = em.merge(listChatGroup_RegisterNewChatGroup_Register);
                    if (oldChatgroupOfListChatGroup_RegisterNewChatGroup_Register != null && !oldChatgroupOfListChatGroup_RegisterNewChatGroup_Register.equals(chatGroup)) {
                        oldChatgroupOfListChatGroup_RegisterNewChatGroup_Register.getListChatGroup_Register().remove(listChatGroup_RegisterNewChatGroup_Register);
                        oldChatgroupOfListChatGroup_RegisterNewChatGroup_Register = em.merge(oldChatgroupOfListChatGroup_RegisterNewChatGroup_Register);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = chatGroup.getChatgroup_id();
                if (findChatGroup(id) == null) {
                    throw new NonexistentEntityException("The chatGroup with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChatGroup chatGroup;
            try {
                chatGroup = em.getReference(ChatGroup.class, id);
                chatGroup.getChatgroup_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chatGroup with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<ChatGroup_Register> listChatGroup_RegisterOrphanCheck = chatGroup.getListChatGroup_Register();
            for (ChatGroup_Register listChatGroup_RegisterOrphanCheckChatGroup_Register : listChatGroup_RegisterOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ChatGroup (" + chatGroup + ") cannot be destroyed since the ChatGroup_Register " + listChatGroup_RegisterOrphanCheckChatGroup_Register + " in its listChatGroup_Register field has a non-nullable chatgroup field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(chatGroup);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ChatGroup> findChatGroupEntities() {
        return findChatGroupEntities(true, -1, -1);
    }

    public List<ChatGroup> findChatGroupEntities(int maxResults, int firstResult) {
        return findChatGroupEntities(false, maxResults, firstResult);
    }

    private List<ChatGroup> findChatGroupEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ChatGroup.class));
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

    public ChatGroup findChatGroup(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ChatGroup.class, id);
        } finally {
            em.close();
        }
    }

    public int getChatGroupCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ChatGroup> rt = cq.from(ChatGroup.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
