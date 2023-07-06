import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Group} from "../models/group.interface";
import {Observable} from "rxjs";
import {Project} from "../models/project.interface";

@Injectable({
  providedIn: 'root'
})
export class GroupService {
  private baseUrl = 'http://localhost:8080/groups'; // Địa chỉ URL backend

  constructor(private http: HttpClient) {
  }

  getGroups() : Observable<Group[]>{
    return this.http.get<Group[]>(this.baseUrl);
  }
}
