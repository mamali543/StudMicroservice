import { HttpClientModule } from '@angular/common/http';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { StudentService } from './student.service';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';


import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';

import { AuthGuard } from './guards/securityGuards';

import { ProfListComponent } from './prof-list/prof-list.component';
import { AddModalComponent } from './add-modal/add-modal.component';
import { HomeComponent } from './home/home.component';
import { AssignModalComponent } from './assign-modal/assign-modal.component';
import { DeleteComponent } from './delete-modal/delete.component';


const routes: Routes = [

  { path: "professor/:id", component: ProfListComponent, canActivate : [AuthGuard], },
  { path: "delete/:id", component: DeleteComponent , canActivate : [AuthGuard],data : { roles: ['ADMIN']}  },
  { path: "assign/:id", component: AssignModalComponent, canActivate : [AuthGuard], data : { roles: ['ADMIN']} },
  { path: '', component: HomeComponent ,},
  { path: 'add', component: AddModalComponent, canActivate : [AuthGuard], data : { roles: ['ADMIN']}}

];

function kcFactory(kcService: KeycloakService)
{
  return ()=>{
    // initialiser keycloak avec les options suivantes:
    kcService.init({
      config : {
        realm: "students-realm",
        clientId: "student-client",
        url:"http://localhost:8080"
      },
      initOptions : {
        /*par rapport a l'authentication soit login-required(l'app commence par l 'authetication)
        (check sso) c'est a moi de proteger les routes */
        onLoad : "login-required",
        // checkLoginIframe: true
      }
    })
  }
}

@NgModule({
  declarations: [
    HomeComponent,
    ProfListComponent,
    AppComponent,
    AddModalComponent,
    AssignModalComponent,
    DeleteComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    KeycloakAngularModule
  ],
  exports: [RouterModule],
  providers: [StudentService, {provide: APP_INITIALIZER, deps : [KeycloakService], useFactory : kcFactory, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
