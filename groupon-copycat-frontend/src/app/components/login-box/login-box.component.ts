import {LoginControllerService} from '../../services/login-controller.service';
import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GlobalService} from '../../services/global.service';


@Component({
  selector: 'app-login-box',
  templateUrl: './login-box.component.html',
  styleUrls: ['./login-box.component.css']
})

export class LoginBoxComponent implements OnInit {

  clientType: string;
  clientChosen = false;
  password: string;
  email: string;
  loginForm: FormGroup;
  success = null;
  error;

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<LoginBoxComponent>, private logMan: LoginControllerService,
              private glob: GlobalService) {
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      password: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }

  login() {
    this.password = this.loginForm.controls.password.value;
    this.email = this.loginForm.controls.email.value;
    this.logMan.login(this.clientType, this.password, this.email).subscribe(
      s => {
        sessionStorage.setItem('token', s.toString());
        this.success = true;
        this.glob.setClientType(this.clientType);
        setTimeout(() => {
          this.closeDialog();
          this.glob.navigateClientHome()
        }, 1200);
        setTimeout(() => {
        //  force ngif conditions to update by reloading entire page
        location.reload();
        }, 1450);
      },
      e => {
        console.log(e);
        this.success = false;
        this.error = e.error;
        this.tryAgain();
      }
    );
  }

  tryAgain() {
    setTimeout(() => {
      this.success = null;
    }, 2300);
  }

  setClientType(type: string) {
    this.clientType = type;
    this.clientChosen = true;
  }
}
