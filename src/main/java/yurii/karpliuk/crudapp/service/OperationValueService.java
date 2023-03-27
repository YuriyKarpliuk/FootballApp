package yurii.karpliuk.crudapp.service;

import yurii.karpliuk.crudapp.enums.FilterOperation;
import yurii.karpliuk.crudapp.model.OperationValue;

public interface OperationValueService {
    OperationValue getByName(FilterOperation filterOperation);
}
