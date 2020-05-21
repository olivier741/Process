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
import com.tatsinktechnologic.beans_entity.Promotion;
import com.tatsinktechnologic.beans_entity.Service;
import com.tatsinktechnologic.beans_entity.Action;
import java.util.HashSet;
import java.util.Set;
import com.tatsinktechnologic.beans_entity.Register;
import com.tatsinktechnologic.beans_entity.Charge_Hist_Success;
import com.tatsinktechnologic.beans_entity.Mo_Hist;
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
public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) {
        if (product.getListAction() == null) {
            product.setListAction(new HashSet<Action>());
        }
        if (product.getListRegister() == null) {
            product.setListRegister(new HashSet<Register>());
        }
        if (product.getListCharge_Hist() == null) {
            product.setListCharge_Hist(new HashSet<Charge_Hist_Success>());
        }
        if (product.getListMo_Hist() == null) {
            product.setListMo_Hist(new HashSet<Mo_Hist>());
        }
        if (product.getListMt_Hist() == null) {
            product.setListMt_Hist(new HashSet<Mt_Hist>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promotion promotion = product.getPromotion();
            if (promotion != null) {
                promotion = em.getReference(promotion.getClass(), promotion.getPromotion_id());
                product.setPromotion(promotion);
            }
            Service service = product.getService();
            if (service != null) {
                service = em.getReference(service.getClass(), service.getService_id());
                product.setService(service);
            }
            Set<Action> attachedListAction = new HashSet<Action>();
            for (Action listActionActionToAttach : product.getListAction()) {
                listActionActionToAttach = em.getReference(listActionActionToAttach.getClass(), listActionActionToAttach.getAction_id());
                attachedListAction.add(listActionActionToAttach);
            }
            product.setListAction(attachedListAction);
            Set<Register> attachedListRegister = new HashSet<Register>();
            for (Register listRegisterRegisterToAttach : product.getListRegister()) {
                listRegisterRegisterToAttach = em.getReference(listRegisterRegisterToAttach.getClass(), listRegisterRegisterToAttach.getRegister_id());
                attachedListRegister.add(listRegisterRegisterToAttach);
            }
            product.setListRegister(attachedListRegister);
            Set<Charge_Hist_Success> attachedListCharge_Hist = new HashSet<Charge_Hist_Success>();
            for (Charge_Hist_Success listCharge_HistCharge_Hist_SuccessToAttach : product.getListCharge_Hist()) {
                listCharge_HistCharge_Hist_SuccessToAttach = em.getReference(listCharge_HistCharge_Hist_SuccessToAttach.getClass(), listCharge_HistCharge_Hist_SuccessToAttach.getCharge_his_id());
                attachedListCharge_Hist.add(listCharge_HistCharge_Hist_SuccessToAttach);
            }
            product.setListCharge_Hist(attachedListCharge_Hist);
            Set<Mo_Hist> attachedListMo_Hist = new HashSet<Mo_Hist>();
            for (Mo_Hist listMo_HistMo_HistToAttach : product.getListMo_Hist()) {
                listMo_HistMo_HistToAttach = em.getReference(listMo_HistMo_HistToAttach.getClass(), listMo_HistMo_HistToAttach.getMo_his_id());
                attachedListMo_Hist.add(listMo_HistMo_HistToAttach);
            }
            product.setListMo_Hist(attachedListMo_Hist);
            Set<Mt_Hist> attachedListMt_Hist = new HashSet<Mt_Hist>();
            for (Mt_Hist listMt_HistMt_HistToAttach : product.getListMt_Hist()) {
                listMt_HistMt_HistToAttach = em.getReference(listMt_HistMt_HistToAttach.getClass(), listMt_HistMt_HistToAttach.getMt_his_id());
                attachedListMt_Hist.add(listMt_HistMt_HistToAttach);
            }
            product.setListMt_Hist(attachedListMt_Hist);
            em.persist(product);
            if (promotion != null) {
                promotion.getListProduct().add(product);
                promotion = em.merge(promotion);
            }
            if (service != null) {
                service.getListProduct().add(product);
                service = em.merge(service);
            }
            for (Action listActionAction : product.getListAction()) {
                Product oldProductOfListActionAction = listActionAction.getProduct();
                listActionAction.setProduct(product);
                listActionAction = em.merge(listActionAction);
                if (oldProductOfListActionAction != null) {
                    oldProductOfListActionAction.getListAction().remove(listActionAction);
                    oldProductOfListActionAction = em.merge(oldProductOfListActionAction);
                }
            }
            for (Register listRegisterRegister : product.getListRegister()) {
                Product oldProductOfListRegisterRegister = listRegisterRegister.getProduct();
                listRegisterRegister.setProduct(product);
                listRegisterRegister = em.merge(listRegisterRegister);
                if (oldProductOfListRegisterRegister != null) {
                    oldProductOfListRegisterRegister.getListRegister().remove(listRegisterRegister);
                    oldProductOfListRegisterRegister = em.merge(oldProductOfListRegisterRegister);
                }
            }
            for (Charge_Hist_Success listCharge_HistCharge_Hist_Success : product.getListCharge_Hist()) {
                Product oldProductOfListCharge_HistCharge_Hist_Success = listCharge_HistCharge_Hist_Success.getProduct();
                listCharge_HistCharge_Hist_Success.setProduct(product);
                listCharge_HistCharge_Hist_Success = em.merge(listCharge_HistCharge_Hist_Success);
                if (oldProductOfListCharge_HistCharge_Hist_Success != null) {
                    oldProductOfListCharge_HistCharge_Hist_Success.getListCharge_Hist().remove(listCharge_HistCharge_Hist_Success);
                    oldProductOfListCharge_HistCharge_Hist_Success = em.merge(oldProductOfListCharge_HistCharge_Hist_Success);
                }
            }
            for (Mo_Hist listMo_HistMo_Hist : product.getListMo_Hist()) {
                Product oldProductOfListMo_HistMo_Hist = listMo_HistMo_Hist.getProduct();
                listMo_HistMo_Hist.setProduct(product);
                listMo_HistMo_Hist = em.merge(listMo_HistMo_Hist);
                if (oldProductOfListMo_HistMo_Hist != null) {
                    oldProductOfListMo_HistMo_Hist.getListMo_Hist().remove(listMo_HistMo_Hist);
                    oldProductOfListMo_HistMo_Hist = em.merge(oldProductOfListMo_HistMo_Hist);
                }
            }
            for (Mt_Hist listMt_HistMt_Hist : product.getListMt_Hist()) {
                Product oldProductOfListMt_HistMt_Hist = listMt_HistMt_Hist.getProduct();
                listMt_HistMt_Hist.setProduct(product);
                listMt_HistMt_Hist = em.merge(listMt_HistMt_Hist);
                if (oldProductOfListMt_HistMt_Hist != null) {
                    oldProductOfListMt_HistMt_Hist.getListMt_Hist().remove(listMt_HistMt_Hist);
                    oldProductOfListMt_HistMt_Hist = em.merge(oldProductOfListMt_HistMt_Hist);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getProduct_id());
            Promotion promotionOld = persistentProduct.getPromotion();
            Promotion promotionNew = product.getPromotion();
            Service serviceOld = persistentProduct.getService();
            Service serviceNew = product.getService();
            Set<Action> listActionOld = persistentProduct.getListAction();
            Set<Action> listActionNew = product.getListAction();
            Set<Register> listRegisterOld = persistentProduct.getListRegister();
            Set<Register> listRegisterNew = product.getListRegister();
            Set<Charge_Hist_Success> listCharge_HistOld = persistentProduct.getListCharge_Hist();
            Set<Charge_Hist_Success> listCharge_HistNew = product.getListCharge_Hist();
            Set<Mo_Hist> listMo_HistOld = persistentProduct.getListMo_Hist();
            Set<Mo_Hist> listMo_HistNew = product.getListMo_Hist();
            Set<Mt_Hist> listMt_HistOld = persistentProduct.getListMt_Hist();
            Set<Mt_Hist> listMt_HistNew = product.getListMt_Hist();
            if (promotionNew != null) {
                promotionNew = em.getReference(promotionNew.getClass(), promotionNew.getPromotion_id());
                product.setPromotion(promotionNew);
            }
            if (serviceNew != null) {
                serviceNew = em.getReference(serviceNew.getClass(), serviceNew.getService_id());
                product.setService(serviceNew);
            }
            Set<Action> attachedListActionNew = new HashSet<Action>();
            for (Action listActionNewActionToAttach : listActionNew) {
                listActionNewActionToAttach = em.getReference(listActionNewActionToAttach.getClass(), listActionNewActionToAttach.getAction_id());
                attachedListActionNew.add(listActionNewActionToAttach);
            }
            listActionNew = attachedListActionNew;
            product.setListAction(listActionNew);
            Set<Register> attachedListRegisterNew = new HashSet<Register>();
            for (Register listRegisterNewRegisterToAttach : listRegisterNew) {
                listRegisterNewRegisterToAttach = em.getReference(listRegisterNewRegisterToAttach.getClass(), listRegisterNewRegisterToAttach.getRegister_id());
                attachedListRegisterNew.add(listRegisterNewRegisterToAttach);
            }
            listRegisterNew = attachedListRegisterNew;
            product.setListRegister(listRegisterNew);
            Set<Charge_Hist_Success> attachedListCharge_HistNew = new HashSet<Charge_Hist_Success>();
            for (Charge_Hist_Success listCharge_HistNewCharge_Hist_SuccessToAttach : listCharge_HistNew) {
                listCharge_HistNewCharge_Hist_SuccessToAttach = em.getReference(listCharge_HistNewCharge_Hist_SuccessToAttach.getClass(), listCharge_HistNewCharge_Hist_SuccessToAttach.getCharge_his_id());
                attachedListCharge_HistNew.add(listCharge_HistNewCharge_Hist_SuccessToAttach);
            }
            listCharge_HistNew = attachedListCharge_HistNew;
            product.setListCharge_Hist(listCharge_HistNew);
            Set<Mo_Hist> attachedListMo_HistNew = new HashSet<Mo_Hist>();
            for (Mo_Hist listMo_HistNewMo_HistToAttach : listMo_HistNew) {
                listMo_HistNewMo_HistToAttach = em.getReference(listMo_HistNewMo_HistToAttach.getClass(), listMo_HistNewMo_HistToAttach.getMo_his_id());
                attachedListMo_HistNew.add(listMo_HistNewMo_HistToAttach);
            }
            listMo_HistNew = attachedListMo_HistNew;
            product.setListMo_Hist(listMo_HistNew);
            Set<Mt_Hist> attachedListMt_HistNew = new HashSet<Mt_Hist>();
            for (Mt_Hist listMt_HistNewMt_HistToAttach : listMt_HistNew) {
                listMt_HistNewMt_HistToAttach = em.getReference(listMt_HistNewMt_HistToAttach.getClass(), listMt_HistNewMt_HistToAttach.getMt_his_id());
                attachedListMt_HistNew.add(listMt_HistNewMt_HistToAttach);
            }
            listMt_HistNew = attachedListMt_HistNew;
            product.setListMt_Hist(listMt_HistNew);
            product = em.merge(product);
            if (promotionOld != null && !promotionOld.equals(promotionNew)) {
                promotionOld.getListProduct().remove(product);
                promotionOld = em.merge(promotionOld);
            }
            if (promotionNew != null && !promotionNew.equals(promotionOld)) {
                promotionNew.getListProduct().add(product);
                promotionNew = em.merge(promotionNew);
            }
            if (serviceOld != null && !serviceOld.equals(serviceNew)) {
                serviceOld.getListProduct().remove(product);
                serviceOld = em.merge(serviceOld);
            }
            if (serviceNew != null && !serviceNew.equals(serviceOld)) {
                serviceNew.getListProduct().add(product);
                serviceNew = em.merge(serviceNew);
            }
            for (Action listActionOldAction : listActionOld) {
                if (!listActionNew.contains(listActionOldAction)) {
                    listActionOldAction.setProduct(null);
                    listActionOldAction = em.merge(listActionOldAction);
                }
            }
            for (Action listActionNewAction : listActionNew) {
                if (!listActionOld.contains(listActionNewAction)) {
                    Product oldProductOfListActionNewAction = listActionNewAction.getProduct();
                    listActionNewAction.setProduct(product);
                    listActionNewAction = em.merge(listActionNewAction);
                    if (oldProductOfListActionNewAction != null && !oldProductOfListActionNewAction.equals(product)) {
                        oldProductOfListActionNewAction.getListAction().remove(listActionNewAction);
                        oldProductOfListActionNewAction = em.merge(oldProductOfListActionNewAction);
                    }
                }
            }
            for (Register listRegisterOldRegister : listRegisterOld) {
                if (!listRegisterNew.contains(listRegisterOldRegister)) {
                    listRegisterOldRegister.setProduct(null);
                    listRegisterOldRegister = em.merge(listRegisterOldRegister);
                }
            }
            for (Register listRegisterNewRegister : listRegisterNew) {
                if (!listRegisterOld.contains(listRegisterNewRegister)) {
                    Product oldProductOfListRegisterNewRegister = listRegisterNewRegister.getProduct();
                    listRegisterNewRegister.setProduct(product);
                    listRegisterNewRegister = em.merge(listRegisterNewRegister);
                    if (oldProductOfListRegisterNewRegister != null && !oldProductOfListRegisterNewRegister.equals(product)) {
                        oldProductOfListRegisterNewRegister.getListRegister().remove(listRegisterNewRegister);
                        oldProductOfListRegisterNewRegister = em.merge(oldProductOfListRegisterNewRegister);
                    }
                }
            }
            for (Charge_Hist_Success listCharge_HistOldCharge_Hist_Success : listCharge_HistOld) {
                if (!listCharge_HistNew.contains(listCharge_HistOldCharge_Hist_Success)) {
                    listCharge_HistOldCharge_Hist_Success.setProduct(null);
                    listCharge_HistOldCharge_Hist_Success = em.merge(listCharge_HistOldCharge_Hist_Success);
                }
            }
            for (Charge_Hist_Success listCharge_HistNewCharge_Hist_Success : listCharge_HistNew) {
                if (!listCharge_HistOld.contains(listCharge_HistNewCharge_Hist_Success)) {
                    Product oldProductOfListCharge_HistNewCharge_Hist_Success = listCharge_HistNewCharge_Hist_Success.getProduct();
                    listCharge_HistNewCharge_Hist_Success.setProduct(product);
                    listCharge_HistNewCharge_Hist_Success = em.merge(listCharge_HistNewCharge_Hist_Success);
                    if (oldProductOfListCharge_HistNewCharge_Hist_Success != null && !oldProductOfListCharge_HistNewCharge_Hist_Success.equals(product)) {
                        oldProductOfListCharge_HistNewCharge_Hist_Success.getListCharge_Hist().remove(listCharge_HistNewCharge_Hist_Success);
                        oldProductOfListCharge_HistNewCharge_Hist_Success = em.merge(oldProductOfListCharge_HistNewCharge_Hist_Success);
                    }
                }
            }
            for (Mo_Hist listMo_HistOldMo_Hist : listMo_HistOld) {
                if (!listMo_HistNew.contains(listMo_HistOldMo_Hist)) {
                    listMo_HistOldMo_Hist.setProduct(null);
                    listMo_HistOldMo_Hist = em.merge(listMo_HistOldMo_Hist);
                }
            }
            for (Mo_Hist listMo_HistNewMo_Hist : listMo_HistNew) {
                if (!listMo_HistOld.contains(listMo_HistNewMo_Hist)) {
                    Product oldProductOfListMo_HistNewMo_Hist = listMo_HistNewMo_Hist.getProduct();
                    listMo_HistNewMo_Hist.setProduct(product);
                    listMo_HistNewMo_Hist = em.merge(listMo_HistNewMo_Hist);
                    if (oldProductOfListMo_HistNewMo_Hist != null && !oldProductOfListMo_HistNewMo_Hist.equals(product)) {
                        oldProductOfListMo_HistNewMo_Hist.getListMo_Hist().remove(listMo_HistNewMo_Hist);
                        oldProductOfListMo_HistNewMo_Hist = em.merge(oldProductOfListMo_HistNewMo_Hist);
                    }
                }
            }
            for (Mt_Hist listMt_HistOldMt_Hist : listMt_HistOld) {
                if (!listMt_HistNew.contains(listMt_HistOldMt_Hist)) {
                    listMt_HistOldMt_Hist.setProduct(null);
                    listMt_HistOldMt_Hist = em.merge(listMt_HistOldMt_Hist);
                }
            }
            for (Mt_Hist listMt_HistNewMt_Hist : listMt_HistNew) {
                if (!listMt_HistOld.contains(listMt_HistNewMt_Hist)) {
                    Product oldProductOfListMt_HistNewMt_Hist = listMt_HistNewMt_Hist.getProduct();
                    listMt_HistNewMt_Hist.setProduct(product);
                    listMt_HistNewMt_Hist = em.merge(listMt_HistNewMt_Hist);
                    if (oldProductOfListMt_HistNewMt_Hist != null && !oldProductOfListMt_HistNewMt_Hist.equals(product)) {
                        oldProductOfListMt_HistNewMt_Hist.getListMt_Hist().remove(listMt_HistNewMt_Hist);
                        oldProductOfListMt_HistNewMt_Hist = em.merge(oldProductOfListMt_HistNewMt_Hist);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = product.getProduct_id();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProduct_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            Promotion promotion = product.getPromotion();
            if (promotion != null) {
                promotion.getListProduct().remove(product);
                promotion = em.merge(promotion);
            }
            Service service = product.getService();
            if (service != null) {
                service.getListProduct().remove(product);
                service = em.merge(service);
            }
            Set<Action> listAction = product.getListAction();
            for (Action listActionAction : listAction) {
                listActionAction.setProduct(null);
                listActionAction = em.merge(listActionAction);
            }
            Set<Register> listRegister = product.getListRegister();
            for (Register listRegisterRegister : listRegister) {
                listRegisterRegister.setProduct(null);
                listRegisterRegister = em.merge(listRegisterRegister);
            }
            Set<Charge_Hist_Success> listCharge_Hist = product.getListCharge_Hist();
            for (Charge_Hist_Success listCharge_HistCharge_Hist_Success : listCharge_Hist) {
                listCharge_HistCharge_Hist_Success.setProduct(null);
                listCharge_HistCharge_Hist_Success = em.merge(listCharge_HistCharge_Hist_Success);
            }
            Set<Mo_Hist> listMo_Hist = product.getListMo_Hist();
            for (Mo_Hist listMo_HistMo_Hist : listMo_Hist) {
                listMo_HistMo_Hist.setProduct(null);
                listMo_HistMo_Hist = em.merge(listMo_HistMo_Hist);
            }
            Set<Mt_Hist> listMt_Hist = product.getListMt_Hist();
            for (Mt_Hist listMt_HistMt_Hist : listMt_Hist) {
                listMt_HistMt_Hist.setProduct(null);
                listMt_HistMt_Hist = em.merge(listMt_HistMt_Hist);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
