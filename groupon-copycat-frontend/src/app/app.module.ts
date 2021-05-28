import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {HomeComponent} from './components/home/home.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LoginBoxComponent} from './components/login-box/login-box.component';
import {ButtonsModule, MDBBootstrapModule, NavbarModule, WavesModule} from 'angular-bootstrap-md';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {AdminControlPanel} from './components/admin/admin-control-panel/admin-control-panel.component';
import {MatDialogModule} from '@angular/material/dialog';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {ErrorBoxComponent} from './components/error-box/error-box.component';
import {ParticlesModule} from 'ngx-particle';
import {ParticlesComponent} from './components/particles/particles.component';
import {GlobalService} from './services/global.service';
import {CompanyPageComponent} from './components/company/company-page/company-page.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatOptionModule} from '@angular/material/core';
import {MatSelectModule} from '@angular/material/select';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatButtonModule} from '@angular/material/button';
import {CouponAddUpdateDeleteComponent} from './components/company/coupon-add-update-delete/coupon-add-update-delete.component';
import {CustomerAddUpdateDeleteComponent} from './components/admin/customer-add-update-delete/customer-add-update-delete.component';
import {CompanyAddUpdateDeleteComponent} from './components/admin/company-add-update-delete/company-add-update-delete.component';
import {CustomerPageComponent} from './components/customer/customer-page/customer-page.component';
import {CustomerDetailsComponent} from './components/customer/customer-details/customer-details.component';
import {CompanyDetailsComponent} from './components/company/company-details/company-details.component';
import {MatTooltipModule} from '@angular/material/tooltip';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    LoginBoxComponent,
    NotFoundComponent,
    AdminControlPanel,
    ErrorBoxComponent,
    ParticlesComponent,
    CompanyPageComponent,
    CouponAddUpdateDeleteComponent,
    CustomerAddUpdateDeleteComponent,
    CompanyAddUpdateDeleteComponent,
    CustomerPageComponent,
    CustomerDetailsComponent,
    CompanyDetailsComponent,

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MDBBootstrapModule.forRoot(),
    NavbarModule,
    WavesModule,
    ButtonsModule,
    MatDialogModule,
    ReactiveFormsModule,
    FormsModule,
    NgxDatatableModule,
    BrowserModule,
    ParticlesModule,
    MatSidenavModule,
    MatOptionModule,
    MatSelectModule,
    MatCheckboxModule,
    MatButtonModule,
    MatTooltipModule,
  ],
  providers: [HttpClientModule, GlobalService],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor() {
  }


}
