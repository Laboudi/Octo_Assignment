package ma.octo.assignement.ServiceImpl;

import lombok.RequiredArgsConstructor;
import ma.octo.assignement.Services.AuditService;
import ma.octo.assignement.domain.AuditDeposit;
import ma.octo.assignement.domain.AuditTransfer;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.repository.AuditDepositRepository;
import ma.octo.assignement.repository.AuditTransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditTransferRepository auditTransferRepository;
    private final AuditDepositRepository auditDepositRepository;
    @Override
    public void auditDeposit(String message) {
        AuditDeposit auditDeposit=new AuditDeposit();
        auditDeposit.setEventType(EventType.DEPOSIT);
        auditDeposit.setMessage(message);
        auditDepositRepository.save(auditDeposit);
    }

    @Override
    public void auditTransfer(String message) {
        AuditTransfer audit = AuditTransfer.builder().build();
        audit.setEventType(EventType.TRANSFER);
        audit.setMessage(message);
        auditTransferRepository.save(audit);
    }
}
