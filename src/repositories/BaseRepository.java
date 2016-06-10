package repositories;

import models.BaseModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository<T extends BaseModel> {
    protected Configuration configuration = null;
    protected ServiceRegistryBuilder registry = null;
    protected ServiceRegistry serviceRegistry = null;
    protected Class<T> tClass;

    protected BaseRepository() {
        try {
            configuration = new Configuration().configure("hibernate.cfg.xml");
            registry = new ServiceRegistryBuilder();
            registry.applySettings(configuration.getProperties());
            serviceRegistry = registry.buildServiceRegistry();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public T getById(int id) {
        return this.getAll().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }

    public List<T> getAll() {
        List<T> items = new ArrayList<T>();

        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            items = session.createCriteria(tClass).list();
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return items;
    }

    public void save(T item) {
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(item);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void delete(int id) {
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(getById(id));
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}