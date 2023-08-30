import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { ProjectService } from 'src/app/service/project.service';
import { Project } from 'src/app/model/project';
import { GroupService } from '../../service/group.service';
import { Group } from 'src/app/model/group';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from 'src/app/service/shared.service';
@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.scss'],
})
export class ProjectDetailComponent {
  @ViewChild('alertPopup') alertPopup!: ElementRef;

  groups: Group[] | undefined;
  updateProject!: Project;
  actionTitle: String = 'New Project';
  btnSubmitContent: String = 'Create Project';
  editMode: boolean = false;
  numberErr: string = '';
  ennDateErr: string = '';
  globalErr: string = 'Please enter all the mandatory fields (*)';

  constructor(
    private projectService: ProjectService,
    private groupService: GroupService,
    private router: Router,
    private route: ActivatedRoute,
    public sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.getGroups();
    this.globalErr = 'Please enter all the mandatory fields (*)';
    const projectNumber: any =
      this.route.snapshot.paramMap.get('projectNumber');
    if (projectNumber) {
      this.editMode = !this.editMode;
      this.getProjectByNumber(projectNumber);
      this.actionTitle = 'Edit Project information';
      this.btnSubmitContent = 'Save Changes';
    }
  }

  public getProjectByNumber(projectNumber: string): void {
    this.projectService.getProjectByNumber(parseInt(projectNumber)).subscribe(
      (response: Project) => {
        this.updateProject = response;
        console.log(this.updateProject);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getGroups(): void {
    this.groupService.getGroups().subscribe(
      (response: Group[]) => {
        this.groups = response;
        console.log(this.groups);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddProject(addForm: NgForm): void {
    console.log(addForm.value);

    if (addForm.invalid) {
      this.globalErr = 'Please enter all the mandatory fields (*)';
      this.numberErr = '';
      return;
    }

    const startTime = new Date(addForm.value.startDate);
    const currentTime = new Date();

    if (startTime < currentTime) {
      this.ennDateErr = 'Start date must be the same or after current date';
      return;
    }

    if (addForm.value.endDate != null) {
      const endTime = new Date(addForm.value.endDate);

      if (startTime >= endTime) {
        this.ennDateErr = 'End date must be the same or after Start date';
        return;
      }
    }

    this.projectService.addProject(addForm.value).subscribe(
      (response: Project) => {
        console.log(response);
        addForm.reset();
        this.router.navigateByUrl('/list');
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        if (error.error.includes('project number already existed')) {
          this.numberErr = error.error;
        }
        this.globalErr = 'Create project failed';
      }
    );
  }

  public onUpdateProject(addForm: NgForm): void {
    console.log(addForm.value);

    if (addForm.invalid) {
      this.globalErr = 'Please enter all the mandatory fields (*)';
      this.numberErr = '';
      return;
    }

    const startTime = new Date(addForm.value.startDate);
    if (addForm.value.endDate != null) {
      const endTime = new Date(addForm.value.endDate);

      if (startTime >= endTime) {
        this.ennDateErr = 'End date must be the same or after Start date';
        return;
      }
    }

    this.projectService.updateProject(addForm.value).subscribe(
      (response: Project) => {
        console.log(response);
        this.getGroups();
        this.router.navigateByUrl('/list');
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  closeAlert() {
    this.alertPopup.nativeElement.style.display = 'none';
  }

  public navigateToList() {
    this.router.navigateByUrl('/list');
  }
}
