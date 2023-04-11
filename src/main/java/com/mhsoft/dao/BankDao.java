package com.mhsoft.dao;

import com.mhsoft.model.DAOBank;
import com.mhsoft.model.DAOUser;
import org.springframework.data.repository.CrudRepository;

public interface BankDao  extends CrudRepository<DAOBank, String> {
    DAOBank findDAOBankByIdIs (String userid);


}
