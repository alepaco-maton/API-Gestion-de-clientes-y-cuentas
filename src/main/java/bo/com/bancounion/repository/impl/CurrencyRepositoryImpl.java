/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.repository.impl;

import bo.com.bancounion.repository.ICurrencyRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CurrencyRepositoryImpl implements ICurrencyRepository {

    @Autowired
    MultiLanguageMessagesService mlms;

    public static List<String> currencies = null;

    @Override
    public List<String> findAll() {
        if (currencies == null) {
            Stream<String> strStream = Arrays.stream(this.mlms.
                    getMessage("LIST_CURRENCIES").split(","));

            currencies = strStream.collect(Collectors.toList());
        }
        
        return currencies;
    }

}
