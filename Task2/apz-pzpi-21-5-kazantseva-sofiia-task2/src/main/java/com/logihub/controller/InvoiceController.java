package com.logihub.controller;

import com.logihub.exception.InvoiceException;
import com.logihub.exception.ParkingPlaceException;
import com.logihub.exception.TruckException;
import com.logihub.exception.UserException;
import com.logihub.model.request.InvoiceRequest;
import com.logihub.model.response.InvoiceDTO;
import com.logihub.model.response.ShortInvoiceDTO;
import com.logihub.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/api/truck-manager/{user-id}/invoices")
    public InvoiceDTO createInvoice(Authentication auth,
                                    @PathVariable("user-id") Long userId,
                                    @RequestBody @Valid InvoiceRequest invoice)
            throws UserException, InvoiceException, TruckException, ParkingPlaceException {
        return invoiceService.createInvoice(auth.getName(), userId, invoice);
    }

    @PatchMapping("/api/truck-manager/{user-id}/invoices/{invoice-id}")
    public InvoiceDTO signInvoiceByTruckManager(Authentication auth,
                                                @PathVariable("user-id") Long userId,
                                                @PathVariable("invoice-id") Long invoiceId)
            throws UserException, InvoiceException {
        return invoiceService.signInvoiceByTruckManager(auth.getName(), userId, invoiceId);
    }

    @GetMapping("/api/truck-manager/{user-id}/invoices/{invoice-id}")
    public InvoiceDTO getInvoiceByTruckManager(Authentication auth,
                                               @PathVariable("user-id") Long userId,
                                               @PathVariable("invoice-id") Long invoiceId)
            throws UserException, InvoiceException {
        return invoiceService.getInvoiceByTruckManager(auth.getName(), userId, invoiceId);
    }

    @GetMapping("/api/truck-manager/{user-id}/invoices")
    public Page<ShortInvoiceDTO> getInvoicesByTruckManager(Authentication auth,
                                                           @PathVariable("user-id") Long userId,
                                                           Pageable pageable) throws UserException {
        return invoiceService.getInvoicesByTruckManager(auth.getName(), userId, pageable);
    }

    @GetMapping("/api/truck-manager/{user-id}/invoices/truck-number/{truck-number}")
    public Page<ShortInvoiceDTO> getInvoicesByTruckNumber(Authentication auth,
                                                          @PathVariable("user-id") Long userId,
                                                          @PathVariable("truck-number") String truckNumber,
                                                          Pageable pageable) throws UserException {
        return invoiceService.getInvoicesByTruckNumber(auth.getName(), userId, truckNumber, pageable);
    }

    @GetMapping("/api/truck-manager/{user-id}/invoices/signed")
    public Page<ShortInvoiceDTO> getSignedInvoicesByTruckManager(Authentication auth,
                                                                 @PathVariable("user-id") Long userId,
                                                                 Pageable pageable) throws UserException {
        return invoiceService.getSignedInvoicesByTruckManager(auth.getName(), userId, pageable);
    }

    @GetMapping("/api/truck-manager/{user-id}/invoices/not-signed-by-parking-manager")
    public Page<ShortInvoiceDTO> getNotSignedTruckManagerInvoicesByParkingManager(Authentication auth,
                                                                                  @PathVariable("user-id") Long userId,
                                                                                  Pageable pageable)
            throws UserException {
        return invoiceService.getNotSignedTruckManagerInvoicesByParkingManager(auth.getName(), userId, pageable);
    }

    @GetMapping("/api/truck-manager/{user-id}/invoices/not-signed-by-truck-manager")
    public Page<ShortInvoiceDTO> getNotSignedTruckManagerInvoicesByTruckManager(Authentication auth,
                                                                                @PathVariable("user-id") Long userId,
                                                                                Pageable pageable) throws UserException {
        return invoiceService.getNotSignedTruckManagerInvoicesByTruckManager(auth.getName(), userId, pageable);
    }

    @PatchMapping("/api/parking-manager/{user-id}/invoices/{invoice-id}")
    public InvoiceDTO signInvoiceByParkingManager(Authentication auth,
                                                  @PathVariable("user-id") Long userId,
                                                  @PathVariable("invoice-id") Long invoiceId)
            throws UserException, InvoiceException {
        return invoiceService.signInvoiceByParkingManager(auth.getName(), userId, invoiceId);
    }

    @GetMapping("/api/parking-manager/{user-id}/invoices/{invoice-id}")
    public InvoiceDTO getInvoiceByParkingManager(Authentication auth,
                                                 @PathVariable("user-id") Long userId,
                                                 @PathVariable("invoice-id") Long invoiceId)
            throws UserException, InvoiceException {
        return invoiceService.getInvoiceByParkingManager(auth.getName(), userId, invoiceId);
    }

    @GetMapping("/api/parking-manager/{user-id}/invoices")
    public Page<ShortInvoiceDTO> getInvoicesByParkingManager(Authentication auth,
                                                             @PathVariable("user-id") Long userId,
                                                             Pageable pageable) throws UserException {
        return invoiceService.getInvoicesByParkingManager(auth.getName(), userId, pageable);
    }

    @GetMapping("/api/parking-manager/{user-id}/invoices/place-number/{place-number}")
    public Page<ShortInvoiceDTO> getInvoicesByPlaceNumber(Authentication auth,
                                                          @PathVariable("user-id") Long userId,
                                                          @PathVariable("place-number") String placeNumber,
                                                          Pageable pageable) throws UserException {
        return invoiceService.getInvoicesByPlaceNumber(auth.getName(), userId, placeNumber, pageable);
    }

    @GetMapping("/api/parking-manager/{user-id}/invoices/signed")
    public Page<ShortInvoiceDTO> getSignedInvoicesByParkingManager(Authentication auth,
                                                                   @PathVariable("user-id") Long userId,
                                                                   Pageable pageable) throws UserException {
        return invoiceService.getSignedInvoicesByParkingManager(auth.getName(), userId, pageable);
    }


    @GetMapping("/api/parking-manager/{user-id}/invoices/not-signed-by-parking-manager")
    public Page<ShortInvoiceDTO> getNotSignedParkingManagerInvoicesByParkingManager(Authentication auth,
                                                                                    @PathVariable("user-id") Long userId,
                                                                                    Pageable pageable)
            throws UserException {
        return invoiceService.getNotSignedParkingManagerInvoicesByParkingManager(auth.getName(), userId, pageable);
    }

    @GetMapping("/api/parking-manager/{user-id}/invoices/not-signed-by-truck-manager")
    public Page<ShortInvoiceDTO> getNotSignedParkingManagerInvoicesByTruckManager(Authentication auth,
                                                                                  @PathVariable("user-id") Long userId,
                                                                                  Pageable pageable)
            throws UserException {
        return invoiceService.getNotSignedParkingManagerInvoicesByTruckManager(auth.getName(), userId, pageable);
    }
}
