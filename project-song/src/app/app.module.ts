import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SongComponent } from './song/song.component';
import { FormComponent } from './song/form/form.component';
import { Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


const routes: Routes = [] = [
  {path: '', redirectTo: '/song', pathMatch: 'full'},
  {path: 'song', component: SongComponent},
  {path: 'song/form', component: FormComponent},
  {path: 'song/form/:id', component: FormComponent}
];
@NgModule({
  declarations: [
    AppComponent,
    SongComponent,
    FormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
