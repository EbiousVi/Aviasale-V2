package com.ebious.aviasale.repository;

import com.ebious.aviasale.domain.entity.UserProfiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfilesRepository extends JpaRepository<UserProfiles, Long> {

}
