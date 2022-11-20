package ma.octo.assignement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "UTILISATEUR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 10, nullable = false, unique = true)
  private String username;
  @Column(nullable = false,unique = true)
  private String password;

  @Column(length = 10, nullable = false)
  private String gender;

  @Column(length = 60, nullable = false)
  private String lastname;

  @Column(length = 60, nullable = false)
  private String firstname;

  @Temporal(TemporalType.DATE)
  private Date birthdate;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "USER_ROLE",
          joinColumns = {
                  @JoinColumn(name = "USER_ID")
          },
          inverseJoinColumns = {
                  @JoinColumn(name = "ROLE_ID")
          }
  )
  private Set<Role> role;
}
