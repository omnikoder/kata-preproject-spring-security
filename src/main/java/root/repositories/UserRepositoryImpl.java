package root.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import root.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<User> getUsers() {
        return entityManager
                .createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public boolean isExisting(String email) {
        return entityManager
                .createQuery("select u from User u where u.email = :email")
                .setParameter("email", email)
                .getResultList()
                .size() > 0;
    }

    @Override
    public void validateEmail(String email, BindingResult bindingResult) {
        if (isExisting(email)) {
            bindingResult.addError(new FieldError(
                    "user",
                    "email",
                    email,
                    false,
                    null,
                    null,
                    "* Пользователь с таким email уже зарегистрирован"
            ));
        }
    }
}
