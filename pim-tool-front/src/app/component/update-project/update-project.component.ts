import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Group } from 'src/app/model/group';
import { Project } from 'src/app/model/project';
import { GroupService } from 'src/app/service/group.service';
import { ProjectService } from 'src/app/service/project.service';
import { SharedService } from 'src/app/service/shared.service';

@Component({
  selector: 'update-project',
  templateUrl: './update-project.component.html',
  styleUrls: ['./update-project.component.scss'],
})
export class UpdateProjectComponent implements OnInit {
  @ViewChild('alertPopup') alertPopup!: ElementRef;

  groups: Group[] | undefined;
  updateProject!: Project;
  actionTitle: String = 'New Project';
  btnSubmitContent: String = 'Create Project';
  editMode: boolean = false;
  ennDateErr: string = '';

  constructor(
    private projectService: ProjectService,
    private groupService: GroupService,
    private router: Router,
    private route: ActivatedRoute,
    public sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.getGroups();

    //get project's number from the parameter after navigating from list
    const projectNumber: any =
      this.route.snapshot.paramMap.get('projectNumber');

    //if projectNumber is not null, ask service to get the project with passed number
    if (projectNumber) {
      this.editMode = !this.editMode;
      this.getProjectByNumber2(projectNumber);
      this.actionTitle = 'Edit Project information';
      this.btnSubmitContent = 'Save Changes';
    }
  }

  public getProjectByNumber2(projectNumber: string): void {
    this.projectService.getProjectByNumber(parseInt(projectNumber)).subscribe(
      (response: Project) => {
        this.updateProject = response;
        console.log(this.updateProject);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    ); //subcribe de theo doi cac thay doi cua du lieu, tbao khi du lieu dc tra ve tu server
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
    ); //subcribe de theo doi cac thay doi cua du lieu, tbao khi du lieu dc tra ve tu server
  }

  public onUpdateProject(addForm: NgForm): void {
    console.log(addForm.value);

    const startTime = new Date(addForm.value.startDate);
    const endTime = new Date(addForm.value.endDate);

    if (startTime > endTime) {
      this.ennDateErr = 'End date must be after Start date';
      return;
    }

    //đưa cái form xuống database để add
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
