package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.octo.assignement.domain.Compte;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

//I am using the annotation for the setters and getters and constructor
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//here I created the deposit dto(data access object) to set the data of MoneyDeposit object .
public class DepositDto {
    private String rib;
    private String nom_prenom_emetteur;
    private BigDecimal montant;
    private String motifDeposit;
    private Date dateExecution;
}
