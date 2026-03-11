package fincance_manager.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Importante adicionar
import org.springframework.stereotype.Repository;

import fincance_manager.dto.ReportCategoryDTO;
import fincance_manager.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = :type AND " +
           "MONTH(t.date) = :month AND YEAR(t.date) = :year")
    BigDecimal sumPerMonth(@Param("userId") long userId, @Param("type") String type, @Param("month") int month, @Param("year") int year);

    @Query("SELECT new fincance_manager.dto.ReportCategoryDTO(t.category.name, SUM(t.amount)) " + 
           "FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.type = 'EXPENSE' " +
           "GROUP BY t.category.name")
    List<ReportCategoryDTO> sumByCategory(@Param("userId") long userId);
    
    List<Transaction> findByUserIdAndDateBetween(long userId, LocalDate startDate, LocalDate endDate);
}