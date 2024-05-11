package com.logihub.service;

import com.logihub.exception.InvoiceException;
import com.logihub.exception.UserException;
import com.logihub.model.request.ItemRequest;

import java.util.List;

public interface ItemService {

    void addItems(Long invoiceId, List<ItemRequest> itemRequest)
            throws UserException, InvoiceException;
}
