/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.producttype;

import bo.com.bancounion.controller.dto.producttype.ListProductTypeResponse;
import bo.com.bancounion.entity.ProductType;

/**
 *
 * @author alepaco.com
 */
public class ReadProductTypeMapper {

    public static ListProductTypeResponse mapperToDto(ProductType model) {
        return new ListProductTypeResponse(model.getId(), model.getName());
    }

}
