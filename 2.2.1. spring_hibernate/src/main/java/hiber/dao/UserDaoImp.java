package hiber.dao;

import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public User getUserByCar (String model, int series) {
      User user = new User();
      try(Session session = sessionFactory.openSession()) {
         String HQL="from User where car.series = :series and car.model = :model";
         user = session.createQuery(HQL, User.class)
                 .setParameter("series", series)
                 .setParameter("model", model)
                 .getSingleResult();
      } catch (HibernateException e) {
         e.printStackTrace();
      }
      return user;
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

}
