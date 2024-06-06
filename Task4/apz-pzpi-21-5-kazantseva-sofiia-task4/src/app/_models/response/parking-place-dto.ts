export class ParkingPlaceDTO {
  id: bigint;
  placeNumber: string;
  address: string;
  minWidth: number;
  minHeight: number;
  minLength: number;
  minWeight: number;
  maxWidth: number;
  maxHeight: number;
  maxLength: number;
  maxWeight: number;
  hourlyPay: number;


  constructor(id: bigint, placeNumber: string, address: string, minWidth: number, minHeight: number,
              minLength: number, minWeight: number, maxWidth: number, maxHeight: number, maxLength: number,
              maxWeight: number, hourlyPay: number) {
    this.id = id;
    this.placeNumber = placeNumber;
    this.address = address;
    this.minWidth = minWidth;
    this.minHeight = minHeight;
    this.minLength = minLength;
    this.minWeight = minWeight;
    this.maxWidth = maxWidth;
    this.maxHeight = maxHeight;
    this.maxLength = maxLength;
    this.maxWeight = maxWeight;
    this.hourlyPay = hourlyPay;
  }
}
