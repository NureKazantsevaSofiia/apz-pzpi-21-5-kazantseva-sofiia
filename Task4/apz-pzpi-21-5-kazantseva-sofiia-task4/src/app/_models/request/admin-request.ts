export class AdminRequest {
  avatar: string;
  firstName: string;
  lastName: string;
  password: string;

  constructor(avatar: string, firstName: string, lastName: string, password: string) {
    this.avatar = avatar;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }
}
