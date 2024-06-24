/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.commons;

import bo.com.bancounion.commons.AppTools;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author alepaco.com
 */
@SpringBootTest
public class AppToolsTest {

    @Test
    public void testIsBlank_NullString() {
        String value = null;

        boolean isBlank = AppTools.isBlank(value);

        assertTrue(isBlank, "Null string should be considered blank");
    }

    @Test
    public void testIsBlank_EmptyString() {
        String value = "";

        boolean isBlank = AppTools.isBlank(value);

        assertTrue(isBlank, "Empty string should be considered blank");
    }

    @Test
    public void testIsBlank_StringWithSpaces() {
        String value = "  ";

        boolean isBlank = AppTools.isBlank(value);

        assertTrue(isBlank, "String with only spaces should be considered blank");
    }

    @Test
    public void testIsBlank_NonBlankString() {
        String value = "Hello World";

        boolean isBlank = AppTools.isBlank(value);

        assertFalse(isBlank,
                "Non-blank string should not be considered blank");
    }

    @Test
    public void testConvertToDateToLocalDate_ValidDate() {
        Date date = new Date();

        LocalDate localDate = AppTools.convertToDateToLocalDate(date);

        assertNotNull(localDate, "Converted LocalDate should not be null");
        assertEquals(LocalDate.now().getYear(),
                localDate.getYear(), "Year should be the same as current date");
    }

    @Test
    public void testConvertToDateToLocalDate_NullDate() {
        Date date = null;

        assertThrows(NullPointerException.class, ()
                -> AppTools.convertToDateToLocalDate(date),
                "Converting null Date should throw NullPointerException");
    }

    @Test
    public void testCalculateAge_ValidBirthdate() {
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        LocalDate today = LocalDate.now();

        int age = AppTools.calculateAge(birthdate);

        int expectedAge = Period.between(
                birthdate, today).getYears();
        assertEquals(expectedAge, age,
                "Calculated age should be correct");
    }

}
