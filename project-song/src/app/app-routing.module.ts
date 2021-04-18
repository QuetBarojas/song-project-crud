import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { FormComponent } from './song/form/form.component';
import { SongComponent } from './song/song.component';


const routes: Routes = [] = [
  {path: '', redirectTo: '/song', pathMatch: 'full'},
  {path: 'song', component: SongComponent},
  {path: 'song/form', component: FormComponent},
  {path: 'song/form/:id', component: FormComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes),FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
