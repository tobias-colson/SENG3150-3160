package seng3150.group4.dao;

import org.springframework.stereotype.Repository;
import seng3150.group4.entity.FlightHistoryEntity;
import seng3150.group4.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDao implements IUserDao
{
    private EntityManager em;

    @Override
    public UserEntity getUserByUserName(String userName) {
        if (em == null)
        {
            em = (EntityManager) Persistence.createEntityManagerFactory("FlightPubPU").
                    createEntityManager();
        }

        Query query =  em.createQuery("SELECT u FROM UserEntity u WHERE u.username=:username");
        query.setParameter("username", userName);
        try
        {
            return (UserEntity) query.getSingleResult();
        }
        catch (NoResultException e)
        { // Username does not exist
           return null;
        }
    }

    @Override
    public void createNewUser(String username, String title, String firstname, String lastname, String phonenum, String dob, String email, String hashedPassword)
    {
        if (em == null)
        {
            em = (EntityManager) Persistence.createEntityManagerFactory("FlightPubPU").
                    createEntityManager();
        }

        em.getTransaction().begin();
        // Create new user
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setTitle(title);
        newUser.setFirstName(firstname);
        newUser.setLastName(lastname);
        newUser.setPhoneNumber(phonenum);
        newUser.setDOB(dob);
        newUser.setEmail(email);
        newUser.setPasswordHash(hashedPassword);

        em.persist(newUser);
        em.getTransaction().commit();
    }

    @Override
    public void updateUser(UserEntity currentUser, String title, String firstname, String lastname, String phonenum, String dob, String email, String password)
    {
        if (em == null)
        {
            em = (EntityManager) Persistence.createEntityManagerFactory("FlightPubPU").
                    createEntityManager();
        }

        em.getTransaction().begin();

        // Update existing user
        if (!(title.equals(""))) currentUser.setTitle(title);
        if (!(firstname.equals(""))) currentUser.setFirstName(firstname);
        if (!(lastname.equals(""))) currentUser.setLastName(lastname);
        if (!(phonenum.equals(""))) currentUser.setPhoneNumber(phonenum);
        if (!(dob.equals(""))) currentUser.setDOB(dob);
        if (!(email.equals(""))) currentUser.setEmail(email);
        if (!(password.equals(""))) currentUser.setPasswordHash(password);

        em.persist(currentUser);
        em.getTransaction().commit();
    }

    @Override
    public List<FlightHistoryEntity> getFlightHistory(UserEntity currentUser)
    {
        if (em == null)
        {
            em = (EntityManager) Persistence.createEntityManagerFactory("FlightPubPU").
                    createEntityManager();
        }

        Query historyQuery = em.createQuery("SELECT f FROM FlightHistoryEntity f WHERE f.id.userID=:userId");
        historyQuery.setParameter("userId", currentUser.getId());
        return historyQuery.getResultList();
    }
}
