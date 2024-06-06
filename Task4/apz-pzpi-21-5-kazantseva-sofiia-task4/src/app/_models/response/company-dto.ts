export class CompanyDTO {
  id: bigint;
  name: string;
  logo: string;
  type: string;

  constructor(id: bigint, name: string, logo: string, type: string) {
    this.id = id;
    this.name = name;
    this.logo = logo;
    this.type = type;
  }
}
