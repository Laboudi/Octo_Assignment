package ma.octo.assignement.ServiceImpl;

import lombok.RequiredArgsConstructor;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.DepositRepository;
import ma.octo.assignement.Services.DepositService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class DepositServiceImpl implements DepositService {

    public static final int MONTANT_MAX=10000;

    private final DepositRepository depositRepository;

    private final CompteRepository compteRepository;


    @Override
    public List<MoneyDeposit> getAllDeposit() {
        List<MoneyDeposit> moneyDeposits=depositRepository.findAll();
        if(CollectionUtils.isEmpty(moneyDeposits)){
            return null;
        }
        return moneyDeposits;
    }

    @Override
    public void DepositMoney(DepositDto dto) throws CompteNonExistantException, TransactionException {

        Compte compteBenificiaire=compteRepository.findCompteByRib(dto.getRib());

        BigDecimal solde = compteBenificiaire.getSolde();

        int solde1=solde.intValue();

        if(compteBenificiaire == null){
            System.out.println("ce compte n'existe pas ");
            throw new CompteNonExistantException("le compte n'existe pas ");
        }
        if(dto.getMontant().intValue() > MONTANT_MAX){
            System.out.println("Transaction impossible :montant supérieur à 10000");
            throw new TransactionException("Transaction impossible");
        }
        if(dto.getMontant().intValue()==0){
            System.out.println("Transaction impossible :montant est vide");
            throw new TransactionException("Transaction impossible");
        }
        if(dto.getMontant().intValue()<=MONTANT_MAX){
            solde1=solde1+dto.getMontant().intValue();
        }
        compteBenificiaire.setSolde(BigDecimal.valueOf(solde1));
        compteRepository.save(compteBenificiaire);
        MoneyDeposit deposit = MoneyDeposit.builder().build();
        deposit.setDateExecution(dto.getDateExecution());
        deposit.setCompteBeneficiaire(compteBenificiaire);
        deposit.setNom_prenom_emetteur(dto.getNom_prenom_emetteur());
        deposit.setMotifDeposit(dto.getMotifDeposit());
        deposit.setMontant(dto.getMontant());
        depositRepository.save(deposit);
    }
}
