package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.FormOfPaymentModel;
import com.github.ludmylla.foodapi.domain.model.FormOfPayment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormOfPaymentModelAssembler {

    @Autowired
    private ModelMapper mapper;

    public FormOfPaymentModel toModel(FormOfPayment formOfPayment){
        return mapper.map(formOfPayment, FormOfPaymentModel.class);
    }

    public List<FormOfPaymentModel> toCollectionModel(List<FormOfPayment> formOfPayments) {
        return formOfPayments.stream()
                .map(formOfPayment -> toModel(formOfPayment))
                .collect(Collectors.toList());
    }
}
