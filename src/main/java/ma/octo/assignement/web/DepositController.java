package ma.octo.assignement.web;

import lombok.RequiredArgsConstructor;
import ma.octo.assignement.ServiceImpl.AuditServiceImpl;
import ma.octo.assignement.ServiceImpl.DepositServiceImpl;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/depositsTransaction")
public class DepositController {

    private final DepositServiceImpl depositService;

    private final AuditServiceImpl auditService;

    @GetMapping("/listDeposits")
    public ResponseEntity<List<MoneyDeposit>> getAllMoneyDeposit(){
        return ResponseEntity.ok().body(depositService.getAllDeposit());
    }

    @PostMapping("/DepositTransaction")
    public void depositMoney(@RequestBody DepositDto depositDto) throws TransactionException, CompteNonExistantException {
        depositService.DepositMoney(depositDto);
        auditService.auditDeposit("Deposé depuis " + depositDto.getNom_prenom_emetteur() + " vers " + depositDto
                .getRib()+ " d'un montant de " + depositDto.getMontant()
                .toString()+" dans la date "+depositDto.getDateExecution());
    }
}
