package ma.octo.assignement.ServiceImpl;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.TransferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTests {
    @Mock
    private TransferRepository transferRepository;

    @Mock
    private CompteRepository compteRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    private Compte compteEmetteur;
    private Compte compteBenificiaire;
    private TransferDto transferDto ;

    @BeforeEach
    public void beforeTest(){
        compteEmetteur =Compte.builder()
                .id(1L)
                .nrCompte("12345678")
                .rib("rib1")
                .solde(new BigDecimal(10000.00))
                .build();
        compteBenificiaire=Compte.
                builder()
                .id(2L)
                .nrCompte("910111213")
                .rib("rib2")
                .solde(new BigDecimal(50000.00))
                .build();
        transferDto=TransferDto.builder()
                .date(new Date())
                .montant(BigDecimal.valueOf(5000.00))
                .nrCompteEmetteur("12345678")
                .nrCompteBeneficiaire("910111213")
                .motif("transfert motif")
                .build();
    }
    @AfterEach
    public void apresChaqueTest(){
        compteEmetteur=null;
        compteBenificiaire=null;
        transferDto=null;
    }
    @DisplayName("Test the method listOfTransfers() from the transferService")
    @Test
    public void testMethodListOfTransfers(){
        Transfer transfer = Transfer.builder()
                        .montantTransfer(BigDecimal.valueOf(4000.00))
                                .motifTransfer("motif3")
                                        .compteBeneficiaire(new Compte())
                .build();

        given(transferRepository.findAll()).willReturn(List.of(new Transfer(),transfer));
        List<Transfer> transfers = transferService.listOfTransfers();
        assertThat(transfers).isNotNull();
        assertThat(transfers.size()).isGreaterThan(0);
    }


    @DisplayName("Test the method MoneyTransfer")
    @Test
    public void TestTransferMethod() throws TransactionException, CompteNonExistantException {
        given(compteRepository.findCompteByNrCompte(compteEmetteur.getNrCompte())).willReturn(compteEmetteur);
        given(compteRepository.findCompteByNrCompte(compteBenificiaire.getNrCompte())).willReturn(compteBenificiaire);
        transferService.transferMoney(transferDto);
        assertThat(compteEmetteur.getSolde().intValue()).isEqualTo(5000);
        assertThat(compteBenificiaire.getSolde().intValue()).isEqualTo(55000);
        verify(compteRepository).save(compteBenificiaire);
        verify(compteRepository).save(compteEmetteur);
        verify(transferRepository).save(any());
    }
}
