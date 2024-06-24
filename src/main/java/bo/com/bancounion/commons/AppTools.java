/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.commons;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author alepaco.com
 */
public class AppTools {

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
        
    public static LocalDate convertToDateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).
                atZone(ZoneId.systemDefault()).toLocalDate();
    }
        
    public static int calculateAge(LocalDate birthdate) {
        LocalDate today = LocalDate.now();
        return Period.between(birthdate, today).getYears();
    }
        
    public static BigDecimal reoundAmountForCustomerAccount(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

}
