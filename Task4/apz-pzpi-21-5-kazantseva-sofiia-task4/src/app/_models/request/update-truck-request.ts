export class UpdateTruckRequest {
  width: number;
  height: number;
  length: number;
  weight: number;

  constructor(width: string, height: string, length: string, weight: string) {
    this.width = parseFloat(width);
    this.height = parseFloat(height);
    this.length = parseFloat(length);
    this.weight = parseFloat(weight);
  }
}
