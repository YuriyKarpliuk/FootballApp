package yurii.karpliuk.crudapp.dto.request;

import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.crudapp.model.OperationValue;

@Setter
@Getter
public class FootballersSearchRequest {
    private Integer age;
    private OperationValue ageOperation;
    private Integer weight;
    private OperationValue weightOperation;
    private Integer height;
    private OperationValue heightOperation;
    private Integer price;
    private OperationValue priceOperation;
}
