import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project, ProjectMembers } from '../model/project';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private projectUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  public addProject(ProjectMembers: ProjectMembers): Observable<ProjectMembers> {
    return this.http.post<ProjectMembers>(`${this.projectUrl}/project/create`, ProjectMembers);
  }

  public getProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.projectUrl}/project/all`);
  }

  public searchProjects(
    searchText: String,
    status: String
  ): Observable<Project[]> {
    return this.http.get<Project[]>(
      `${this.projectUrl}/project/search?searchText=${searchText}&status=${status}`
    );
  }

  public deleteProject(projectId: number): Observable<void> {
    return this.http.delete<void>(
      `${this.projectUrl}/project/delete?id=${projectId}`
    );
  }

  public getProjectByNumber(number: number): Observable<Project> {
    return this.http.get<Project>(`${this.projectUrl}/project/${number}`);
  }

  public updateProject(ProjectMembers: ProjectMembers): Observable<Project> {
    return this.http.put<Project>(`${this.projectUrl}?id=${ProjectMembers.projectDto.id}`, ProjectMembers);
  }
}
