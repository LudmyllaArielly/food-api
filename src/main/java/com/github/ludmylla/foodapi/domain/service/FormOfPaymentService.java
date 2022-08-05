package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.dtos.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.dtos.exceptions.FormOfPaymentNotFoundException;
import com.github.ludmylla.foodapi.domain.model.FormOfPayment;
import com.github.ludmylla.foodapi.domain.repository.FormOfPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormOfPaymentService {

    private static final String MSG_FORM_OF_PAYMENT_IN_USE =
            "Payment method code %d cannot be removed as it is in use.";

    @Autowired
    private FormOfPaymentRepository formOfPaymentRepository;

    @Transactional
    public FormOfPayment create(FormOfPayment formOfPayment){
        return formOfPaymentRepository.save(formOfPayment);
    }

    public List<FormOfPayment> findAll(){
        return formOfPaymentRepository.findAll();
    }

    public FormOfPayment findById(Long id){
        return formOfPaymentRepository.findById(id)
                .orElseThrow(() -> new FormOfPaymentNotFoundException(id));
    }

    @Transactional
    public FormOfPayment update(Long id, FormOfPayment formOfPayment){
        FormOfPayment formOfPaymentActual = findById(id);
        formOfPayment.setId(formOfPaymentActual.getId());
        return formOfPaymentRepository.save(formOfPayment);
    }

    @Transactional
    public void delete(Long id){
        try {
            findById(id);
            formOfPaymentRepository.deleteById(id);
            formOfPaymentRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new FormOfPaymentNotFoundException(id);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(String.format(MSG_FORM_OF_PAYMENT_IN_USE, id));
        }
    }
}
