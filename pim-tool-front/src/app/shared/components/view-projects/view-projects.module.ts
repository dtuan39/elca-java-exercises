import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewProjectsComponent } from './view-projects.component';
import { NgbDropdownModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [ViewProjectsComponent],
  imports: [
    CommonModule,
    NgbDropdownModule,
    FormsModule,
    NgbPaginationModule
  ],
  exports: [ViewProjectsComponent]
})
export class ViewProjectsModule { }
