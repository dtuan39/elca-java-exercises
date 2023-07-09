import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Employee } from '../model/employee.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeeServiceService {
  constructor(private http: HttpClient) {}

  getEmployeesById(ids: string) {
    let params = new HttpParams();

    params = params.append('ids', ids);
    let result: Employee[] = [];

    return this.http.get<Employee[]>('http://localhost:8080/employees/get', {
      params: params,
    });
  }
}
