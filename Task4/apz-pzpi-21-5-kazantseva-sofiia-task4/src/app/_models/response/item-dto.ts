export class ItemDTO {
  itemId: bigint;
  name: string;
  weight: number;
  price: number;
  amount: bigint;
  totalWeight: number;
  totalPrice: number;

  constructor(itemId: bigint, name: string, weight: number, price: number, amount: bigint,
              totalWeight: number, totalPrice: number) {
    this.itemId = itemId;
    this.name = name;
    this.weight = weight;
    this.price = price;
    this.amount = amount;
    this.totalWeight = totalWeight;
    this.totalPrice = totalPrice;
  }
}
