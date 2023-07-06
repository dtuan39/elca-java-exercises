import {DatePipe} from '@angular/common';
import {Component, OnInit} from '@angular/core';
import {Project, Status} from "../../../models/project.interface";
import {ProjectService} from "../../../services/project.service";
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {TranslateService} from "@ngx-translate/core";
import {config} from "rxjs";

@Component({
  selector: 'app-view-projects',
  templateUrl: './view-projects.component.html',
  styleUrls: ['./view-projects.component.scss']
})
export class ViewProjectsComponent implements OnInit {
  tableData: Array<Project> = [];
  selectedStatus: string = "Project status"; // The selected status from the dropdown
  keyword: string = ''; // The keyword to search for
  page = 1; //The current page. Page numbers start with 1.
  pageSize = 5; //The number of items per page.
  collectionSize = 0; // The total number of items in the collection. Make sure this is set after fetching the data.
  protected readonly Status = Status; // Expose the Status enum to the template
  protected readonly confirm = confirm; // Expose the confirm function to the template

  constructor(public translate: TranslateService, private projectService: ProjectService, private dialog: MatDialog, private router: Router) {
  }

  ngOnInit() {
    this.selectedStatus = this.projectService.getStatus() === '' ? 'Project status' : this.projectService.getStatus();
    this.keyword = this.projectService.getKeyword()
    this.initTableData();
  }

  initTableData() {
    if (this.selectedStatus === 'Project status' && this.keyword === '') {//if no search criteria, load all projects
      this.loadProjects();
    } else {
      this.searchProjects();
    }
  }

  navigateToEditPage(project: Project) {//Click on the project id to navigate to the edit page
    // const keyword = (document.getElementById('search') as HTMLInputElement).value;
    // if (this.selectedStatus === 'Project status' && keyword === '') {//if no search criteria, load all projects
    //   return;
    // }
    this.router.navigate(['/view-project', project.id]);
  }

  convertDate(date: string): string {
    const myDate = new Date(date);
    const datePipe = new DatePipe('en-US');
    const formattedDate = datePipe.transform(myDate, 'dd.MM.yyyy') ?? '';
    return formattedDate;
  }

  deleteProject(project: number, confirm: boolean): void {
    console.log(confirm)
    if (confirm) {
      this.projectService.deleteProject(project).subscribe(() => {
        // Handle successful deletion
        console.log('Project deleted successfully');
        // Optionally, you can refresh the project list or perform any other necessary actions
        this.initTableData();
      }, (error: any) => {
        // Handle error
        console.error('Error occurred during project deletion:', error);
      });
    }
  }

  deleteSelectedProjects(confirm: boolean): void {//Delete multiple projects
    // Filter the selected projects with status "NEW"
    const selectedNewProjects = this.projectService.selectedProjects;
    if (confirm) {
      // Delete the selected new projects
      selectedNewProjects.forEach((projectNumber) => {
        // Implement your project deletion logic here
        // You can call your delete service or perform any other necessary operations
        // using the project object
        // Example:
        this.projectService.deleteProject(projectNumber).subscribe(() => {
          // Handle successful deletion
          console.log('Project deleted successfully');
          // Optionally, you can refresh the project list or perform any other necessary actions
          this.initTableData();
        }, (error: any) => {
          // Handle error
          console.error('Error occurred during project deletion:', error);
        });
      });
      // Clear the selection
      this.projectService.selectedProjects = [];
    }
  }

  resetSearch(): void {
    // Clear the search criteria and fetch all projects
    this.page = 1;
    this.selectedStatus = 'Project status';
    // clear the search criteria by id search box
    this.keyword = '';
    // Implement your logic here to reset the search
    this.projectService.setKeyword(this.keyword);
    this.projectService.setStatus(this.selectedStatus);
    this.projectService.selectedProjects = [];
    // Load all projects at the first page
    this.loadProjects();
  }

  loadProjects() {//load from services
    this.projectService.getProjects(this.page, this.pageSize)
      .subscribe(response => {
        // Set the projects data
        this.tableData = response.projects;
        // Set the total count for pagination
        this.collectionSize = response.totalCount;
      });
  }

  searchProjects() {
    //get searchCriteria from the search box
    if (this.selectedStatus === this.translate.instant('Project status') && this.keyword === '') {
      return;//if no search criteria, load all projects
    }
    this.projectService.setKeyword(this.keyword);
    this.projectService.setStatus(this.selectedStatus);
    this.projectService.selectedProjects = [];
    // Perform search logic based on the search criteria
    // For example, you can call an API or filter an existing list of projects
    // Here's a sample console log to demonstrate the function
    // console.log('Searching projects with criteria:', searchCriteria);
    //this.selectedStatus = Status;
    this.projectService.searchProjects(this.keyword, this.selectedStatus as Status, this.page, this.pageSize)
      .subscribe(resp => {
        // Set the total count for pagination
        this.collectionSize = resp.totalCount;
        // Set the projects data
        this.tableData = resp.projects;
      }, (error: any) => {
        console.error('Error occurred:', error);
      });
  }

  selectStatus(event: Event, status: string): void {
    event.preventDefault();
    this.selectedStatus = status;
    this.page = 1;
    this.searchProjects();
  }

  // Handle page change event
  onPageChange(page: number) {
    this.page = page;
    // Fetch data for the new page or update the displayed content
    this.initTableData();
  }

  //
  isSelectedProject(number: number): boolean {
    return this.projectService.selectedProjects.includes(number);
  }

  toggleSelectedProject(number: number): void {
    if (this.projectService.selectedProjects.includes(number)) {
      this.projectService.selectedProjects = this.projectService.selectedProjects.filter((projectNumber) => projectNumber !== number);
    } else {
      this.projectService.selectedProjects.push(number);
    }
  }

  getSelectedProjectSize(): number {
    return this.projectService.selectedProjects.length;
  }
}


