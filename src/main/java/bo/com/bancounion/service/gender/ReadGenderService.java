/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.service.gender;

import bo.com.bancounion.mappers.gender.ReadGenderMapper;
import bo.com.bancounion.controller.dto.gender.ListGenderResponse;
import bo.com.bancounion.repository.IGenderRepository;
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
public class ReadGenderService {

    @Autowired
    IGenderRepository repository;

    public List<ListGenderResponse> list() {
        return repository.findAll().stream().
                map(g -> ReadGenderMapper.mapperToDto(g)).
                collect(Collectors.toList());
    }

}
