package br.com.springboot.tabelafipe.repository;

import java.util.List;

import br.com.springboot.tabelafipe.entity.VehicleEntity;
import br.com.springboot.tabelafipe.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

//@Query(value = "select v from VehicleEntity v " +
 //       "where v.renavam =:renavam")
//VehicleEntity findByRenavan(@Param("renavam") String renavam);

@QueryHints({
        @QueryHint(
                name = "javax.persistence.lock.timeout",value = "true")
})
@Lock(LockModeType.PESSIMISTIC_WRITE)
List<VehicleEntity> findAllByStatus(Status status);

List<VehicleEntity> findAllByOrderBySubscriptionDateDesc();

}
