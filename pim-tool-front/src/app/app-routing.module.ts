import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddProjectComponent } from './component/add-project/add-project.component';
import { ListProjectComponent } from './component/list-project/list-project.component';
import { UpdateProjectComponent } from './component/update-project/update-project.component';

const routes: Routes = [
  { path: '', component: ListProjectComponent },
  { path: 'add', component: AddProjectComponent },
  { path: 'list', component: ListProjectComponent },
  { path: 'update/:projectNumber', component: UpdateProjectComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
