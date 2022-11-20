package ma.octo.assignement.Services;

import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface DepositService {
    public List<MoneyDeposit> getAllDeposit();
    public void DepositMoney(DepositDto dto) throws CompteNonExistantException, TransactionException;
}
