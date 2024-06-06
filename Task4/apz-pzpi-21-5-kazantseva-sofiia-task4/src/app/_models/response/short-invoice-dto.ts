export class ShortInvoiceDTO {
  id: bigint;
  type: string;
  truckNumber: string;
  placeNumber: string;
  truckManagerEmail: string;
  parkingManagerEmail: string;
  creationDate: Date;
  price: number;

  constructor(id: bigint, type: string, truckNumber: string, placeNumber: string,
              truckManagerEmail: string, parkingManagerEmail: string, creationDate: Date, price: number) {
    this.id = id;
    this.type = type;
    this.truckNumber = truckNumber;
    this.placeNumber = placeNumber;
    this.truckManagerEmail = truckManagerEmail;
    this.parkingManagerEmail = parkingManagerEmail;
    this.creationDate = creationDate;
    this.price = price;
  }
}
