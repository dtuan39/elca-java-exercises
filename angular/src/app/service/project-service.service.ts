import {
  HttpClient,
  HttpHeaders,
  HttpParams,
  HttpErrorResponse,
} from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Project } from '../model/project.model';
import { map, tap } from 'rxjs';
import { ProjectPage } from '../model/project-page.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectServiceService {
  private projects: Project[] = [];

  constructor(private http: HttpClient) {}

  fetchProjects(page: number) {
    const httpOptions = {
      headers: new HttpHeaders({
        Accept: 'application/json',
        'Content-Type': 'application/json',
      }),
    };

    return this.http.get<ProjectPage>(
      'http://localhost:8080/projects/all/' + page,
      httpOptions
    );
  }

  deleteProjects(projectNumbers: number[]) {
    return this.http.delete('http://localhost:8080/projects/delete', {
      body: projectNumbers,
    });
  }

  searchProjects(searchValue: string, statusValue: string, offset: number) {
    let param = new HttpParams();
    param = param.append('searchValue', searchValue);
    param = param.append('statusValue', statusValue);
    param = param.append('offset', offset);

    return this.http.get<ProjectPage>('http://localhost:8080/projects/search', {
      params: param,
    });
  }

  saveProject(project: Project) {
    return this.http.post('http://localhost:8080/projects/add', project);
  }

  getProjectByProjectNumber(projectNumber: number) {
    return this.http.get<Project>(
      'http://localhost:8080/projects/' + projectNumber
    );
  }
  updateProject(project: Project) {
    return this.http.put('http://localhost:8080/projects/update', project);
  }
}
