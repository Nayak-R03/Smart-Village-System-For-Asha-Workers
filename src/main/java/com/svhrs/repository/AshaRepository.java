//package com.svhrs.repository;
//
//import com.svhrs.model.Asha;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface AshaRepository extends JpaRepository<Asha, Long> {
//    Optional<Asha> findByAshaId(Long ashaId);
//    Optional<Asha> findByUsername(String username);
//}
package com.svhrs.repository;

import com.svhrs.model.Asha;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AshaRepository extends JpaRepository<Asha, Long> {
    Optional<Asha> findByAshaId(Long ashaId);
    boolean existsByAshaId(Long ashaId);
}
