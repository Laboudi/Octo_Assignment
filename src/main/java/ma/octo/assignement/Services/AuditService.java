package ma.octo.assignement.Services;

import ma.octo.assignement.domain.AuditTransfer;

public interface AuditService {
    public void auditDeposit(String rib);
    public void auditTransfer(String nrCompte);
}
