package br.com.springboot.tabelafipe.repository;

import br.com.springboot.tabelafipe.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {

}
