import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Projects} from "./projects";
import {AppService} from "../app.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {group} from "@angular/animations";

@Component({
  selector: 'app-project', templateUrl: './project.component.html', styleUrls: ['./project.component.css']
})
export class ProjectComponent {
  createproject!: FormGroup;
  updateproject!: FormGroup;


  message: string;
  editMode: boolean = false;

  public projects: Projects[] = [];
  public project_parent: Projects[] = [];
  protected readonly onclick = onclick;
  protected readonly group = group;

  // getProject(projectNumber: number){
  //   this.projectsService.findProject(projectNumber).subscribe(
  //     (response: Projects[]) => {
  //       this.projectNumber_parent = response;
  //     },
  //     (error: HttpErrorResponse) => {
  //       this.router.navigateByUrl('/error');
  //       alert(error.message);
  //     }
  //   );
  // }

  constructor(private projectsService: AppService, private fb: FormBuilder, private router: Router) {
  }

  get projectNumber(): number {
    return this.createproject.get('projectNumber').value;
  }

  // onSubmit() {
  //   if (this.createproject.valid) {
  //
  //     this.projectsService.addProjects(this.createproject.value).subscribe(
  //       (response: Projects) => {
  //         console.log(response);
  //         this.router.navigateByUrl('/list');
  //       },
  //       (error: HttpErrorResponse) => {
  //         if (error.error.message === "The project number already existed. Please select a different number") {
  //           this.message = error.error.message;
  //           console.log(this.projectNumber);
  //           this.title = "Edit Project Information";
  //           this.projectsService.findProject(this.projectNumber).subscribe(
  //             (response: Projects[]) => {
  //               this.projects = response;
  //             },
  //             (error: HttpErrorResponse)=>{
  //               console.log(error)
  //             }
  //           );
  //         }
  //         else {
  //           this.router.navigateByUrl('/error');
  //         }
  //       }
  //     );
  //   }
  // }

  ngOnInit() {
    this.createproject = this.fb.group({
      // group_id: ['', Validators.required],
      projectNumber: ['', Validators.required],
      name: ['', Validators.required],
      customer: ['', Validators.required],
      status: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: [''],
      group: this.fb.group({
        id: ['', Validators.required],
        version: ['1',Validators.required]
      }),
      version: ['1']
    });

    // this.getProject();
  }

  onSubmit() {
    if (!this.editMode) {
      this.OnCreate();
    } else {
      this.onUpdate();
    }
  }

  findProject() {
    console.log(this.projectNumber);
    this.projectsService.findProject(this.projectNumber).subscribe((response: Projects[]) => {
      this.project_parent = response;
      this.editMode = true;
      this.project_parent.forEach(value => {
        this.createproject.controls['name'].patchValue(value.name);
        this.createproject.get('group.id').setValue(value.groupId);
        this.createproject.controls['customer'].patchValue(value.customer);
        this.createproject.controls['status'].patchValue(value.status);
        this.createproject.controls['startDate'].patchValue(value.startDate);
        // this.createproject.controls['group.version'].patchValue(value.version);
        this.createproject.controls['version'].patchValue(value.version);
        this.createproject.controls['endDate'].setValue(value.endDate);
      })
      // this.onUpdate();
      // this.projectsService.updateProject()
    }, (error: HttpErrorResponse) => {
      console.log(error)
    });
  }

  onUpdate() {
    this.projectsService.updateProject(this.createproject.value, this.projectNumber).subscribe((response: Projects) => {
      this.router.navigateByUrl('/list');
    }, (error: HttpErrorResponse) => {
      console.log(error)
    });
  }

  private OnCreate() {
    if (this.createproject.valid) {
      this.projectsService.addProjects(this.createproject.value).subscribe((response: Projects) => {
        console.log(response);
        this.router.navigateByUrl('/list');
      }, (error: HttpErrorResponse) => {
        console.log(error.error.message)
        if (error.error.message === "The project number already existed. Please select a different number") {
          this.editMode = true;
          //this.router.navigateByUrl('/project/update');
          this.findProject();
        } else {
          this.router.navigateByUrl('/error');
        }
      });
    }
  }
}
