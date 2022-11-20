package ma.octo.assignement.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.Services.TransferService;
import ma.octo.assignement.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private static final int MONTANT_MAXIMAL = 10000;

    private final CompteRepository compteRepository;


    private final TransferRepository transferRepository;

    //récupérer la liste des transfers
    @Override
    public List<Transfer> listOfTransfers(){
        List<Transfer> transfers=transferRepository.findAll();
        log.info("la liste des transfers");
        if(CollectionUtils.isEmpty(transfers)){
            return null;
        }
        return transfers;
    }

    @Override
    public void transferMoney(TransferDto transferDto) throws CompteNonExistantException, TransactionException {
        Compte compteEmetteur = compteRepository.findCompteByNrCompte(transferDto.getNrCompteEmetteur());
        Compte compteBenificiaire = compteRepository.findCompteByNrCompte(transferDto.getNrCompteBeneficiaire());
        BigDecimal solde=compteBenificiaire.getSolde();
        int soldetoInt=solde.intValue();

        if (compteEmetteur== null) {
            System.out.println("Compte  emetteur Non existant");
            throw new CompteNonExistantException("Compte emetteur Non existant");
        }

        if (compteBenificiaire == null) {
            System.out.println("Compte benificiaire Non existant");
            throw new CompteNonExistantException("Compte benificiaire Non existant");
        }

        if (transferDto.getMontant().equals(null)) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (transferDto.getMontant().intValue() == 0) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (transferDto.getMontant().intValue() < 10) {
            System.out.println("Montant minimal de transfer non atteint");
            throw new TransactionException("Montant minimal de transfer non atteint");
        } else if (transferDto.getMontant().intValue() > MONTANT_MAXIMAL) {
            System.out.println("Montant maximal de transfer dépassé");
            throw new TransactionException("Montant maximal de transfer dépassé");
        }

        if (transferDto.getMotif().length() < 0) {
            System.out.println("Motif vide");
            throw new TransactionException("Motif vide");
        }

        if (compteEmetteur.getSolde().intValue() - transferDto.getMontant().intValue() < 0) {
            log.error("Solde insuffisant pour l'utilisateur");
        }

        if (compteEmetteur.getSolde().intValue() - transferDto.getMontant().intValue() < 0) {
            log.error("Solde insuffisant pour l'utilisateur");
        }

        compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(transferDto.getMontant()));
        compteRepository.save(compteEmetteur);
        int soldeFinal=soldetoInt+transferDto.getMontant().intValue();
        compteBenificiaire
                .setSolde(BigDecimal.valueOf(soldeFinal));
        compteRepository.save(compteBenificiaire);

        Transfer transfer = new Transfer();
        transfer.setDateExecution(transferDto.getDate());
        transfer.setCompteBeneficiaire(compteBenificiaire);
        transfer.setMotifTransfer(transferDto.getMotif());
        transfer.setCompteEmetteur(compteEmetteur);
        transfer.setMontantTransfer(transferDto.getMontant());

        transferRepository.save(transfer);
    }
}
