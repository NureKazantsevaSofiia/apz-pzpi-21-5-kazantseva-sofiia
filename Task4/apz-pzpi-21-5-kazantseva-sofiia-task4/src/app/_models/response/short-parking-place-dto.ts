export class ShortParkingPlaceDTO {
  id: bigint;
  address: string;
  hourlyPay: number;
  parkingManagerEmail: string;

  constructor(id: bigint, address: string, hourlyPay: number, parkingManagerEmail: string) {
    this.id = id;
    this.address = address;
    this.hourlyPay = hourlyPay;
    this.parkingManagerEmail = parkingManagerEmail;
  }
}
