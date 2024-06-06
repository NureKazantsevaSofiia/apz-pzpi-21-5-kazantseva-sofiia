export class TruckRequest {
  number: string;
  width: number;
  height: number;
  length: number;
  weight: number;

  constructor(number: string, width: string, height: string, length: string, weight: string) {
    this.number = number;
    this.width = parseFloat(width);
    this.height = parseFloat(height);
    this.length = parseFloat(length);
    this.weight = parseFloat(weight);
  }
}
