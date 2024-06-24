/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customer.businessrules;
 
import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.validator.IValidator;


/**
 *
 * @author alepaco.com
 */
public class CustomerDocumentTypeValidator implements IValidator<CreateUpdateCustomerRequest> {
    
    private final IDocumentTypeRepository documentTypeRepository;

    public CustomerDocumentTypeValidator(IDocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }
   
    @Override
    public ErrorCode validate(CreateUpdateCustomerRequest request) {
        if (request.getDocumentTypeId () == null) {
            return ErrorCode.CREATE_CUSTOMER_DOCUMENT_TYPE_ID_IS_REQUIRED;
        }
        
        DocumentType model = documentTypeRepository.findById(
                request.getDocumentTypeId()).orElse(null);

        if (model == null) {
            return ErrorCode.CREATE_CUSTOMER_DOCUMENT_TYPE_ID_IS_INVALID;
        }

        return ErrorCode.SUCCESSFUL;
    }
    
}
