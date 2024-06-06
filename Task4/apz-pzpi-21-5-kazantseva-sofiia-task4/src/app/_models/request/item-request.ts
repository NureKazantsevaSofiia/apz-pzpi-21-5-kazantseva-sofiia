export class ItemRequest {
  name: string;
  weight: number;
  price: number;
  amount: bigint;

  constructor(name: string, weight: number, price: number, amount: bigint) {
    this.name = name;
    this.weight = weight;
    this.price = price;
    this.amount = amount;
  }
}
