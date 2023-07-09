import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Project } from 'src/app/model/project.model';
import { ProjectServiceService } from 'src/app/service/project-service.service';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css'],
})
export class ProjectListComponent implements OnInit {
  pageSize: number = 0;
  isEmpty: boolean = true;
  totalElements: number = 0;
  projects: Project[] = [];
  totalPages: number = 0;
  currentPage: number = 0;
  selectedProjects: Project[] = [];
  canDeleteMultiple: boolean = true;

  searchValue = '';
  statusValue = '';

  constructor(
    private projectService: ProjectServiceService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.projectService.fetchProjects(0).subscribe((response) => {
      this.pageSize = response.size;
      this.isEmpty = response.content.length > 0 ? false : true;
      this.totalElements = response.totalElements;
      this.totalPages = response.totalPages;
      this.projects = response.content;
      this.currentPage = 0;
    });
  }

  onFetchProject(page: number) {
    this.projectService.fetchProjects(page).subscribe((response) => {
      this.pageSize = response.size;
      this.isEmpty = response.content.length > 0 ? false : true;
      this.totalElements = response.totalElements;
      this.totalPages = response.totalPages;
      this.projects = response.content;
      this.currentPage = page;
    });
  }

  onCheck(project: Project) {
    let index = this.selectedProjects.indexOf(project);
    if (index !== -1) {
      this.selectedProjects.splice(index, 1);
    } else {
      this.selectedProjects.push(project);
    }
    this.canDeleteMultiple = true;
    this.selectedProjects.forEach((project) => {
      if (project.status !== 'NEW') {
        this.canDeleteMultiple = false;
      }
    });
  }

  onDeleteProjects() {
    let selectedProjectNumbers: number[] = [];
    this.selectedProjects.forEach((project) =>
      selectedProjectNumbers.push(project.projectNumber)
    );

    this.projectService.deleteProjects(selectedProjectNumbers).subscribe(() => {
      this.selectedProjects = [];
      this.onSearch(this.currentPage);
    });
  }
  onDeleteProject(project: Project) {
    let projectNumber = [project.projectNumber];
    this.projectService.deleteProjects(projectNumber).subscribe(() => {
      this.onSearch(this.currentPage);
    });
  }

  onReset() {
    this.searchValue = '';
    this.statusValue = '';
  }

  onSearch(page: number) {
    this.projectService
      .searchProjects(this.searchValue, this.statusValue, page)
      .subscribe((response) => {
        this.pageSize = response.size;
        this.isEmpty = response.content.length > 0 ? false : true;
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
        this.projects = response.content;
        this.currentPage = page;
      });
  }

  onEditProject(projectNumber: number) {
    this.router.navigate(['edit', projectNumber], {
      relativeTo: this.route,
    });
  }
}
