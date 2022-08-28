package com.greengrocer.suppliers.mappers;

import com.greengrocer.suppliers.model.Supplier;

import com.greengrocer.suppliers.repositories.SupplierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierMapper {
    SupplierMapper  INSTANCE = Mappers.getMapper(SupplierMapper.class);
    SupplierEntity mapTo(Supplier supplier);
}
