import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Project, Status} from "../models/project.interface";
import {Injectable} from '@angular/core';
import {map, Observable} from "rxjs";

interface ProjectsResponse {
  projects: Project[];
  totalCount: number;
}

@Injectable({
  providedIn: 'root'
})

export class ProjectService {
  private baseUrl = 'http://localhost:8080/projects'; // Địa chỉ URL backend
  // TODO : Centralize Error Handling
  // TODO : Use Strongly Typed Responses
  // TODO : Provide Detailed Error Messages
  // TODO : Handle Different Types of Errors
  // TODO : Provide User-Friendly Error Messages
  // TODO : Implement Error Logging

  private keyword : string = '';
  private status : string = 'Project status';
  selectedProjects: number[] = []; // the selected projects from the table
  constructor(private http: HttpClient) {
  }


  createProjects(projectData: any): Observable<any> {
    return this.http.post(this.baseUrl, projectData);
  }

  getProjects(page: number, limit: number): Observable<ProjectsResponse> {
    page = page - 1; // Because the backend starts from 0
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());
    // Make the HTTP request
    return this.http.get<ProjectsResponse>(this.baseUrl, {params});
  }

  searchProjects(keyword: string, status: Status, page: number, limit: number): Observable<ProjectsResponse> {
    page = page - 1; // Because the backend starts from 0
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString())
      .set('keyword', keyword)
      .set('status', status);
    return this.http.get<ProjectsResponse>(this.baseUrl.concat('/search'), {params});
  }

  updateProject(projectData: any): Observable<any> {
    return this.http.put(this.baseUrl, projectData);
  }

  deleteProject(projectNumber: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${projectNumber}`);
  }

  getProjectById(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.baseUrl}/${id}`);
  }

  setKeyword(keyword: string) {
    this.keyword = keyword;
  }

  getKeyword(): string {
    return this.keyword;
  }

  setStatus(status: string) {
    this.status = status;
  }

  getStatus(): string {
    return this.status;
  }
}
