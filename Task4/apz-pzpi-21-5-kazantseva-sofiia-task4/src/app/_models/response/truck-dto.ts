import {TruckManagerDTO} from "./truck-manager-dto";

export class TruckDTO {
  id: bigint;
  number: string;
  truckManager: TruckManagerDTO;
  width: number;
  height: number;
  length: number;
  weight: number;

  constructor(id: bigint, number: string, truckManager: TruckManagerDTO, width: number, height: number, length: number, weight: number) {
    this.id = id;
    this.number = number;
    this.truckManager = truckManager;
    this.width = width;
    this.height = height;
    this.length = length;
    this.weight = weight;
  }
}
