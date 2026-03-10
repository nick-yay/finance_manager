package fincance_manager.dto;

import java.math.BigDecimal;

public record ReportCategoryDTO (
    String categoryName,
    BigDecimal totalAmount
){}
