/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.repository.impl;

import bo.com.bancounion.commons.AppTools;
import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.repository.IListCustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ListCustomerRepositoryImpl implements IListCustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ListCustomerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> filter(String id, String name, String paternal, String maternal,
            String documentType, String identityDocument, String birthDate,
            String gender, String status, String sortBy, String sortDirection,
            int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> queryRoot = criteriaQuery.from(Customer.class);
 
        Predicate predicate = buildPredicate(criteriaBuilder, queryRoot,
                id, name, paternal, maternal, documentType, identityDocument,
                birthDate, gender, status);
 
        if (sortBy == null || sortDirection == null) {
            sortBy = "ANY";
            sortDirection = "ANY";
        }
    
        criteriaQuery.orderBy(buildOrderClause(criteriaBuilder, queryRoot, sortBy, sortDirection));
        
        criteriaQuery.select(queryRoot).where(predicate);

        TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public long count(String id, String name, String paternal, String maternal,
            String documentType, String identityDocument, String birthDate,
            String gender, String status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Customer> queryRoot = criteriaQuery.from(Customer.class);

        // Build predicate dynamically based on provided parameters
        Predicate predicate = buildPredicate(criteriaBuilder, queryRoot,
                id, name, paternal, maternal, documentType, identityDocument,
                birthDate, gender, status);

        criteriaQuery.select(criteriaBuilder.count(queryRoot)).where(predicate);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private Predicate buildPredicate(CriteriaBuilder cb, Root<Customer> root,
            String id, String name, String paternal, String maternal,
            String documentType, String identityDocument, String birthDate,
            String gender, String status) {
        Predicate predicate = cb.conjunction();

        // Add predicates based on provided parameters
        if (!AppTools.isBlank(id)) {
            predicate = cb.and(predicate, cb.like(root.get("id").as(String.class), "%" + id.trim().toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(name)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("name")), "%" + name.trim().toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(paternal)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("paternal")), "%" + paternal.trim().toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(maternal)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("maternal")), "%" + maternal.trim().toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(documentType)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("documentType").get("name")), "%" + documentType.trim().toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(identityDocument)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("identityDocument")), "%" + identityDocument.trim().toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(birthDate)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("birthDate").as(String.class)), "%" + birthDate.trim().toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(gender)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("gender").get("name")), "%" + gender.trim().toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(status)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("status")), "%" + status.trim().toUpperCase() + "%"));
        }

        return predicate;
    }

    private Order buildOrderClause(CriteriaBuilder cb, Root<Customer> root, String sortBy, String sortDirection) {
        switch (sortBy) {
            case "id":
                return sortDirection.equals("ASC") ? cb.asc(root.get("id")) : cb.desc(root.get("id"));
            case "name":
                return sortDirection.equals("ASC") ? cb.asc(root.get("name")) : cb.desc(root.get("name"));
            case "paternal":
                return sortDirection.equals("ASC") ? cb.asc(root.get("paternal")) : cb.desc(root.get("paternal"));
            case "maternal":
                return sortDirection.equals("ASC") ? cb.asc(root.get("maternal")) : cb.desc(root.get("maternal"));
            case "documentType":
                return sortDirection.equals("ASC") ? cb.asc(root.get("documentType").get("name")) : cb.desc(root.get("documentType").get("name"));
            case "identityDocument":
                return sortDirection.equals("ASC") ? cb.asc(root.get("identityDocument")) : cb.desc(root.get("identityDocument"));
            case "birthDate":
                return sortDirection.equals("ASC") ? cb.asc(root.get("birthDate")) : cb.desc(root.get("birthDate"));
            case "gender":
                return sortDirection.equals("ASC") ? cb.asc(root.get("gender").get("name")) : cb.desc(root.get("gender").get("name"));
            case "status":
                return sortDirection.equals("ASC") ? cb.asc(root.get("status")) : cb.desc(root.get("status"));
            default:
                return cb.desc(root.get("id"));
        }
    }
 
}
