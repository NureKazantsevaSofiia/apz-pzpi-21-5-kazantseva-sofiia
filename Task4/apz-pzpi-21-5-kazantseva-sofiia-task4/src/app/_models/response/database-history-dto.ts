export class DatabaseHistoryDTO {
  id: bigint;
  adminEmail: string;
  time: Date;
  operation: string;

  constructor(id: bigint, adminEmail: string, time: Date, operation: string) {
    this.id = id;
    this.adminEmail = adminEmail;
    this.time = time;
    this.operation = operation;
  }
}
