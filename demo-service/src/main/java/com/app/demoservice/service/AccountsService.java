package com.app.demoservice.service;

import com.app.demoservice.constant.ConstantMessage;
import com.app.demoservice.entity.Accounts;
import com.app.demoservice.exception.DataNotfoundException;
import com.app.demoservice.exception.InternalServerException;
import com.app.demoservice.model.ValidationResponse;
import com.app.demoservice.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsRepository accountsRepository;

    public ValidationResponse save(Accounts request){
        log.info("request save accounts = {} ",request);
        try{
            Accounts accounts = new Accounts();
            BeanUtils.copyProperties(request, accounts);
            accounts.setIsDeleted(0);
            accountsRepository.save(accounts);
            return ValidationResponse.builder()
                    .valid(true)
                    .build();
        }catch (Exception e){
            log.error("error save because : ",e.fillInStackTrace());
            throw new InternalServerException(ConstantMessage.MESSAGE_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public ValidationResponse update(Accounts request){
        log.info("request update accounts = {} ",request);
        try{

            accountsRepository.findById(request.getId())
                    .ifPresentOrElse(accounts -> {
                        accounts.setName(request.getName());
                        accounts.setPassword(Objects.isNull(request.getPassword()) ? accounts.getPassword() : request.getPassword());
                        accounts.setAddress(request.getAddress());
                        accounts.setRoles(request.getRoles());
                        accounts.setEmail(request.getEmail());
                        accounts.setPhoneNumber(request.getPhoneNumber());
                        accounts.setIsDeleted(accounts.getIsDeleted());
                        accountsRepository.save(accounts);
                    }, () -> {
                        log.error(String.format(ConstantMessage.MESSAGE_DATA_NOTFOUND, request.getId()));
                        throw new DataNotfoundException(String.format(ConstantMessage.MESSAGE_DATA_NOTFOUND,request.getId()),HttpStatus.NOT_FOUND.value());
                    });

            return ValidationResponse.builder()
                    .valid(true)
                    .build();
        }catch (Exception e){
            log.error("error save because : ",e.fillInStackTrace());
            throw new InternalServerException(ConstantMessage.MESSAGE_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public ValidationResponse delete(Long id){
        log.info("request delete accounts = {} ",id);
        try{

            accountsRepository.findById(id)
                    .ifPresentOrElse(accounts -> {
                        accounts.setIsDeleted(1);
                        accountsRepository.save(accounts);
                    }, () -> {
                        log.error(String.format(ConstantMessage.MESSAGE_DATA_NOTFOUND, id));
                        throw new DataNotfoundException(String.format(ConstantMessage.MESSAGE_DATA_NOTFOUND,id),HttpStatus.NOT_FOUND.value());
                    });
            return ValidationResponse.builder()
                    .valid(true)
                    .build();
        }catch (Exception e){
            log.error("error save because : ",e.fillInStackTrace());
            throw new InternalServerException(ConstantMessage.MESSAGE_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public Accounts getAccounts(Long id){
        return accountsRepository.findById(id)
                .orElseThrow(() -> new DataNotfoundException(String
                        .format(ConstantMessage.MESSAGE_DATA_NOTFOUND,id),
                        HttpStatus.NOT_FOUND.value()));
    }

    public Page<Accounts> search(Pageable pageable){
        log.info("request search data = {} ",pageable);
        return accountsRepository.findAll(pageable);
    }
}
