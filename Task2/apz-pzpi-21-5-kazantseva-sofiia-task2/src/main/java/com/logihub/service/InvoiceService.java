package com.logihub.service;

import com.logihub.exception.InvoiceException;
import com.logihub.exception.ParkingPlaceException;
import com.logihub.exception.TruckException;
import com.logihub.exception.UserException;
import com.logihub.model.request.InvoiceRequest;
import com.logihub.model.response.InvoiceDTO;
import com.logihub.model.response.ShortInvoiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {


    InvoiceDTO createInvoice(String email, Long userId, InvoiceRequest invoice) throws UserException, InvoiceException, TruckException, ParkingPlaceException;

    InvoiceDTO signInvoiceByParkingManager(String email, Long userId, Long invoiceId) throws UserException, InvoiceException;

    InvoiceDTO signInvoiceByTruckManager(String email, Long userId, Long invoiceId) throws UserException, InvoiceException;

    InvoiceDTO getInvoiceByTruckManager(String email, Long userId, Long invoiceId) throws UserException, InvoiceException;

    InvoiceDTO getInvoiceByParkingManager(String email, Long userId, Long invoiceId) throws UserException, InvoiceException;

    Page<ShortInvoiceDTO> getInvoicesByTruckManager(String email, Long userId, Pageable pageable)
            throws UserException;

    Page<ShortInvoiceDTO> getInvoicesByParkingManager(String email, Long userId, Pageable pageable)
            throws UserException;

    Page<ShortInvoiceDTO> getInvoicesByTruckNumber(String email, Long userId, String truckNumber,
                                                   Pageable pageable) throws UserException;

    Page<ShortInvoiceDTO> getInvoicesByPlaceNumber(String email, Long userId, String placeNumber,
                                                   Pageable pageable) throws UserException;

    Page<ShortInvoiceDTO> getSignedInvoicesByTruckManager(String email, Long userId, Pageable pageable) throws UserException;

    Page<ShortInvoiceDTO> getSignedInvoicesByParkingManager(String email, Long userId, Pageable pageable) throws UserException;

    Page<ShortInvoiceDTO> getNotSignedTruckManagerInvoicesByParkingManager(String email, Long userId,
                                                                           Pageable pageable) throws UserException;

    Page<ShortInvoiceDTO> getNotSignedParkingManagerInvoicesByParkingManager(String email, Long userId,
                                                                             Pageable pageable) throws UserException;

    Page<ShortInvoiceDTO> getNotSignedTruckManagerInvoicesByTruckManager(String email, Long userId,
                                                                         Pageable pageable) throws UserException;

    Page<ShortInvoiceDTO> getNotSignedParkingManagerInvoicesByTruckManager(String email, Long userId,
                                                                           Pageable pageable) throws UserException;
}