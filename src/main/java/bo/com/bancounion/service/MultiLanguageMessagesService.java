package bo.com.bancounion.service;

import java.util.Locale;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alepaco.com
 */
@Log4j2
@Component
public class MultiLanguageMessagesService {
    
    @Autowired
    private MessageSource messageSource;
     
    public String getMessage(String code) {  
        return messageSource.getMessage(code, null, Locale.getDefault());
    }
    
}
