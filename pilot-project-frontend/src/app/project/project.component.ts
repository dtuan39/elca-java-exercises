import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Projects} from "./projects";
import {AppService} from "../app.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent {
  createproject!: FormGroup;

  title = 'New Project';
  message: string;

  public projects: Projects[] = [];

  constructor(private projectsService: AppService, private fb: FormBuilder, private router: Router) {
  }

  ngOnInit() {
    this.createproject = this.fb.group({
      groupId: ['', Validators.required],
      projectNumber: ['', Validators.required],
      name: ['', Validators.required],
      customer: ['', Validators.required],
      status: ['', Validators.required],
      startDate: ['', Validators.required],
      version: ['']
    })
  }

  get projectNumber():number {
    return this.createproject.get('projectNumber').value;
  }

  protected readonly onclick = onclick;

  onClickSubmit(value: any) {
    alert("Not allow")
  }

  onSubmit() {
    if (this.createproject.valid) {
      this.projectsService.addProjects(this.createproject.value).subscribe(
        (response: Projects) => {
          console.log(response);
          this.router.navigateByUrl('/list');
        },
        (error: HttpErrorResponse) => {
          if (error.error.message === "The project number already existed. Please select a different number") {
            this.message = error.error.message;
            console.log(this.projectNumber);
            this.title = "Edit Project Information";
            this.projectsService.findProject(this.projectNumber).subscribe(
              (response: Projects[]) => {
                this.projects = response;
              },
            (error: HttpErrorResponse)=>{
                console.log(error)
            }
            );
          }
          else {
            this.router.navigateByUrl('/error');
          }
        }
      );
    }
  }

}
