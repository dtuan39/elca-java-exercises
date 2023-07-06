import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Projects} from "./project/projects";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AppService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getProjects(): Observable<Projects[]> {
    return this.http.get<Projects[]>(`${this.apiServerUrl}/projects/all`);
  }

  public addProjects(projects: Projects): Observable<Projects> {
    return this.http.post<Projects>(`${this.apiServerUrl}/projects/add`, projects);
  }

  public findProject(projectNumber: number): Observable<Projects[]>{
    return this.http.get<Projects[]>(`${this.apiServerUrl}/projects/find/${projectNumber}`);
  }

  public searchProject(value: String): Observable<Projects[]>{
    return this.http.get<Projects[]>(`${this.apiServerUrl}/projects/search/${value}`);
  }
}
