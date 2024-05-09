package com.xdisx.customer.app.repository.db.filtering;

import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
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

    if (StringUtils.isNotBlank(customerPageRequestDto.getCustomerName())) {
      specifications.add(buildNameLikeSpecification(customerPageRequestDto.getCustomerName()));
    }

    if (StringUtils.isNotBlank(customerPageRequestDto.getEmail())) {
      specifications.add(buildEmailLikeSpecification(customerPageRequestDto.getEmail()));
    }

    if (StringUtils.isNotBlank(customerPageRequestDto.getPhoneNumber())) {
      specifications.add(
          buildPhoneNumberLikeSpecification(customerPageRequestDto.getPhoneNumber()));
    }

    return Specification.allOf(specifications);
  }

  private static Specification<CustomerEntity> buildPhoneNumberLikeSpecification(
      String phoneNumber) {
    return (root, query, builder) ->
        builder.like(
            builder.lower(root.get("phoneNumber")), "%" + phoneNumber.trim().toLowerCase() + "%");
  }

  private static Specification<CustomerEntity> buildEmailLikeSpecification(String email) {
    return (root, query, builder) ->
        builder.like(builder.lower(root.get("email")), "%" + email.trim().toLowerCase() + "%");
  }

  private static Specification<CustomerEntity> buildNameLikeSpecification(String customerName) {
    return (root, query, builder) -> {
      String pattern = "%" + customerName.trim().toLowerCase() + "%";
      Predicate firstNamePredicate = builder.like(builder.lower(root.get("firstName")), pattern);
      Predicate lastNamePredicate = builder.like(builder.lower(root.get("lastName")), pattern);
      return builder.or(firstNamePredicate, lastNamePredicate);
    };
  }

  private static Specification<CustomerEntity> buildEqualSpecificationCreatedOn(Object value) {
    LocalDateTime localDate = ((LocalDate) value).atStartOfDay();
    LocalDateTime localDate1 = localDate.plusDays(1).minusNanos(1);
    return (root, query, builder) -> builder.between(root.get("created"), localDate, localDate1);
  }
}
