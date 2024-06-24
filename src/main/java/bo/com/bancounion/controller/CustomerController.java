/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.controller.dto.customer.CreateCustomerResponse;
import bo.com.bancounion.controller.dto.customer.ListCustomerResponse;
import bo.com.bancounion.controller.exception.AppException;
import bo.com.bancounion.exception.ExceptionNotFoundResponse;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.service.customer.CreateCustomerService;
import bo.com.bancounion.service.customer.DeleteCustomerService;
import bo.com.bancounion.service.customer.ReadCustomerService;
import bo.com.bancounion.service.customer.UpdateCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
@Tag(name = "Clientes API", description = "APIs para crear, leer, actualizar y eliminar clientes.")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.PUT,
    RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS})
@RequestMapping(CustomerController.API_RESOURCE)
public class CustomerController {
    
    public static final String API_RESOURCE = "/api/v1/customer";

    @Autowired
    CreateCustomerService createClientService;

    @Autowired
    ReadCustomerService readClientService;

    @Autowired
    UpdateCustomerService updateClientService;

    @Autowired
    DeleteCustomerService deleteClientService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear cliente",
            description = "Este metodo nos permite crear nuevos clientes.")
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
    public ResponseEntity<CreateCustomerResponse> create(@RequestBody CreateUpdateCustomerRequest request) throws URISyntaxException, ExceptionResponse {
        CreateCustomerResponse response = createClientService.create(request);

        return ResponseEntity.created(new URI("/" + response.getId())).body(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar clientes",
            description = "Este metodo nos permite listar los clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok - Solciitud procesada"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Error al procesar la solicitud.",
                content = {
                    @Content(mediaType = "application/json", schema
                            = @Schema(implementation = AppException.class))})
    })
    @GetMapping(produces = "application/json; charset=UTF-8")
    public Page<ListCustomerResponse> list(
            @RequestParam(required = false) @Parameter(name = "id",
                    description = "Filtrado por el contenido y no sensible a "
                    + "mayúsculas y minúsculas sobre el campo nombre.",
                    example = "1") String id,
            @RequestParam(required = false) @Parameter(name = "name",
                    description = "Filtrado por el contenido y no sensible a "
                    + "mayúsculas y minúsculas sobre el campo nombre.",
                    example = "Alex") String name,
            @RequestParam(required = false) @Parameter(name = "paternal",
                    description = "Filtrado por el contenido y no sensible a "
                    + "mayúsculas y minúsculas sobre el campo paterno.",
                    example = "Paco") String paternal,
            @RequestParam(required = false) @Parameter(name = "maternal",
                    description = "Filtrado por el contenido y no sensible a "
                    + "mayúsculas y minúsculas sobre el campo materno.",
                    example = "Surco") String maternal,
            @RequestParam(required = false) @Parameter(name = "documentTypeId",
                    description = "Filtrado por el identificador unico de tipo de documento.",
                    example = "1") Integer documentTypeId,
            @RequestParam(required = false) @Parameter(name = "identityDocument",
                    description = "Filtrado por el contenido y no sensible a "
                    + "mayúsculas y minúsculas sobre el campo documento de identificación.",
                    example = "1") String identityDocument,
            @RequestParam(required = false) @Parameter(name = "birthDate",
                    description = "Filtrado por el contenido y no sensible a "
                    + "mayúsculas y minúsculas sobre el campo nombre.",
                    example = "04/11/1992") String birthDate,
            @RequestParam(required = false) @Parameter(name = "genero",
                    description = "Filtrado por el identificador unico de genero.",
                    example = "1") Integer genderId,
            @RequestParam(required = false) @Parameter(name = "status",
                    description = "Filtrado por el contenido y no sensible a "
                    + "mayúsculas y minúsculas sobre el campo estado.",
                    example = "Activo") String status,
            Pageable pageable
    ) throws ExceptionResponse {
        String sortBy = null;
        String sortDirection = null;
        int page = 0;
        int size = 10;

        if (pageable != null && pageable.getSort() != null && !pageable.getSort().isEmpty()) {
            sortBy = pageable.getSort().get().findFirst().get().getProperty();
            sortDirection = pageable.getSort().get().findFirst().get().getDirection().name();
        }

        if (pageable != null) {
            page = pageable.getPageNumber();
            size = pageable.getPageSize();
        }

        List<ListCustomerResponse> response = readClientService.list(id, name,
                paternal, maternal, documentTypeId, identityDocument,
                //birthDate,
                null,
                genderId, status, sortBy, sortDirection, page, size);

        long totalElements = readClientService.count(id, name,
                paternal, maternal, documentTypeId, identityDocument,
                //birthDate,
                null,
                genderId, status);

        return new PageImpl<>(response, pageable, totalElements);
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
    public ResponseEntity update(@RequestBody CreateUpdateCustomerRequest request, @Parameter(name = "id",
            description = "Identificador unico de cliente.",
            example = "1") @PathVariable int id) throws ExceptionResponse, ExceptionNotFoundResponse {
        updateClientService.update(request, id);

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
            description = "Identificador unico de cliente.",
            example = "1") @PathVariable int id) throws ExceptionResponse, ExceptionNotFoundResponse {
        deleteClientService.delete(id);

        return ResponseEntity.ok().build();
    }

}
