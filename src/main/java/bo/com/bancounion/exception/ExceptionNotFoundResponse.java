/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.exception;

import lombok.Getter;

/**
 *
 * @author alepaco.com
 */
@Getter
public class ExceptionNotFoundResponse extends Exception {
    
    private final String code;
    private final String message;

    public ExceptionNotFoundResponse(String code, String message) {
        this.code = code;
        this.message = message;
    } 
    
}
