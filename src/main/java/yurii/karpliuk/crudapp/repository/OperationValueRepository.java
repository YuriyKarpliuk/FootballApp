package yurii.karpliuk.crudapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yurii.karpliuk.crudapp.enums.FilterOperation;
import yurii.karpliuk.crudapp.model.OperationValue;

public interface OperationValueRepository extends JpaRepository<OperationValue,Long> {
    OperationValue findByName(FilterOperation filterOperation);

}
