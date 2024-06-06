export class ManagerRequest {
  avatar: string;
  firstName: string;
  lastName: string;

  constructor(avatar: string, firstName: string, lastName: string) {
    this.avatar = avatar;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
