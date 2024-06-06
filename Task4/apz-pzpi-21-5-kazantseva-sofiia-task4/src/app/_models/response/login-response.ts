export class LoginResponse {
  id: bigint;
  token: string;
  role: string;
  name: string;
  surname: string;
  avatar: string;

  constructor(id: bigint, token: string, role: string, name: string, surname: string, avatar: string) {
    this.id = id;
    this.token = token;
    this.role = role;
    this.name = name;
    this.surname = surname;
    this.avatar = avatar;
  }
}
