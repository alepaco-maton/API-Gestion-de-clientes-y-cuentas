/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.service.documenttype;

import bo.com.bancounion.mappers.documenttype.ReadDocumentTypeMapper;
import bo.com.bancounion.controller.dto.documenttype.ListDocumentTypeResponse;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@AllArgsConstructor
@Service
@Component
public class ReadDocumentTypeService {

    @Autowired
    IDocumentTypeRepository repository;

    public List<ListDocumentTypeResponse> list() {
        return repository.findAll().stream().
                map(g -> ReadDocumentTypeMapper.mapperToDto(g)).
                collect(Collectors.toList());
    }

}
