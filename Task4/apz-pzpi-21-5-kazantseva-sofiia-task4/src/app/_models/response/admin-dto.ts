export class AdminDTO {
  id: bigint;
  firstName: string;
  lastName: string;
  email: string;
  avatar: string;
  dateOfApproving: Date;
  dateOfAdding: Date;
  addedBy: string;
  approved: boolean;
  newAccount: boolean;

  constructor(id: bigint, firstName: string, lastName: string, email: string,
              avatar: string, dateOfApproving: Date, dateOfAdding: Date, addedBy: string,
              approved: boolean, newAccount: boolean) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.avatar = avatar;
    this.dateOfApproving = dateOfApproving;
    this.dateOfAdding = dateOfAdding;
    this.addedBy = addedBy;
    this.approved = approved;
    this.newAccount = newAccount;
  }
}
