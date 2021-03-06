import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from "@angular/common/http";

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AppRoutingModule } from './/app-routing.module';
import { WelcomeComponent } from './welcome/welcome.component';

import { UserService } from "./user.service";
import { RatdataService } from "./ratdata.service";
import { MapsComponent } from './maps/maps.component';

import { AuthInterceptor } from "./authinterceptor";
import { GraphComponent } from './graph/graph.component';
import { RatdatadisplayComponent } from './ratdatadisplay/ratdatadisplay.component';
import { RatdataentryComponent, NgbdModalContent } from './ratdataentry/ratdataentry.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AdminComponent } from './admin/admin.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    WelcomeComponent,
    MapsComponent,
    GraphComponent,
    RatdatadisplayComponent,
    RatdataentryComponent,
    NgbdModalContent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule.forRoot()
  ],
  providers: [UserService, RatdataService, {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent],
  entryComponents: [NgbdModalContent]
})
export class AppModule { }
