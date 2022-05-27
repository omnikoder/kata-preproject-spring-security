package root.services;

import org.springframework.validation.BindingResult;
import root.entities.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(Long id);
    void save(User user);
    void update(User user);
    void delete(Long id);
    boolean isExisting(String email);
    void validateEmail(String email, BindingResult bindingResult);
}
