package ma.octo.assignement.Services;

import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface TransferService {
    public List<Transfer> listOfTransfers();
    public void transferMoney(TransferDto dto) throws CompteNonExistantException, TransactionException;
}
