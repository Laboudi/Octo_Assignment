package ma.octo.assignement.ServiceImpl;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.DepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.DepositRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DepositServiceTests {
    @Mock
    private CompteRepository compteRepository;

    @Mock
    private DepositRepository depositRepository;

    @InjectMocks
    private DepositServiceImpl depositService;

    private Compte compteBeneficiaire;
    private DepositDto depositDto;
    private MoneyDeposit deposit ;


    @BeforeEach
    public void initialize(){
        compteBeneficiaire=Compte.builder().
                id(3L)
                .nrCompte("123")
                .rib("rib3").solde(new BigDecimal(10000)).build();
        depositDto =DepositDto.builder().nom_prenom_emetteur("Oumaima Laboudi")
                .motifDeposit("motif1")
                .dateExecution(new Date())
                .rib("rib3").montant(new BigDecimal(1400)).build();

    }


    @AfterEach
    public void afterTest(){
        compteBeneficiaire=null;
        depositDto=null;
    }

    @DisplayName("Test the getAllDepositsMethods")
    @Test
    public void testMethodgetAllDeposits(){
        MoneyDeposit moneyDeposit=MoneyDeposit.builder()
                .id(1L)
                .motifDeposit(depositDto.getMotifDeposit())
                .compteBeneficiaire(compteBeneficiaire)
                .montant(depositDto.getMontant())
                .nom_prenom_emetteur(depositDto.getNom_prenom_emetteur())
                .build();
        given(depositRepository.findAll()).willReturn(List.of(MoneyDeposit.builder().build(),moneyDeposit));
        List<MoneyDeposit> moneyDeposits=depositService.getAllDeposit();
        assertThat(moneyDeposits).isNotNull();
        assertThat(moneyDeposits.size()).isGreaterThan(0);

    }
    @DisplayName("Test the depositMoney methode")
    @Test
    public void DepositMoneyTest() throws TransactionException, CompteNonExistantException {
        given(compteRepository.findCompteByRib(depositDto.getRib())).willReturn(compteBeneficiaire);
        depositService.DepositMoney(depositDto);
        assertThat(compteBeneficiaire.getSolde().intValue()).isEqualTo(11400);
        verify(compteRepository).save(compteBeneficiaire);
        verify(depositRepository).save(any());
    }
}
