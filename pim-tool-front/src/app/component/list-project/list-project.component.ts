import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Project, Status } from 'src/app/model/project';
import { ProjectService } from 'src/app/service/project.service';
import { SharedService } from 'src/app/service/shared.service';

@Component({
  selector: '.list-project',
  templateUrl: './list-project.component.html',
  styleUrls: ['./list-project.component.scss'],
})
export class ListProjectComponent implements OnInit {
  public projects: Project[] = [];
  public deleteProjectId!: number;
  protected readonly Status = Status;
  projectsArr: Project[] = []; // Your projects array
  savedSearchText: any;
  savedStatus!: String;

  selectedItems: Project[] = [];
  page = 1;

  constructor(
    private projectService: ProjectService,
    private router: Router,
    private route: ActivatedRoute,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.savedSearchText = this.sharedService.getSavedSearchText();
    this.savedStatus = this.sharedService.getSavedSatus();
    if (this.savedSearchText == '' && this.savedStatus == '') {
      this.getProjects();
    }

    if (this.savedSearchText != undefined || this.savedStatus != undefined) {
      console.log(this.savedSearchText);
      console.log(this.savedStatus);
      this.searchProjects2(this.savedSearchText, this.savedStatus);
    } else {
      this.getProjects();
    }

    // this.router.events.subscribe((event) => {
    //   if (event instanceof NavigationEnd) {
    //     // Call the getProjects() method when the component is navigated to
    //     this.getProjects();
    //   }
    // });
  }

  public getProjects(): void {
    this.projectService.getProjects().subscribe(
      (response: Project[]) => {
        console.log(response);

        this.projects = response;
        this.projects.sort((a, b) => a.number - b.number); //sort list project by number asc
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    ); //subcribe de theo doi cac thay doi cua du lieu, tbao khi du lieu dc tra ve tu server
  }

  public searchProjects2(searchText: any, status: any) {
    if (searchText == '' && status == '') {
      return;
    }

    this.projectService
      .searchProjects(
        searchText == '' ? null : searchText,
        status == '' ? null : status
      )
      .subscribe(
        (response: Project[]) => {
          console.log('day la search');
          console.log(response);
          this.projects = response;
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
  }

  public searchProjects(searchForm: NgForm): void {
    console.log(searchForm.value);

    //if 2 fields are empty, load all projects and return
    if (searchForm.value.searchText == '' && searchForm.value.status == '') {
      this.getProjects();
      return;
    }

    this.sharedService.setSavedSearchText(searchForm.value.searchText);
    this.sharedService.setSavedSatus(searchForm.value.status);

    this.projectService
      // .searchProjects(
      //   searchForm.value.searchText == '' ? null : searchForm.value.searchText,
      //   searchForm.value.status == '' ? null : searchForm.value.status
      // )
      .searchProjects(searchForm.value.searchText, searchForm.value.status)
      .subscribe(
        (response: Project[]) => {
          console.log('day la search');
          console.log(response);
          this.projects = response;
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
  }

  public resetSearchHandle(searchForm: NgForm) {
    searchForm.reset();
    this.sharedService.setSavedSearchText('');
    this.sharedService.setSavedSatus('');
    this.getProjects();
  }

  public setProjectIdDelete(projectId: number) {
    this.deleteProjectId = projectId;
  }

  public deleteProject(projectId: number): void {
    //đưa cái form xuống database để add
    this.projectService.deleteProject(projectId).subscribe(
      (response: void) => {
        console.log(response);
        this.getProjects();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

    this.selectedItems = [];
  }

  public deleteSelectedProject(): void {
    this.selectedItems.forEach((project) => {
      this.projectService.deleteProject(project.id).subscribe(
        (response: void) => {
          console.log(response);
          this.getProjects();
        },
        (error: HttpErrorResponse) => {
          console.log(project.id);
          alert(error.message);
        }
      );

      // this.projectService.deleteProject(project.id);
    });

    this.selectedItems = [];
  }

  toggleSelection(project: Project) {
    if (this.isSelected(project)) {
      // Item is already selected, remove it from the array
      // creates a new array containing only the elements that satisfy a given condition (use when user check the box twice
      //to remove the checkbox, the array is re-updated)
      this.selectedItems = this.selectedItems.filter(
        (item) => item.id !== project.id
      );
    } else {
      // Item is not selected, add it to the array
      this.selectedItems.push(project);
    }
    console.log(this.selectedItems);
  }

  //to check if at least one item (project) in selectedItems has the id equal with the project we have pass as argument
  //it will return true
  isSelected(project: Project) {
    return this.selectedItems.some((item) => item.id === project.id);
  }

  navigateToUpdateProject(project: Project) {
    this.router.navigate(['/update', project.number]);
    // try
  }
}
