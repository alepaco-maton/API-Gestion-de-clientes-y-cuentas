/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.documenttype;

import bo.com.bancounion.controller.dto.documenttype.ListDocumentTypeResponse;
import bo.com.bancounion.entity.DocumentType;

/**
 *
 * @author alepaco.com
 */
public class ReadDocumentTypeMapper {

    public static ListDocumentTypeResponse mapperToDto(DocumentType model) {
        return new ListDocumentTypeResponse(model.getId(), model.getName());
    }

}
