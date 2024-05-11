package com.logihub.service.impl;

import com.logihub.exception.InvoiceException;
import com.logihub.exception.ParkingPlaceException;
import com.logihub.exception.TruckException;
import com.logihub.exception.UserException;
import com.logihub.model.entity.Invoice;
import com.logihub.model.entity.TruckManager;
import com.logihub.model.enums.InvoiceType;
import com.logihub.model.request.InvoiceRequest;
import com.logihub.model.response.InvoiceDTO;
import com.logihub.model.response.ItemDTO;
import com.logihub.model.response.ShortInvoiceDTO;
import com.logihub.repository.InvoiceRepository;
import com.logihub.repository.ParkingPlaceRepository;
import com.logihub.repository.TruckRepository;
import com.logihub.service.InvoiceService;
import com.logihub.service.ItemService;
import com.logihub.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final TruckRepository truckRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;
    private final ItemService itemService;
    private final AuthUtil authUtil;

    @Override
    public InvoiceDTO createInvoice(String email, Long userId, InvoiceRequest invoice)
            throws UserException, InvoiceException, TruckException, ParkingPlaceException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        InvoiceType type = switch (invoice.getType().toUpperCase()) {
            case "PARKING_INVOICE" -> InvoiceType.PARKING_INVOICE;
            case "LOADING_INVOICE" -> InvoiceType.LOADING_INVOICE;
            case "UNLOADING_INVOICE" -> InvoiceType.UNLOADING_INVOICE;
            default -> throw new InvoiceException(InvoiceException.InvoiceExceptionProfile.INVALID_INVOICE_TYPE);
        };

        var newInvoice = createInvoice(type, invoice, truckManager);

        newInvoice = invoiceRepository.save(newInvoice);

        if (type.equals(InvoiceType.LOADING_INVOICE) || type.equals(InvoiceType.UNLOADING_INVOICE)) {
            itemService.addItems(newInvoice.getId(), invoice.getItems());
            newInvoice.setPrice(invoice.getItems()
                    .stream()
                    .mapToDouble(o -> o.getPrice() * o.getAmount())
                    .sum()
            );
            newInvoice = invoiceRepository.save(newInvoice);
        }

        return toInvoiceDTO(newInvoice);
    }


    private Invoice createInvoice(InvoiceType type, InvoiceRequest invoiceRequest, TruckManager truckManager)
            throws TruckException, ParkingPlaceException, InvoiceException {
        var truck = truckRepository.findByNumber(invoiceRequest.getTruckNumber()).orElseThrow(
                () -> new TruckException(TruckException.TruckExceptionProfile.TRUCK_NOT_FOUND)
        );

        var parkingPlace = parkingPlaceRepository.findByPlaceNumber(invoiceRequest.getPlaceNumber()).orElseThrow(
                () -> new ParkingPlaceException(
                        ParkingPlaceException.ParkingPlaceExceptionProfile.PARKING_PLACE_NOT_FOUND)
        );

        double price = 0d;

        if (type.equals(InvoiceType.LOADING_INVOICE) || type.equals(InvoiceType.UNLOADING_INVOICE)) {
            if (invoiceRequest.getItems().isEmpty()) {
                throw new InvoiceException(InvoiceException.InvoiceExceptionProfile.INVALID_INVOICE);
            }
        } else {
            price = parkingPlace.getHourlyPay();
        }

        return Invoice.builder()
                .type(type)
                .truckManager(truckManager)
                .parkingManager(parkingPlace.getParkingManager())
                .truck(truck)
                .parkingPlace(parkingPlace)
                .creationDate(LocalDateTime.now())
                .description(invoiceRequest.getDescription())
                .price(price)
                .signedByParkingManager(false)
                .signedByTruckManager(false)
                .build();
    }

    @Override
    public InvoiceDTO signInvoiceByParkingManager(String email, Long userId, Long invoiceId) throws UserException, InvoiceException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        var invoice = invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new InvoiceException(InvoiceException.InvoiceExceptionProfile.INVOICE_NOT_FOUND)
        );

        if (!Objects.equals(invoice.getParkingManager().getId(), parkingManager.getId())) {
            throw new UserException(UserException.UserExceptionProfile.FORBIDDEN);
        }

        invoice.setSignedByParkingManager(true);
        invoice.setSignedByParkingManagerDate(LocalDateTime.now());

        return toInvoiceDTO(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceDTO signInvoiceByTruckManager(String email, Long userId, Long invoiceId)
            throws UserException, InvoiceException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        var invoice = invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new InvoiceException(InvoiceException.InvoiceExceptionProfile.INVOICE_NOT_FOUND)
        );

        if (!Objects.equals(invoice.getTruckManager().getId(), truckManager.getId())) {
            throw new UserException(UserException.UserExceptionProfile.FORBIDDEN);
        }

        if (Boolean.TRUE.equals(invoice.getSignedByParkingManager())) {
            invoice.setSignedByTruckManager(true);
            invoice.setSignedByTruckManagerDate(LocalDateTime.now());
        } else {
            throw new InvoiceException(InvoiceException.InvoiceExceptionProfile.INVOICE_NOT_SIGNED_BY_PARKING_MANAGER);
        }

        return toInvoiceDTO(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceDTO getInvoiceByTruckManager(String email, Long userId, Long invoiceId)
            throws UserException, InvoiceException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        var invoice = invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new InvoiceException(InvoiceException.InvoiceExceptionProfile.INVOICE_NOT_FOUND)
        );

        if (!invoice.getTruckManager().getId().equals(truckManager.getId())) {
            throw new UserException(UserException.UserExceptionProfile.FORBIDDEN);
        }

        return toInvoiceDTO(invoice);
    }

    @Override
    public InvoiceDTO getInvoiceByParkingManager(String email, Long userId, Long invoiceId)
            throws UserException, InvoiceException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        var invoice = invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new InvoiceException(InvoiceException.InvoiceExceptionProfile.INVOICE_NOT_FOUND)
        );

        if (!invoice.getParkingManager().getId().equals(parkingManager.getId())) {
            throw new UserException(UserException.UserExceptionProfile.FORBIDDEN);
        }

        return toInvoiceDTO(invoice);
    }

    @Override
    public Page<ShortInvoiceDTO> getInvoicesByTruckManager(String email, Long userId, Pageable pageable)
            throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByTruckManager(truckManager, pageable).map(this::toShortInvoiceDTO);
    }

    @Override
    public Page<ShortInvoiceDTO> getInvoicesByParkingManager(String email, Long userId, Pageable pageable)
            throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByParkingManager(parkingManager, pageable).map(this::toShortInvoiceDTO);
    }

    @Override
    public Page<ShortInvoiceDTO> getInvoicesByTruckNumber(String email, Long userId, String truckNumber,
                                                          Pageable pageable) throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByTruckManagerAndTruck_NumberContaining(truckManager, truckNumber, pageable)
                .map(this::toShortInvoiceDTO);
    }

    @Override
    public Page<ShortInvoiceDTO> getInvoicesByPlaceNumber(String email, Long userId, String placeNumber,
                                                          Pageable pageable) throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByParkingManagerAndParkingPlace_PlaceNumberContaining(parkingManager,
                placeNumber, pageable).map(this::toShortInvoiceDTO);
    }

    @Override
    public Page<ShortInvoiceDTO> getSignedInvoicesByTruckManager(String email, Long userId, Pageable pageable)
            throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByTruckManagerAndSignedByParkingManagerAndSignedByTruckManager(
                truckManager, true, true, pageable
        ).map(this::toShortInvoiceDTO);
    }

    @Override
    public Page<ShortInvoiceDTO> getSignedInvoicesByParkingManager(String email, Long userId, Pageable pageable)
            throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByParkingManagerAndSignedByParkingManagerAndSignedByTruckManager(
                        parkingManager, true, true, pageable)
                .map(this::toShortInvoiceDTO);
    }

    @Override
    public Page<ShortInvoiceDTO> getNotSignedTruckManagerInvoicesByParkingManager(String email, Long userId,
                                                                                  Pageable pageable)
            throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByTruckManagerAndSignedByParkingManagerAndSignedByTruckManager(
                truckManager, false, false, pageable
        ).map(this::toShortInvoiceDTO);
    }

    @Override
    public Page<ShortInvoiceDTO> getNotSignedParkingManagerInvoicesByParkingManager(String email, Long userId,
                                                                                    Pageable pageable)
            throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByParkingManagerAndSignedByParkingManagerAndSignedByTruckManager(
                        parkingManager, false, false, pageable)
                .map(this::toShortInvoiceDTO);
    }

    @Override
    public Page<ShortInvoiceDTO> getNotSignedTruckManagerInvoicesByTruckManager(String email, Long userId,
                                                                                Pageable pageable)
            throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByTruckManagerAndSignedByParkingManagerAndSignedByTruckManager(
                truckManager, true, false, pageable
        ).map(this::toShortInvoiceDTO);
    }

    @Override
    public Page<ShortInvoiceDTO> getNotSignedParkingManagerInvoicesByTruckManager(String email, Long userId,
                                                                                  Pageable pageable)
            throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        return invoiceRepository.findAllByParkingManagerAndSignedByParkingManagerAndSignedByTruckManager(
                        parkingManager, true, false, pageable)
                .map(this::toShortInvoiceDTO);
    }

    private InvoiceDTO toInvoiceDTO(Invoice invoice) {
        List<ItemDTO> items = new ArrayList<>();

        if (!invoice.getItems().isEmpty()) {
            items = invoice.getItems().stream().map(ItemDTO::new).toList();
        }

        return InvoiceDTO.builder()
                .id(invoice.getId())
                .type(invoice.getType())
                .truckNumber(invoice.getTruck().getNumber())
                .placeNumber(invoice.getParkingPlace().getPlaceNumber())
                .items(items)
                .truckManagerEmail(invoice.getTruckManager().getEmail())
                .parkingManagerEmail(invoice.getParkingManager().getEmail())
                .creationDate(invoice.getCreationDate())
                .description(invoice.getDescription())
                .price(invoice.getPrice())
                .signedByParkingManagerDate(invoice.getSignedByParkingManagerDate())
                .signedByTruckManagerDate(invoice.getSignedByTruckManagerDate())
                .signedByParkingManager(invoice.getSignedByParkingManager())
                .signedByTruckManager(invoice.getSignedByTruckManager())
                .build();
    }

    private ShortInvoiceDTO toShortInvoiceDTO(Invoice invoice) {
        return ShortInvoiceDTO.builder()
                .id(invoice.getId())
                .type(invoice.getType())
                .truckNumber(invoice.getTruck().getNumber())
                .placeNumber(invoice.getParkingPlace().getPlaceNumber())
                .truckManagerEmail(invoice.getTruckManager().getEmail())
                .parkingManagerEmail(invoice.getParkingManager().getEmail())
                .creationDate(invoice.getCreationDate())
                .price(invoice.getPrice())
                .build();
    }
}
