export class PlaceRequest {
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

  constructor(placeNumber: string, address: string, minWidth: string, minHeight: string,
              minLength: string, minWeight: string, maxWidth: string, maxHeight: string,
              maxLength: string, maxWeight: string, hourlyPay: string) {
    this.placeNumber = placeNumber;
    this.address = address;
    this.minWidth = parseFloat(minWidth);
    this.minHeight = parseFloat(minHeight);
    this.minLength = parseFloat(minLength);
    this.minWeight = parseFloat(minWeight);
    this.maxWidth = parseFloat(maxWidth);
    this.maxHeight = parseFloat(maxHeight);
    this.maxLength = parseFloat(maxLength);
    this.maxWeight = parseFloat(maxWeight);
    this.hourlyPay = parseFloat(hourlyPay);
  }
}
