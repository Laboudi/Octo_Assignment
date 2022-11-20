package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDto {
  private String nrCompteEmetteur;
  private String nrCompteBeneficiaire;
  private String motif;
  private BigDecimal montant;
  private Date date;
}
