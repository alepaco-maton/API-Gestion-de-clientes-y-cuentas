/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.exception;

import lombok.AllArgsConstructor;

/**
 *
 * @author alepaco.com
 */
@AllArgsConstructor
public enum ErrorCode {

    //general errors 00000 - 00099
    SUCCESSFUL("APP_BU-00000"),
    ERROR_PROCESSING_THE_TRANSACTION("APP_BU-00001"),
    
    // errors customer create 000100 - 000199
    CREATE_CUSTOMER_NAME_IS_REQUIRED("APP_BU-00100"),
    CREATE_CUSTOMER_NAME_IS_INVALID("APP_BU-00101"),
    CREATE_CUSTOMER_PATERNAL_OR_MATERNAL_IS_REQUIRED("APP_BU-00102"),
    CREATE_CUSTOMER_PATERNAL_IS_INVALID("APP_BU-00103"),
    CREATE_CUSTOMER_MATERNAL_IS_INVALID("APP_BU-00104"),
    CREATE_CUSTOMER_DOCUMENT_TYPE_ID_IS_REQUIRED("APP_BU-00105"),
    CREATE_CUSTOMER_DOCUMENT_TYPE_ID_IS_INVALID("APP_BU-00106"),
    CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_REQUIRED("APP_BU-00107"),
    CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_INVALID("APP_BU-00108"),
    CREATE_CUSTOMER_BIRTH_DATE_IS_REQUIRED("APP_BU-00109"),
    CREATE_CUSTOMER_BIRTH_DATE_IS_INVALID_CANNOT_BE_IN_THE_FUTURE("APP_BU-00110"),
    CREATE_CUSTOMER_BIRTH_DATE_IS_INVALID_USER_MUST_BE_AT_LEAST_18_YEARS_OLD("APP_BU-00111"),
    CREATE_CUSTOMER_GENDER_ID_IS_REQUIRED("APP_BU-00112"),
    CREATE_CUSTOMER_GENDER_ID_IS_INVALID("APP_BU-00113"),
    
    
    // errors customer UPDATE 000300 - 000399
    UPDATE_CUSTOMER_ID_NOT_FOUND("APP_BU-00300"),
    
    // errors customer delete 000400 - 000499
    DELETE_CUSTOMER_ID_NOT_FOUND("APP_BU-00400"),
    
    // errors customer account create 000500 - 000599
    CREATE_CUSTOMER_ACCOUNT_PRODUCT_TYPE_ID_IS_REQUIRED("APP_BU-00500"),
    CREATE_CUSTOMER_ACCOUNT_PRODUCT_TYPE_ID_IS_INVALID("APP_BU-00501"),
    CREATE_CUSTOMER_ACCOUNT_ACCOUNT_NUMBER_IS_REQUIRED("APP_BU-00502"),
    CREATE_CUSTOMER_ACCOUNT_ACCOUNT_NUMBER_IS_INVALID("APP_BU-00503"),
    CREATE_CUSTOMER_ACCOUNT_CURRENCY_IS_REQUIRED("APP_BU-00504"),
    CREATE_CUSTOMER_ACCOUNT_CURRENCY_IS_INVALID("APP_BU-00505"),
    CREATE_CUSTOMER_ACCOUNT_BRANCH_IS_REQUIRED("APP_BU-00506"),
    CREATE_CUSTOMER_ACCOUNT_BRANCH_IS_INVALID("APP_BU-00507"),
    CREATE_CUSTOMER_ACCOUNT_CUSTOMER_ID_IS_REQUIRED("APP_BU-00508"),
    CREATE_CUSTOMER_ACCOUNT_CUSTOMER_ID_IS_INVALID("APP_BU-00509"),
    CREATE_CUSTOMER_AMOUNT_AMOUNT_IS_REQUIRED("APP_BU-00510"),
    CREATE_CUSTOMER_AMOUNT_AMOUNT_IS_INVALID("APP_BU-00511"),
    
    // errors customer account create 000600 - 000699
    UPDATE_CUSTOMER_ACCOUNT_ID_IS_NOT_FOUND("APP_BU-00600"),
    
    
    // errors customer account create 000700 - 000799
    DELETE_CUSTOMER_ACCOUNT_CUSTOMER_ID_NOT_FOUND("APP_BU-00700")
    
    
    ;

    private final String code;

    public String getCode() {
        return code;
    }

    public boolean isSuccessfull() {
        return code.equals(SUCCESSFUL.getCode());
    }
    
}
