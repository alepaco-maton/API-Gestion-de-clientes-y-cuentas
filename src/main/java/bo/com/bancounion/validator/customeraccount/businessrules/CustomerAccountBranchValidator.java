/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customeraccount.businessrules;

import bo.com.bancounion.commons.AppTools;
import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.IBranchRepository;
import bo.com.bancounion.validator.IValidator;
import java.util.List;

/**
 *
 * @author alepaco.com
 */
public class CustomerAccountBranchValidator implements IValidator<CreateUpdateCustomerAccountRequest> {

    private final IBranchRepository branchRepository;

    public CustomerAccountBranchValidator(IBranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public ErrorCode validate(CreateUpdateCustomerAccountRequest request) {
        if (AppTools.isBlank(request.getBranch())) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_BRANCH_IS_REQUIRED;
        }

        List<String> branches = branchRepository.findAll();

        if (!branches.contains(request.getBranch())) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_BRANCH_IS_INVALID;
        }

        return ErrorCode.SUCCESSFUL;
    }

}
