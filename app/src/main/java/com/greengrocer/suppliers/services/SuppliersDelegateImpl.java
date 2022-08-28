package com.greengrocer.suppliers.services;

import com.greengrocer.suppliers.api.SuppliersApiDelegate;
import com.greengrocer.suppliers.mappers.SupplierMapper;
import com.greengrocer.suppliers.model.Supplier;
import com.greengrocer.suppliers.model.SupplierSummary;
import com.greengrocer.suppliers.repositories.SupplierEntity;
import com.greengrocer.suppliers.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class SuppliersDelegateImpl implements SuppliersApiDelegate {

    @Autowired
    SupplierRepository supplierRepository;

    HttpStatus status = HttpStatus.OK;
    HttpHeaders customResponseHeaders = new HttpHeaders();

    @Override
    public ResponseEntity<SupplierSummary> getSuppliers() {
        List<SupplierEntity> supplierEntities = supplierRepository.findAll();
        SupplierSummary supplierSummary = new SupplierSummary();
        if(! supplierEntities.isEmpty()){
            for (SupplierEntity supplierEntity : supplierEntities) {
                supplierSummary.addRecordsItem(populateSupplier(supplierEntity));
            }
        }
        else{
            buildErrorResponse("SUPPLIER_REQ_ERROR","No Suppliers / Something Went Wrong");
        }
        return ResponseEntity.status(status).headers(customResponseHeaders).body(supplierSummary);
    }

    @Override
    public ResponseEntity<Void> addSupplier(@RequestBody Supplier supplier){
        SupplierEntity supplierEntity = SupplierMapper.INSTANCE.mapTo(supplier);
        SupplierEntity savedSupplier = supplierRepository.save(supplierEntity);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedSupplier.getSupplierId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    @Query("SELECT supplier FROM Supplier_Entity WHERE supplier.SUPPLIER_ID=(:supplierId)")
    public ResponseEntity<Supplier> getSupplierBySupplierCode(@PathVariable("supplierId") String supplierCode){
        Optional<SupplierEntity> supplierEntity = supplierRepository.findSupplierBySupplierId(supplierCode);
        Supplier supplier = null;
        if(supplierEntity.isPresent())
            supplier = populateSupplier(supplierEntity.get());
        else {
            buildErrorResponse("BAD_REQ","Invalid Supplier Id / Supplier Not Found");
        }
        return ResponseEntity.status(status).headers(customResponseHeaders).body(supplier);
    }

    private void buildErrorResponse(String errorType, String errorMessage) {
        status = HttpStatus.BAD_REQUEST;
        customResponseHeaders.set(errorType, errorMessage);
    }


    private Supplier populateSupplier(SupplierEntity supplierEntity) {
        Supplier supplierObj = new Supplier();
        supplierObj.setSupplierId(supplierEntity.getSupplierId());
        supplierObj.setSupplierCode(supplierEntity.getSupplierCode());
        supplierObj.setSupplierName(supplierEntity.getSupplierName());
        supplierObj.setSupplierType(supplierEntity.getSupplierType());
        return supplierObj;
    }
}
