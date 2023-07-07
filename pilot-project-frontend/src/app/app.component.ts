import {Component, OnInit} from '@angular/core';
import {Projects} from "./project/projects";
import {AppService} from "./app.service";
import {FormGroup, NgForm} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {Title} from "@angular/platform-browser";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'pilot-project-frontend';

  ngOnInit() {
    this.reactiveForm = new FormGroup({});
  }

  reactiveForm: FormGroup;
  public projects: Projects[];

  constructor(private projectsService: AppService, private titleService: Title, private router: Router) {
    this.projects = [];
    // this.titleService.setTitle($localize(this.title));
  }

  protected readonly onclick = onclick;

  onClickSubmit(value: any) {
    alert("Not allow")
  }

  onAddProject(createproject: NgForm) {
    this.projectsService.addProjects(createproject.value).subscribe(
      (response: Projects) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
