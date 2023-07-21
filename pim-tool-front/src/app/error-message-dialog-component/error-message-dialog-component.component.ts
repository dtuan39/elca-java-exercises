import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-error-message-dialog-component',
  styleUrls: ['./error-message-dialog-component.component.scss'],
  templateUrl: `./error-message-dialog-component.component.html`,
})
export class ErrorMessageDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ErrorMessageDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public errorMessage: string,
  ) {
  }
}
