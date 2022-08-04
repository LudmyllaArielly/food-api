package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.input.FormOfPaymentInputModel;
import com.github.ludmylla.foodapi.domain.model.FormOfPayment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormOfPaymentInputDisassembler {

    @Autowired
    private ModelMapper mapper;

    public FormOfPayment toDomainModel(FormOfPaymentInputModel formOfPaymentInput){
        return mapper.map(formOfPaymentInput, FormOfPayment.class);
    }
    
}
