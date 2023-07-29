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
  selector: '.add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.scss'],
})
export class AddProjectComponent implements OnInit {
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

  public onAddProject(addForm: NgForm): void {
    //data cua form
    console.log(addForm.value);

    if (addForm.invalid) {
      this.globalErr = 'Please enter all the mandatory fields (*)';
      this.numberErr = ''
      return;
    }

    //change the string data type of endDate and startDate from the form.value to Date datatype
    const startTime = new Date(addForm.value.startDate);
    const endTime = new Date(addForm.value.endDate);

    //check valid end time
    if (startTime > endTime) {
      this.ennDateErr = 'End date must be after Start date';
      return;
    }

    //data cua response duoc map tu dto qua interface Project
    this.projectService.addProject(addForm.value).subscribe(
      (response: Project) => {
        console.log(response);
        addForm.reset(); //to reset all fields of form when you want to re-add new amp
        this.router.navigateByUrl('/list');
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        if (error.error.includes('project number already existed')) {
          this.numberErr = error.error;
        }
        this.globalErr = 'Create project failed';
        console.log(this.numberErr);
        addForm.reset(); //reset even if there's an error
      }
    );
  }

  public onUpdateProject(addForm: NgForm): void {
    console.log(addForm.value);

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
