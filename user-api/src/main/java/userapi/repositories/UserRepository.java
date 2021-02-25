package userapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import userapi.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserByUsername(String username);
    User findUserByEmail(String email);
    
}
