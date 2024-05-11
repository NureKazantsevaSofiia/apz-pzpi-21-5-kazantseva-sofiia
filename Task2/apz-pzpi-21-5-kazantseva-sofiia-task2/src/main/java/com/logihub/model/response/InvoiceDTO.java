package com.logihub.model.response;

import com.logihub.model.enums.InvoiceType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class InvoiceDTO {

    private Long id;

    private InvoiceType type;

    private String truckNumber;

    private String placeNumber;

    private List<ItemDTO> items;

    private String truckManagerEmail;

    private String parkingManagerEmail;

    private LocalDateTime creationDate;

    private String description;

    private Double price;

    private LocalDateTime signedByTruckManagerDate;

    private LocalDateTime signedByParkingManagerDate;

    private Boolean signedByTruckManager;

    private Boolean signedByParkingManager;
}
