export class RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  companyId: bigint;

  constructor(firstName: string, lastName: string, email: string, password: string, companyId: bigint) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.companyId = companyId;
  }
}
