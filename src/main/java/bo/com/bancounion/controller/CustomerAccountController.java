/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller;

import bo.com.bancounion.controller.dto.customeraccount.CreateCustomerAccountResponse;
import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.controller.dto.customeraccount.ListCustomerAccountResponse;
import bo.com.bancounion.controller.exception.AppException;
import bo.com.bancounion.exception.ExceptionNotFoundResponse;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.service.customeraccount.CreateCustomerAccountService;
import bo.com.bancounion.service.customeraccount.DeleteCustomerAccountService;
import bo.com.bancounion.service.customeraccount.ReadCustomerAccountService;
import bo.com.bancounion.service.customeraccount.UpdateCustomerAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@RestController
@Tag(name = "Cuentas de Clientes API", description = "APIs para crear, leer, actualizar y eliminar las cuentas de los clientes.")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.PUT,
    RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS})
@RequestMapping(CustomerAccountController.API_RESOURCE)
public class CustomerAccountController {

    public static final String API_RESOURCE = "/api/v1/customer/account";
    
    @Autowired
    CreateCustomerAccountService createCustomerAccountService;

    @Autowired
    ReadCustomerAccountService readCustomerAccountService;

    @Autowired
    UpdateCustomerAccountService updateCustomerAccountService;

    @Autowired
    DeleteCustomerAccountService deleteCustomerAccountService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear cuenta para un cliente",
            description = "Este metodo nos permite crear nuevas cuentas para los clientes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created - Recurso creado"),
        @ApiResponse(responseCode = "400", description = "Bad Request - Errores generales de solicitud (sintaxis, formato, URL).",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppException.class))
                }),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Errores de validación de datos específicos de la API.",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppException.class))
                }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Error al procesar la solicitud.",
                content = {
                    @Content(mediaType = "application/json", schema
                            = @Schema(implementation = AppException.class))})
    })
    @PostMapping(consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public ResponseEntity<CreateCustomerAccountResponse> create(@RequestBody CreateUpdateCustomerAccountRequest request) throws URISyntaxException, ExceptionResponse {
        CreateCustomerAccountResponse response = createCustomerAccountService.create(request);

        return ResponseEntity.created(new URI("/" + response.getId())).body(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar las cuentas de un cliente",
            description = "Este metodo nos permite listar las cuentas de un cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok - Solciitud procesada"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Error al procesar la solicitud.",
                content = {
                    @Content(mediaType = "application/json", schema
                            = @Schema(implementation = AppException.class))})
    })
    @GetMapping(produces = "application/json; charset=UTF-8")
    public List<ListCustomerAccountResponse> findByCustomerId(
            @RequestParam(required = false) @Parameter(name = "name",
                    description = "Filtrado por el contenido y no sensible a "
                    + "mayúsculas y minúsculas sobre el campo nombre.",
                    example = "Alex") int id
    ) throws ExceptionResponse {
        return readCustomerAccountService.findByCustomerId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizar cliente",
            description = "Este metodo nos permite actualizar los datos de los clientes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok - Recurso actualizado"),
        @ApiResponse(responseCode = "400", description = "Bad Request - Errores generales de solicitud (sintaxis, formato, URL).",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppException.class))
                }),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Errores de validación de datos específicos de la API.",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppException.class))
                }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Error al procesar la solicitud.",
                content = {
                    @Content(mediaType = "application/json", schema
                            = @Schema(implementation = AppException.class))})
    })
    @PutMapping(path = "/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public ResponseEntity update(@RequestBody CreateUpdateCustomerAccountRequest request, @Parameter(name = "id",
            description = "Identificador unico de la cuenta del cliente.",
            example = "1") @PathVariable int id) throws ExceptionResponse, ExceptionNotFoundResponse {
        updateCustomerAccountService.update(request, id);

        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Eliminar cliente",
            description = "Este metodo nos permite eliminar clientes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok - Recurso actualizado"),
        @ApiResponse(responseCode = "404", description = "Not Found - El recurso especificado "
                + "no se ha encontrado en el servidor.",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppException.class))
                }),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Errores de validación de datos específicos de la API.",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppException.class))
                }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Error al procesar la solicitud.",
                content = {
                    @Content(mediaType = "application/json", schema
                            = @Schema(implementation = AppException.class))})
    })

    @DeleteMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity delete(@Parameter(name = "id",
            description = "Identificador unico de la cuenta del cliente.",
            example = "1") @PathVariable int id) throws ExceptionResponse, ExceptionNotFoundResponse {
        deleteCustomerAccountService.delete(id);

        return ResponseEntity.ok().build();
    }

}
