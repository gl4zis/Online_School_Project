package ru.school.profileservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.school.profileservice.model.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
