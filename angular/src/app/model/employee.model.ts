export class Employee {
  public id: number;
  public visa: string;
  public firstName: string;
  public lastName: string;

  constructor(id: number, visa: string, firstName: string, lastName: string) {
    this.id = id;
    this.visa = visa;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
