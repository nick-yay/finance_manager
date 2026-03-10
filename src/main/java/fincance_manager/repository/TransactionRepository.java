package fincance_manager.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fincance_manager.dto.ReportCategoryDTO;
import fincance_manager.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = :type AND"+
    "MONTH(t.date) = :month AND YEAR(t.date) = :year")
    BigDecimal sumPerMonth(long userId, String type, int month, int year);

    @Query("SELECT new finance_manager.dto.ReportCategoryDTO(t.category.name, SUM(t.amount))"+ 
    "FROM Transaction t"+
    "WHERE t.user.id = :userId AND t.type = 'EXPENSE' GROUP BY t.category.name")
    List<ReportCategoryDTO> sumByCategory(long userId);
    
    List<Transaction> findByUserIdAndDateBetween(long userId, String type, LocalDate startDate, LocalDate endDate);
}
