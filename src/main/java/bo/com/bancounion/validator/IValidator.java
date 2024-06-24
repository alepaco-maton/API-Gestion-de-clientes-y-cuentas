/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bo.com.bancounion.validator;

import bo.com.bancounion.exception.ErrorCode; 

/**
 *
 * @author alepaco.com
 * @param <T>
 */
public interface IValidator<T> {
    
    ErrorCode validate(T request);
    
}
