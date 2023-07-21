import {Component, OnInit, ViewChild} from '@angular/core';
import {NgbAlert} from '@ng-bootstrap/ng-bootstrap';
import {debounceTime, Subject} from 'rxjs';
import {ProjectService} from "../../../services/project.service";
import {GroupService} from "../../../services/group.service";
import {Group} from "../../../models/group.interface";
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {TranslateService} from "@ngx-translate/core";
import {HttpErrorResponse} from "@angular/common/http";
import {ErrorHandlingService} from "../../../services/error-handling.service";
import {Project, Status} from "../../../models/project.interface";
// TODO : date format
// TODO : debounce time when typing in the search box and members
// TODO : user group leader not group id
// TODO : add sort at the table each column
// TODO : disable delete check box if project status is not new
// TODO : remain selected check box when change page
@Component({
  selector: 'app-view-project',
  templateUrl: './view-project-form.component.html',
  styleUrls: ['./view-project-form.component.scss']
})
export class CreateProjectFormComponent implements OnInit {
  projectNumberError: string = '';
  projectNameError: string = '';
  customerError: string = '';
  groupError: string = '';
  membersError: string = '';
  statusError: string = '';
  startDateError: string = '';
  endDateError: string = '';
  selectedGroup: number = 0;
  selectedStatus: string = 'NEW';
  cardHeaderText: string = "New Project";
  buttonSubmitText: string = "Create Project";
  groups: Group[] = []; // Property to store the fetched groups
  project: null | Project = null; // Property to store the fetched project if in edit mode, otherwise null to indicate new project
  successMessage = '';
  // staticAlertClosed = false; // Property to store whether the static alert is closed or not
  @ViewChild('selfClosingAlert', {static: false}) selfClosingAlert!: NgbAlert;
  private _success = new Subject<string>();

  constructor(private route: ActivatedRoute, private errorHandling: ErrorHandlingService, public translate: TranslateService, private dialog: MatDialog, private router: Router, private projectService: ProjectService, private groupService: GroupService) {
  }

  ngOnInit(): void {
    // Retrieve the id parameter from the URL
    const id = this.route.snapshot.paramMap.get('projectId');
    if (id) {
      this.loadProject(parseInt(id));
      this.cardHeaderText = 'Edit Project information';
      this.buttonSubmitText = "Save Changes";
    }
    this.loadGroups();
    this._success.subscribe((message) => (this.successMessage = message));
    this._success.pipe(debounceTime(5000)).subscribe(() => {
      // If the alert is not closed, close it after 5 seconds
      if (this.selfClosingAlert) {
        this.selfClosingAlert.close();
      }
      // Reset the error to an empty string
      this.projectNumberError = '';
      this.projectNameError = '';
      this.customerError = '';
      this.groupError = '';
      this.membersError = '';
      this.statusError = '';
      this.startDateError = '';
      this.endDateError = '';
    });
  }

  loadProject(id: number): void {
    this.projectService.getProjectById(id).subscribe((project) => {
      // Assign the fetched project to the this.project
      this.project = project;
      this.selectedGroup = this.project?.groupId as number;
      this.selectedStatus = this.project?.status as string
      console.log('Project :', project);
    });
  }

  loadGroups(): void {
    this.groupService.getGroups().subscribe((data) => {
      this.groups = data; // Assign the fetched groups to the property
    }, (error) => {
      console.error('Error loading groups:', error);
    });
  }

  selectStatus(event: Event, status: string): void {
    event.preventDefault();
    this.selectedStatus = status;
  }

  selectGroup(event: Event, groupId: number): void {
    event.preventDefault();
    this.selectedGroup = groupId == 0 ? this.translate.instant('New') : groupId;
  }

  public createProject(event: Event) {
    event.preventDefault();
    // Retrieve the form input values
    const projectName = (document.getElementById('projectName') as HTMLInputElement).value;
    const customer = (document.getElementById('customer') as HTMLInputElement).value;
    const members = (document.getElementById('members') as HTMLInputElement).value;
    const startDate = (document.getElementById('startDate') as HTMLInputElement).value;
    const endDate = (document.getElementById('endDate') as HTMLInputElement).value;
    const projectNumber = (document.getElementById('projectNumber') as HTMLInputElement).value;

    if (!this.isValidNumber(projectNumber)) {
      this._success.next(this.translate.instant('Please enter all the mandatory fields'));
      this.projectNumberError = this.translate.instant('Please enter a valid project number');
      return;
    }

    //Check validation
    if (!projectName || !customer || this.selectedGroup == 0 || !startDate) {
      this._success.next(this.translate.instant('Please enter all the mandatory fields'));
      return; // At least one mandatory field is missing
    }

    // Create an object representing the project data
    const projectData = {
      number: parseInt(projectNumber, 10),
      name: projectName,
      customer: customer,
      groupId: this.selectedGroup,
      members: members ? members : null,
      status: this.selectedStatus as Status,
      startDate: startDate,
      endDate: endDate ? endDate : null,
    };

    console.log('Project data:', projectData);

    // Send the create project data to the server using the project service
    this.projectService.createProjects(projectData).subscribe((response) => {
      // Handle successful response from the server
      console.log('Project created successfully:', response);
      // Clear the form fields or perform any other necessary actions
      this.clearForm();
      this.router.navigate(['/view-projects']);
    }, (error: HttpErrorResponse) => {
      this._success.next(this.translate.instant('Error creating project'));
      let message = this.errorHandling.extractErrorMessage(error);
      //if message is project number already exists, it means the error is from the backend
      //check message is project number already exists
      if (message.toLowerCase().includes('project number already exists')) {
        this.projectNumberError = this.translate.instant("project number already exists")
      } else {
        console.log('message:', message);
      }
    });
  }

  public updateProject(event: Event) {
    event.preventDefault();
    //Check validation from this.project
    // Retrieve the form input values
    const name = (document.getElementById('projectName') as HTMLInputElement).value;
    const customer = (document.getElementById('customer') as HTMLInputElement).value;
    const members = (document.getElementById('members') as HTMLInputElement).value;
    const startDate = (document.getElementById('startDate') as HTMLInputElement).value;
    const endDate = (document.getElementById('endDate') as HTMLInputElement).value;

    //Check validation
    if (!name || !customer || this.selectedGroup == 0 || !startDate) {
      this._success.next(this.translate.instant('Please enter all the mandatories fields'));
      return; // At least one mandatory field is missing
    }

    // Create an object representing the new project data
    const projectData = {
      id: this.project?.id,
      number: this.project?.number,
      name: name,
      customer: customer,
      groupId: this.selectedGroup,
      members: members ? members : null,
      status: this.selectedStatus as Status,
      startDate: startDate,
      endDate: endDate ? endDate : null,
      version: this.project?.version
    };

    // Send the create project data to the server using the project service
    this.projectService.updateProject(projectData).subscribe((response) => {
      // Handle successful response from the server
      console.log('Project updated successfully:', response);
      // Clear the form fields or perform any other necessary actions
      this.clearForm();
      this.router.navigate(['/view-projects']);
    }, (error: HttpErrorResponse) => {
      // Handle error response from the server
      console.error('Error updating project:', error);
      // Display an error message or perform appropriate error handling
      this._success.next(this.translate.instant('Error updating project'));
      let message: string = error.error.message();
      console.log('message:', message);
    });
  }

  public cancelEdit() {
    // Navigate back to the project list screen with preserved query parameters
    this.router.navigate(['/view-projects']);
  }

  private clearForm(): void {
    // Clear the form fields
    (document.getElementById('projectName') as HTMLInputElement).value = '';
    (document.getElementById('customer') as HTMLInputElement).value = '';
    (document.getElementById('group') as HTMLSelectElement).value = '';
    (document.getElementById('members') as HTMLInputElement).value = '';
    (document.getElementById('status') as HTMLSelectElement).value = '';
    (document.getElementById('startDate') as HTMLInputElement).value = '';
    (document.getElementById('endDate') as HTMLInputElement).value = '';
    (document.getElementById('projectNumber') as HTMLInputElement).value = '';
  }

  private isValidNumber(input: string): boolean {
    const numericRegex = /^[0-9]+$/;
    return numericRegex.test(input);
  }
}
