package yurii.karpliuk.crudapp.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import yurii.karpliuk.crudapp.enums.FilterOperation;
import yurii.karpliuk.crudapp.model.OperationValue;
import yurii.karpliuk.crudapp.repository.OperationValueRepository;
import yurii.karpliuk.crudapp.service.OperationValueService;
@Service
public class OperationValueServiceImpl implements OperationValueService {
    private OperationValueRepository operationValueRepository;

    public OperationValueServiceImpl(OperationValueRepository operationValueRepository) {
        this.operationValueRepository = operationValueRepository;
    }

    @Override
    public OperationValue getByName(FilterOperation filterOperation) {
        return operationValueRepository.findByName(filterOperation);
    }

    @PostConstruct
    public void init() {
        if (operationValueRepository.findAll().isEmpty()) {
            for (FilterOperation filterOperation : FilterOperation.values()) {
                OperationValue operationValue = new OperationValue();
                operationValue.setName(filterOperation);
                operationValueRepository.save(operationValue);
            }
        }
    }
}
