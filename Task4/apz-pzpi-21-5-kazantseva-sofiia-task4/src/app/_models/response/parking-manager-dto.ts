import {CompanyDTO} from "./company-dto";

export class ParkingManagerDTO {
  id: bigint;
  firstName: string;
  lastName: string;
  email: string;
  avatar: string;
  company: CompanyDTO;

  constructor(id: bigint, firstName: string, lastName: string, email: string,
              avatar: string, company: CompanyDTO) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.avatar = avatar;
    this.company = company;
  }
}
