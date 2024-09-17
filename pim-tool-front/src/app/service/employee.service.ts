import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private empUrl: string = 'http://localhost:8080/employee';

  constructor(private http: HttpClient) {}

  public getEmployees(){
    return this.http.get<any>(`${this.empUrl}`);
  }

  public searchEmployees(searchText: String){
    return this.http.get<any>(
      `${this.empUrl}/search?value=${searchText}`
    );
  }
}
