import { Employee } from './employee.model';

export class Project {
  public groupId: number;
  public projectNumber: number;
  public name: string;
  public employees: Employee[];
  public customer: string;
  public status: string;
  public startDate: Date;
  public endDate: Date;

  constructor(
    groupId: number,
    projectNumber: number,
    name: string,
    employees: Employee[],
    customer: string,
    status: string,
    startDate: Date,
    endDate: Date
  ) {
    this.groupId = groupId;
    this.projectNumber = projectNumber;
    this.name = name;
    this.employees = employees;
    this.customer = customer;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
