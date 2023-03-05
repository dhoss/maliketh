package in.stonecolddev.maliketh.cms.api.user;

import in.stonecolddev.maliketh.cms.api.entry.Entry;
import in.stonecolddev.maliketh.cms.api.entry.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User find(Integer id) {
    return userRepository.findById(id).orElseThrow();
  }

  public User create(User user) {
    return userRepository.save(user);
  }

  public Set<Entry> entries(Integer userId) {
    return userRepository.entries(userId);
  }
}