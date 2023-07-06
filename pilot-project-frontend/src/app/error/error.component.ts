import {Component, Input} from '@angular/core';
import {error} from "@angular/compiler-cli/src/transformers/util";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent {
  title = "Error"
  constructor(private notify: HttpErrorResponse) {
  }
  // title = this.notify.error.error.message;
}
