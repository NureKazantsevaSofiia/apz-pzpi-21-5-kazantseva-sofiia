package com.logihub.model.response;

import com.logihub.model.enums.InvoiceType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ShortInvoiceDTO {

    private Long id;

    private InvoiceType type;

    private String truckNumber;

    private String placeNumber;

    private String truckManagerEmail;

    private String parkingManagerEmail;

    private LocalDateTime creationDate;

    private Double price;
}
