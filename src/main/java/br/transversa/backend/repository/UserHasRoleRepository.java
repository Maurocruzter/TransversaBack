package br.transversa.backend.repository;



import br.transversa.backend.model.UserHasRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserHasRoleRepository extends JpaRepository<UserHasRole, Long> {
    //Optional<Role> findByName(RoleName roleName);
}
