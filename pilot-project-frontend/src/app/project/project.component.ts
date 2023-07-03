import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Projects} from "./projects";
import {AppService} from "../app.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent{
  createproject!: FormGroup;

  public projects: Projects[];

  constructor(private projectsService: AppService, private fb: FormBuilder) {
    this.projects = [];
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

  protected readonly onclick = onclick;

  onClickSubmit(value: any) {
    alert("Not allow")
  }

  // onAddProject(createproject: NgForm) {
  //   this.projectsService.addProjects(createproject.value).subscribe(
  //     (response: Projects) => {
  //       console.log(response);
  //     },
  //     (error: HttpErrorResponse) => {
  //       alert(error.message);
  //     }
  //   );
  // }

  onSubmit() {
    if (this.createproject.valid) {
      this.projectsService.addProjects(this.createproject.value).subscribe(
        (response: Projects) => {
          console.log(response);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }
}
