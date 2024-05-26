package com.xdisx.customer.app.service.specs;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import com.xdisx.customer.app.repository.db.filtering.CustomerSpecificationBuilder;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class SpecsTest {
  @Test
  void testBuildEmailLikeSpecification() {
    Specification<CustomerEntity> spec =
        CustomerSpecificationBuilder.buildEmailLikeSpecification("example@example.com");

    CriteriaBuilder cb = mock(CriteriaBuilder.class);
    Root<CustomerEntity> root = mock(Root.class);
    CriteriaQuery<?> query = mock(CriteriaQuery.class);
    Predicate predicate = mock(Predicate.class);

    when(cb.like(any(Expression.class), anyString())).thenReturn(predicate);

    Path<String> emailPath = mock(Path.class);
    when(root.<String>get("email")).thenReturn(emailPath);
    when(cb.lower(emailPath)).thenReturn(emailPath);

    Predicate result = spec.toPredicate(root, query, cb);

    verify(cb).like(emailPath, "%example@example.com%");
    assertNotNull(result);
  }

  @Test
  void testBuildNameLikeSpecification() {
    Specification<CustomerEntity> spec =
        CustomerSpecificationBuilder.buildNameLikeSpecification("John");

    CriteriaBuilder cb = mock(CriteriaBuilder.class);
    Root<CustomerEntity> root = mock(Root.class);
    CriteriaQuery<?> query = mock(CriteriaQuery.class);
    Predicate predicate = mock(Predicate.class);

    lenient().when(cb.like(any(Expression.class), anyString())).thenReturn(predicate);

    Predicate result = spec.toPredicate(root, query, cb);

    ArgumentCaptor<Expression> expressionCaptor = ArgumentCaptor.forClass(Expression.class);
    ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
    verify(cb, times(2)).like(expressionCaptor.capture(), stringCaptor.capture());
    assertTrue(stringCaptor.getValue().contains("john"));

    verify(cb, times(2)).lower(root.get("firstName"));
    verify(cb, times(2)).lower(root.get("lastName"));
  }
}
