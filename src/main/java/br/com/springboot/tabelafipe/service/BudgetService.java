package br.com.springboot.tabelafipe.service;

import br.com.springboot.tabelafipe.dto.BudgetDTO;
import br.com.springboot.tabelafipe.dto.VehicleDTO;

public interface BudgetService {

    void updateBudget(BudgetDTO budgetDTO);

    void saveBudget(String cpf, BudgetDTO vehicle);

    void deleteBudget(String cpf, Long id);

}
