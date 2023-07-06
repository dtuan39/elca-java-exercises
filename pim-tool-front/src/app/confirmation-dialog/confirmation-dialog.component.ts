import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-confirmation-dialog', template: `
    <h2 mat-dialog-title>{{translate.instant('Confirm Delete')}}</h2>
    <mat-dialog-content>{{this.data}}</mat-dialog-content>
    <mat-dialog-actions align="end" class="dialog-buttons">
      <button mat-button mat-dialog-close class="cancel-button">{{translate.instant('Cancel')}}</button>
      <button mat-button [mat-dialog-close]="'confirm'" class="delete-button">{{translate.instant('Delete')}}</button>
    </mat-dialog-actions>
  `, styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent {
  constructor(public dialogRef: MatDialogRef<ConfirmationDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: string,
              public translate : TranslateService
  ) {
  }
}
