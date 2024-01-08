import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project, ProjectMembers } from '../model/project';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private projectUrl: string = 'http://localhost:8080/project';

  constructor(private http: HttpClient) {}

  public addProject(ProjectMembers: ProjectMembers): Observable<ProjectMembers> {
    return this.http.post<ProjectMembers>(`${this.projectUrl}/create`, ProjectMembers);
  }

  public getProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.projectUrl}/all`);
  }

  public searchProjects(
    searchText: String,
    status: String
  ): Observable<Project[]> {
    return this.http.get<Project[]>(
      `${this.projectUrl}/search?searchText=${searchText}&status=${status}`
    );
  }

  public deleteProject(projectId: number): Observable<void> {
    return this.http.delete<void>(
      `${this.projectUrl}/delete?id=${projectId}`
    );
  }

  public getProjectByNumber(number: number): Observable<Project> {
    return this.http.get<Project>(`${this.projectUrl}/${number}`);
  }

  public updateProject(ProjectMembers: ProjectMembers): Observable<ProjectMembers> {
    return this.http.put<ProjectMembers>(`${this.projectUrl}/update`, ProjectMembers);
  }

  public getProjectsCount(): Observable<number> {
    return this.http.get<number>(`${this.projectUrl}/count`);
  }

  public getProjectsPagination(limit: number, skip: number): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.projectUrl}/pagination?limit=${limit}&skip=${skip}`);
  }

  public searchProjectsWithPagination(
    searchText: String,
    status: String,
    limit: number,
    skip: number
  ): Observable<Project[]> {
    return this.http.get<Project[]>(
      `${this.projectUrl}/search-with-pagination?searchText=${searchText}&status=${status}&limit=${limit}&skip=${skip}`
    );
  }
}
