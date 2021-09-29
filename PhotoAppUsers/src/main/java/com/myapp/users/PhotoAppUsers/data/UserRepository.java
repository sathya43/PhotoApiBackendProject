package com.myapp.users.PhotoAppUsers.data;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends
        CrudRepository<UserEntity
        , Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}
