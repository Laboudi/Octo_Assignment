package ma.octo.assignement.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TRAN")
public class Transfer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(precision = 16, scale = 2, nullable = false)
  private BigDecimal montantTransfer;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateExecution;

  @ManyToOne
  private Compte compteEmetteur;

  @ManyToOne
  private Compte compteBeneficiaire;

  @Column(length = 200)
  private String motifTransfer;

}
