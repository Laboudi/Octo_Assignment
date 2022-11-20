package ma.octo.assignement.web;

import lombok.RequiredArgsConstructor;
import ma.octo.assignement.ServiceImpl.AuditServiceImpl;
import ma.octo.assignement.ServiceImpl.CompteServiceImpl;
import ma.octo.assignement.ServiceImpl.UtilisateurServiceImpl;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.ServiceImpl.TransferServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transfers")
class TransferController {

    private final TransferServiceImpl transferService;
    private final AuditServiceImpl auditService;
    private final UtilisateurServiceImpl utilisateurService;
    private final CompteServiceImpl compteService;


    @GetMapping("/listTransferts")
    ResponseEntity<List<Transfer>> loadAllTransfers() {
        return ResponseEntity.ok(transferService.listOfTransfers());
    }

    @GetMapping("/listAccounts")
    public ResponseEntity<List<Compte>> loadAllUsers(){
        return ResponseEntity.ok().body(compteService.Comptes());
    }

    @GetMapping("/listUtilisateurs")
    public ResponseEntity<List<Utilisateur>> loadAllUtilisateur(){
        return ResponseEntity.ok(utilisateurService.Utilisateurs());
    }

    @PostMapping("/transferTransaction")
    public void createTransaction(@RequestBody TransferDto transferDto)
            throws CompteNonExistantException, TransactionException {
        transferService.transferMoney(transferDto);
        auditService.auditTransfer("Transfer depuis " + transferDto.getNrCompteEmetteur() + " vers " + transferDto
                        .getNrCompteBeneficiaire() + " d'un montant de " + transferDto.getMontant()
                        .toString());
    }
}
