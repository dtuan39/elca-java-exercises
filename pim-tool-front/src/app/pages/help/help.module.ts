import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HelpComponent } from './help.component';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [HelpComponent],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [HelpComponent]
})
export class HelpModule { }
