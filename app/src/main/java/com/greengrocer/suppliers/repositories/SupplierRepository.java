package com.greengrocer.suppliers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<SupplierEntity,Long> {
    Optional<SupplierEntity> findSupplierBySupplierId(String supplierId);
    Long countBySupplierId(String supplierId);

}
