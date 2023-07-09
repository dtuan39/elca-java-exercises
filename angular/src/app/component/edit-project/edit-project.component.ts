import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Employee } from 'src/app/model/employee.model';
import { HttpErrorResponse } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';

import { Project } from 'src/app/model/project.model';
import { ProjectServiceService } from 'src/app/service/project-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { EmployeeServiceService } from 'src/app/service/employee-service.service';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css'],
})
export class EditProjectComponent implements OnInit {
  componentEditState: boolean = false;
  editForm: FormGroup = new FormGroup({});

  constructor(
    private toastr: ToastrService,
    private projectService: ProjectServiceService,
    private employeeService: EmployeeServiceService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    let projectNumber = +this.route.snapshot.params['number'];
    if (projectNumber) {
      this.componentEditState = true;
    }

    this.editForm = new FormGroup({
      projectNumber: new FormControl(null, [
        Validators.required,
        Validators.pattern('^[0-9]{4}$'),
      ]),
      projectName: new FormControl(null, [Validators.required]),
      customer: new FormControl(null, [Validators.required]),
      group: new FormControl(1, [Validators.required]),
      members: new FormControl(null, Validators.pattern(/^\d+((,\d+)+)?$/)),
      status: new FormControl('NEW', [Validators.required]),
      date1: new FormControl(null, [Validators.required]),
      date2: new FormControl(null),
    });

    if (this.componentEditState) {
      let urlProjectNumber: number = +this.route.snapshot.params['number'];

      this.editForm.get('projectNumber')?.setValue(urlProjectNumber);
      this.editForm.get('projectNumber')?.disable();

      let editProject = this.projectService
        .getProjectByProjectNumber(urlProjectNumber)
        .subscribe((project) => {
          this.editForm.get('projectName')?.setValue(project.name);
          this.editForm.get('customer')?.setValue(project.customer);
          this.editForm.get('group')?.setValue(project.groupId);
          this.editForm.get('status')?.setValue(project.status);
          this.editForm.get('date1')?.setValue(project.startDate);
          this.editForm.get('date2')?.setValue(project.endDate);

          let memberIdArray: number[] = [];
          project.employees.forEach((employee) => {
            memberIdArray.push(employee.id);
          });
          this.editForm.get('members')?.setValue(memberIdArray.toString());
        });
    }
  }

  validateDate() {
    let valueDate1 = this.editForm.get('date1')?.value;
    let valueDate2 = this.editForm.get('date2')?.value;

    if (valueDate1 != null && valueDate2 != null) {
      let date1 = new Date(valueDate1);
      let date2 = new Date(valueDate2);
      if (date2 < date1) {
        return false;
      }
    }
    return true;
  }

  async onSubmit() {
    if (!this.editForm.valid) {
      this.toastr.warning(
        'Please enter all required fields',
        'Fields Requirement'
      );
      return;
    }
    if (!this.validateDate()) {
      this.toastr.error(
        'Finish Date must be before Start Date',
        'Date invalid'
      );
      return;
    }

    let groupID: number = this.editForm.get('group')?.value * 1;
    let projectNumber: number = this.editForm.get('projectNumber')?.value * 1;
    let projectName: string = this.editForm.get('projectName')?.value;
    let customer: string = this.editForm.get('customer')?.value;
    let status: string = this.editForm.get('status')?.value;
    let startDate: Date = new Date(this.editForm.get('date1')?.value);

    let endDate: any = null;
    if (this.editForm.get('date2')?.value != null) {
      endDate = new Date(this.editForm.get('date2')?.value);
    }

    let employees: Employee[] = [];

    let empIds = this.editForm.get('members')?.value;
    if (empIds == null) {
      empIds = '';
    } else {
      empIds = empIds.trim();
    }
    if (empIds.length > 0) {
      try {
        let employeeResponse: Employee[] = await lastValueFrom(
          this.employeeService.getEmployeesById(empIds)
        );
        employeeResponse.forEach((e) => {
          employees.push(e);
        });
      } catch (error: any) {
        let errorObj = error.error.errors;

        for (let title in errorObj) {
          this.toastr.error(errorObj[title]);
        }

        return;
      }
    }

    let project = new Project(
      groupID,
      projectNumber,
      projectName,
      employees,
      customer,
      status,
      startDate,
      endDate
    );

    let serviceFunction: Observable<Object>;
    if (this.componentEditState) {
      serviceFunction = this.projectService.updateProject(project);
    } else {
      serviceFunction = this.projectService.saveProject(project);
    }

    serviceFunction.subscribe(
      (response: any) => {
        this.toastr.success('Project updated successfully');
      },
      (error: HttpErrorResponse) => {
        let errorObj = { ...error.error.errors };
        for (let title in errorObj) {
          this.toastr.error(errorObj[title], title);
        }
      }
    );
  }

  onCancel() {
    this.router.navigate(['/projects']);
  }
}
