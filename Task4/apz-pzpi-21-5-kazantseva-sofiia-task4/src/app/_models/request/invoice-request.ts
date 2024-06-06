import {ItemRequest} from "./item-request";

export class InvoiceRequest {
  type: string;
  truckNumber: string;
  placeNumber: string;
  items: ItemRequest[];
  description: string;

  constructor(type: string, truckNumber: string, placeNumber: string, items: ItemRequest[], description: string) {
    this.type = type;
    this.truckNumber = truckNumber;
    this.placeNumber = placeNumber;
    this.items = items;
    this.description = description;
  }
}
