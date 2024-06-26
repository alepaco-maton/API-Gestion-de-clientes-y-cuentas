/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author alepaco.com
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageTest<T> {

    private Integer totalPages;
    private Long totalElements;
    private Integer size;
    private List<T> content;
    private Integer number;
    private Integer numberOfElements;
    private Boolean first;
    private Boolean last; 
    private Boolean empty;
    
}
