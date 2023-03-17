import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { StudentService } from './student.service';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { ProfListComponent } from './prof-list/prof-list.component';
import { HomeComponent } from './home/home.component';


const routes: Routes = [

  { path: "professor/:id", component: ProfListComponent },
  { path: '', component: HomeComponent }

];

@NgModule({
  declarations: [
    HomeComponent,
    ProfListComponent,
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule],
  providers: [StudentService],
  bootstrap: [AppComponent]
})
export class AppModule { }
