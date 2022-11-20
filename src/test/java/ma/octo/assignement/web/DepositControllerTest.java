package ma.octo.assignement.web;



import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ma.octo.assignement.ServiceImpl.DepositServiceImpl;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.DepositDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
@RequiredArgsConstructor
public class DepositControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private DepositServiceImpl depositService;

    private final ObjectMapper objectMapper;

    private DepositDto depositDto;
    private MoneyDeposit moneyDeposit;
    @BeforeEach
    public void setup(){
        depositDto=new DepositDto();
        depositDto.setMotifDeposit("motifDeposit");
        depositDto.setMontant(new BigDecimal(10000));
        depositDto.setDateExecution(new Date());
        depositDto.setRib("Rib1");
        depositDto.setNom_prenom_emetteur("Oumaima Laboudi");

        moneyDeposit=MoneyDeposit.builder()
                .id(1L).motifDeposit("motif").compteBeneficiaire(new Compte()).motifDeposit("motif").
        montant(new BigDecimal(10000)).dateExecution(new Date()).build();

    }
    @AfterEach
    public void clean(){
        depositDto=null;
    }
    @Test
    public void TestFindAllTransfers() throws Exception {
        List<MoneyDeposit> deposits = new ArrayList<>();
        deposits.add(MoneyDeposit.builder().id(2L).motifDeposit("motif deposit").montant(new BigDecimal(1000)).compteBeneficiaire(new Compte()).dateExecution(new Date()).nom_prenom_emetteur("Hanane").build());
        deposits.add(MoneyDeposit.builder().id(3L).motifDeposit("motif deposit 2").montant(new BigDecimal(1233)).nom_prenom_emetteur("Inas").compteBeneficiaire(new Compte()).dateExecution(new Date()).build());
        given(depositService.getAllDeposit()).willReturn(deposits);
        ResultActions response = mockMvc.perform(get("/depositsTransaction/listDeposits"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(deposits.size())));

    }
    @Test
    public void DepositMoney() throws Exception {
        ResultActions response = mockMvc.perform(post("/depositsTransaction/DepositTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(moneyDeposit))).andDo(print()).andExpect(status().isCreated());
    }
}
