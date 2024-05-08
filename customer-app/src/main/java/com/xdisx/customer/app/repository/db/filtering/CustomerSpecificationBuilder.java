package com.xdisx.customer.app.repository.db.filtering;

import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class CustomerSpecificationBuilder {
  public Specification<CustomerEntity> getFilterSpecifications(
      CustomerPageRequestDto customerPageRequestDto) {
    List<Specification<CustomerEntity>> specifications = new ArrayList<>();

    if (customerPageRequestDto.getCreatedOn() != null) {
      specifications.add(buildEqualSpecificationCreatedOn(customerPageRequestDto.getCreatedOn()));
    }

    return Specification.allOf(specifications);
  }

  private static Specification<CustomerEntity> buildEqualSpecificationCreatedOn(Object value) {
    LocalDateTime localDate = ((LocalDate) value).atStartOfDay();
    LocalDateTime localDate1 = localDate.plusDays(1).minusNanos(1);
    return (root, query, builder) -> builder.between(root.get("created"), localDate, localDate1);
  }
}
