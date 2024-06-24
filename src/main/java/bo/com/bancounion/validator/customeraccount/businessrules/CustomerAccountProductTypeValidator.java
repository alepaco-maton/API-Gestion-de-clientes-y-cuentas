/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customeraccount.businessrules;

import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.entity.ProductType;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.IProductTypeRepository;
import bo.com.bancounion.validator.IValidator;

/**
 *
 * @author alepaco.com
 */
public class CustomerAccountProductTypeValidator implements IValidator<CreateUpdateCustomerAccountRequest> {

    private final IProductTypeRepository productTypeRepository;

    public CustomerAccountProductTypeValidator(IProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public ErrorCode validate(CreateUpdateCustomerAccountRequest request) {
        if (request.getProductTypeId() == null) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_PRODUCT_TYPE_ID_IS_REQUIRED;
        }

        ProductType model = productTypeRepository.findById(
                request.getProductTypeId()).orElse(null);

        if (model == null) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_PRODUCT_TYPE_ID_IS_INVALID;
        }

        return ErrorCode.SUCCESSFUL;
    }

}
