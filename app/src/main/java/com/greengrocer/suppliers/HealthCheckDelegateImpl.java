package com.greengrocer.suppliers;

import com.greengrocer.suppliers.api.HealthCheckApiDelegate;
import com.greengrocer.suppliers.model.HealthCheckStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckDelegateImpl implements HealthCheckApiDelegate {
    @Override
    public ResponseEntity<HealthCheckStatus> healthCheck(){
        HealthCheckStatus healthCheckStatus = new HealthCheckStatus();
        healthCheckStatus.setAppId("supplier-api-catalog");
        healthCheckStatus.setHealthState(HealthCheckStatus.HealthStateEnum.UP);
        return ResponseEntity.ok(healthCheckStatus);
    }
}
