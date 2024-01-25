package net.unir.missi.desarrollowebfullstack.bookabook.operador.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.BadParametersException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.EntityNotFoundException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.service.LoanServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * TODO: delete Loan
 * TODO: patch loan
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class LoansController {
    @Autowired
    private final LoanServiceImpl service;

    @GetMapping("/loans")
    public ResponseEntity<List<LoanResponse>> getAllLoans(
            @Parameter(name="bookId", example = "")
            @RequestParam(required = false) Long bookId,
            @Parameter(name="clientId", example = "")
            @RequestParam(required = false) Long clientId,
            @Parameter(name="loanDate", example = "")
            @RequestParam(required = false)Date loanDate,
            @Parameter(name="returnDate", example = "")
            @RequestParam(required = false)Date returnDate,
            @Parameter(name="dueDate", example = "")
            @RequestParam(required = false)Date dueDate,
            @Parameter(name="isReturned", example = "")
            @RequestParam(required = false)Boolean isReturned,
            @Parameter(name="renewalCount", example = "")
            @RequestParam(required = false)Integer renewalCount
            )
    {
            try
            {
                List<LoanResponse> response = service.getAllLoans(bookId, clientId, loanDate, returnDate, dueDate, isReturned, renewalCount);
                return ResponseEntity.ok(Objects.requireNonNullElse(response, Collections.emptyList()));
            }
            catch (EntityNotFoundException e)
            {
                return ResponseEntity.notFound().build();
            }
            catch (BadParametersException e)
            {
                return ResponseEntity.badRequest().build();
            }
            catch(Exception e)
            {
                return ResponseEntity.internalServerError().build();
            }
    }

    @PostMapping("/loans")
    public ResponseEntity<LoanResponse> addLoan(@RequestBody LoanRequest loanRequest)
    {
        try
        {
            if (loanRequest == null)
            {
                return ResponseEntity.badRequest().build();
            }
            LoanResponse newLoan = service.createLoan(loanRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
        }
        catch (EntityNotFoundException e)
        {
            Logger.getGlobal().warning("Not found" + e.getMessage());
            return ResponseEntity.notFound().build();
        }
        catch (BadParametersException e)
        {
            return ResponseEntity.badRequest().build();
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable String id)
    {
        try
        {
            LoanResponse response = service.getLoanById(Long.valueOf(id));
            return ResponseEntity.ok(response);
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (BadParametersException e)
        {
            return ResponseEntity.badRequest().build();
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/loans/client/{clientId}")
    public ResponseEntity<List<LoanResponse>> getLoanByClientId(@PathVariable String clientId)
    {
        try
        {
            List<LoanResponse> response = service.getLoansByClientId(Long.valueOf(clientId));
            return ResponseEntity.ok(Objects.requireNonNullElse(response, Collections.emptyList()));
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (BadParametersException e)
        {
            return ResponseEntity.badRequest().build();
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }
}
