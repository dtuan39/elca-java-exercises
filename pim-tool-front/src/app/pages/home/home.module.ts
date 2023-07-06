import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ViewProjectFormModule } from 'src/app/shared/components/view-project/view-project-form.module';



@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    RouterModule,
    ViewProjectFormModule,
  ],
  exports: [HomeComponent]
})
export class HomeModule { }
