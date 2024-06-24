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
import java.util.Date;
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
            Integer documentTypeId, String identityDocument, Date birthDate,
            Integer genderId, String status, String sortBy, String sortDirection,
            int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> queryRoot = criteriaQuery.from(Customer.class);

        // Build predicate dynamically based on provided parameters
        Predicate predicate = buildPredicate(criteriaBuilder, queryRoot, id, name, paternal, maternal,
                documentTypeId, identityDocument, birthDate, genderId, status);

        // Add order by clause (if sort parameters are provided)
        if (sortBy != null && sortDirection != null) {
            criteriaQuery.orderBy(buildOrderClause(criteriaBuilder, queryRoot, sortBy, sortDirection));
        } else {
            // Default sorting (optional, can be removed if not needed)
            criteriaQuery.orderBy(criteriaBuilder.asc(queryRoot.get("id")));
        }

        criteriaQuery.select(queryRoot).where(predicate);

        TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public long count(String id, String name, String paternal, String maternal,
            Integer documentTypeId, String identityDocument, Date birthDate,
            Integer genderId, String status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Customer> queryRoot = criteriaQuery.from(Customer.class);

        // Build predicate dynamically based on provided parameters
        Predicate predicate = buildPredicate(criteriaBuilder, queryRoot, id, name, paternal, maternal,
                documentTypeId, identityDocument, birthDate, genderId, status);

        criteriaQuery.select(criteriaBuilder.count(queryRoot)).where(predicate);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private Predicate buildPredicate(CriteriaBuilder cb, Root<Customer> root, String id, String name, String paternal, String maternal,
            Integer documentTypeId, String identityDocument, Date birthDate,
            Integer genderId, String status) {
        Predicate predicate = cb.conjunction();

        // Add predicates based on provided parameters
        if (!AppTools.isBlank(id)) {
            predicate = cb.and(predicate, cb.like(root.get("id").as(String.class), "%" + id.toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(name)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(paternal)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("paternal")), "%" + paternal.toUpperCase() + "%"));
        }

        if (!AppTools.isBlank(maternal)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("maternal")), "%" + maternal.toUpperCase() + "%"));
        }

        if (documentTypeId != null && documentTypeId != -1) {
            predicate = cb.and(predicate, cb.equal(root.get("documentType").get("id"), documentTypeId));
        }

        if (!AppTools.isBlank(identityDocument)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("identityDocument")), "%" + identityDocument.toUpperCase() + "%"));
        }

        if (birthDate != null) {
            predicate = cb.and(predicate, cb.equal(root.get("birthDate"), birthDate));
        }

        if (genderId != null && genderId != -1) {
            predicate = cb.and(predicate, cb.equal(root.get("gender").get("id"), genderId));
        }

        if (!AppTools.isBlank(status)) {
            predicate = cb.and(predicate, cb.like(cb.upper(root.get("status")), "%" + status.toUpperCase() + "%"));
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
                return cb.asc(root.get("id"));
        }
    }

    /*@Override
    public List<Customer> filter(String id, String name, String paternal, String maternal,
            Integer documentTypeId, String identityDocument, Date birthDate,
            Integer genderId, String status, String sortBy, String sortDirection,
            int page, int size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> queryRoot = criteriaQuery.from(Customer.class);

        // Build predicate for filtering
        Predicate predicate = criteriaBuilder.conjunction();

        // Add filters based on provided parameters
        if (!AppTools.isBlank(id)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(queryRoot.get("id").as(String.class), "%" + id.toUpperCase() + "%"));
        }
        if (!AppTools.isBlank(name)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("name")), "%" + name.toUpperCase() + "%"));
        }
        if (!AppTools.isBlank(paternal)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("paternal")), "%" + paternal.toUpperCase() + "%"));
        }
        if (!AppTools.isBlank(maternal)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("maternal")), "%" + maternal.toUpperCase() + "%"));
        }
        if (documentTypeId != null && documentTypeId != -1) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(queryRoot.get("documentType").
                            get("id"), documentTypeId));
        }
        if (!AppTools.isBlank(identityDocument)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("identityDocument")), "%" + identityDocument.toUpperCase() + "%"));
        }
        if (birthDate != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    equal(queryRoot.get("birthDate"), birthDate));
        }
        if (genderId != null && genderId != -1) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    equal(queryRoot.get("gender").get("id"), genderId));
        }
        if (!AppTools.isBlank(status)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("status")), "%" + status.toUpperCase() + "%"));
        }

        criteriaQuery.select(queryRoot).where(predicate);

        // Add order by clause (if sort parameters are provided)
        if (sortBy != null && sortDirection != null) {
            switch (sortBy) {
                case "id":
                    criteriaQuery.orderBy(sortDirection.equals("ASC")
                            ? criteriaBuilder.asc(queryRoot.get("id"))
                            : criteriaBuilder.desc(queryRoot.get("id")));
                    break;
                case "name":
                    criteriaQuery.orderBy(sortDirection.equals("ASC")
                            ? criteriaBuilder.asc(queryRoot.get("name"))
                            : criteriaBuilder.desc(queryRoot.get("name")));
                    break;
                case "paternal":
                    criteriaQuery.orderBy(sortDirection.equals("ASC")
                            ? criteriaBuilder.asc(queryRoot.get("paternal"))
                            : criteriaBuilder.desc(queryRoot.get("paternal")));
                    break;
                case "maternal":
                    criteriaQuery.orderBy(sortDirection.equals("ASC")
                            ? criteriaBuilder.asc(queryRoot.get("maternal"))
                            : criteriaBuilder.desc(queryRoot.get("maternal")));
                    break;
                case "documentType":
                    criteriaQuery.orderBy(sortDirection.equals("ASC")
                            ? criteriaBuilder.asc(queryRoot.get("documentType").get("name"))
                            : criteriaBuilder.desc(queryRoot.get("documentType").get("name")));
                    break;
                case "identityDocument":
                    criteriaQuery.orderBy(sortDirection.equals("ASC")
                            ? criteriaBuilder.asc(queryRoot.get("identityDocument"))
                            : criteriaBuilder.desc(queryRoot.get("identityDocument")));
                    break;
                case "birthDate":
                    criteriaQuery.orderBy(sortDirection.equals("ASC")
                            ? criteriaBuilder.asc(queryRoot.get("birthDate"))
                            : criteriaBuilder.desc(queryRoot.get("birthDate")));
                    break;
                case "gender":
                    criteriaQuery.orderBy(sortDirection.equals("ASC")
                            ? criteriaBuilder.asc(queryRoot.get("gender").get("name"))
                            : criteriaBuilder.desc(queryRoot.get("gender").get("name")));
                    break;
                case "status":
                    criteriaQuery.orderBy(sortDirection.equals("ASC")
                            ? criteriaBuilder.asc(queryRoot.get("status"))
                            : criteriaBuilder.desc(queryRoot.get("status")));
                    break;
                default:
                    criteriaQuery.orderBy(sortDirection.equals("DESC")
                            ? criteriaBuilder.asc(queryRoot.get("id"))
                            : criteriaBuilder.desc(queryRoot.get("id")));
                    break;
            }
        }

        TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public long count(String id, String name, String paternal, String maternal, Integer documentTypeId, String identityDocument, Date birthDate, Integer genderId, String status) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Customer> queryRoot = criteriaQuery.from(Customer.class);

        // Build predicate for filtering
        Predicate predicate = criteriaBuilder.conjunction();

        // Add filters based on provided parameters
        if (!AppTools.isBlank(id)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(queryRoot.get("id").as(String.class), "%" + id.toUpperCase() + "%"));
        }
        if (!AppTools.isBlank(name)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("name")), "%" + name.toUpperCase() + "%"));
        }
        if (!AppTools.isBlank(paternal)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("paternal")), "%" + paternal.toUpperCase() + "%"));
        }
        if (!AppTools.isBlank(maternal)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("maternal")), "%" + maternal.toUpperCase() + "%"));
        }
        if (documentTypeId != null && documentTypeId != -1) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(queryRoot.get("documentType").
                            get("id"), documentTypeId));
        }
        if (!AppTools.isBlank(identityDocument)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("identityDocument")), "%" + identityDocument.toUpperCase() + "%"));
        }
        if (birthDate != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    equal(queryRoot.get("birthDate"), birthDate));
        }
        if (genderId != null && genderId != -1) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    equal(queryRoot.get("gender").get("id"), genderId));
        }
        if (!AppTools.isBlank(status)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.
                    like(criteriaBuilder.upper(queryRoot.get("status")), "%" + status.toUpperCase() + "%"));
        }

        criteriaQuery.select(criteriaBuilder.count(queryRoot)).where(predicate);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }*/
}
