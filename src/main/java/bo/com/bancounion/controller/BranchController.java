/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller;

import bo.com.bancounion.controller.exception.AppException;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.service.branch.ReadBranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@RestController
@Tag(name = "Sucrusales API", description = "APIs para leer las sucursales.")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@RequestMapping(BranchController.API_RESOURCE)
public class BranchController {

    public static final String API_RESOURCE = "/api/v1/branch";
    
    @Autowired
    ReadBranchService readBranchService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar sucursales",
            description = "Este metodo nos permite listar las sucursales")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok - Solciitud procesada"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Error al procesar la solicitud.",
                content = {
                    @Content(mediaType = "application/json", schema
                            = @Schema(implementation = AppException.class))})
    })
    @GetMapping(produces = "application/json; charset=UTF-8")
    public List<String> list() throws ExceptionResponse {
        return readBranchService.list();
    }

}
