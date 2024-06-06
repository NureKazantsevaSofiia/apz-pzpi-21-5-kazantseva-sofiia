export class ShortTruckDTO {
  id: bigint;
  number: string;
  truckManagerEmail: string;

  constructor(id: bigint, number: string, truckManagerEmail: string) {
    this.id = id;
    this.number = number;
    this.truckManagerEmail = truckManagerEmail;
  }
}
