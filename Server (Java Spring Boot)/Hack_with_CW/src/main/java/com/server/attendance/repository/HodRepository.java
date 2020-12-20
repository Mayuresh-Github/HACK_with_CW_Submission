package com.server.attendance.repository;

import com.server.attendance.entity.HoDDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HodRepository extends JpaRepository<HoDDetail, String> {

    HoDDetail findByHodEmail(String hodEmail);

    HoDDetail findByHodEmailAndHodPassword(String hodEmail, String hodPassword);
}
