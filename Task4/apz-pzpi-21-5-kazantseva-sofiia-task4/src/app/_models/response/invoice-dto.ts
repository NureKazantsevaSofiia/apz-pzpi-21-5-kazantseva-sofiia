import {ItemDTO} from "./item-dto";

export class InvoiceDTO {
  id: bigint;
  type: string;
  truckNumber: string;
  placeNumber: string;
  items: ItemDTO[];
  truckManagerEmail: string;
  parkingManagerEmail: string;
  creationDate: Date;
  description: string;
  price: number;
  signedByTruckManagerDate: Date;
  signedByParkingManagerDate: Date;
  signedByTruckManager: boolean;
  signedByParkingManager: boolean;


  constructor(id: bigint, type: string, truckNumber: string, placeNumber: string, items: ItemDTO[],
              truckManagerEmail: string, parkingManagerEmail: string, creationDate: Date,
              description: string, price: number, signedByTruckManagerDate: Date,
              signedByParkingManagerDate: Date, signedByTruckManager: boolean, signedByParkingManager: boolean) {
    this.id = id;
    this.type = type;
    this.truckNumber = truckNumber;
    this.placeNumber = placeNumber;
    this.items = items;
    this.truckManagerEmail = truckManagerEmail;
    this.parkingManagerEmail = parkingManagerEmail;
    this.creationDate = creationDate;
    this.description = description;
    this.price = price;
    this.signedByTruckManagerDate = signedByTruckManagerDate;
    this.signedByParkingManagerDate = signedByParkingManagerDate;
    this.signedByTruckManager = signedByTruckManager;
    this.signedByParkingManager = signedByParkingManager;
  }
}
