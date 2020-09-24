import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PeliService } from '../services/peli.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage {

  constructor(private authSvc: PeliService, private router:Router) { }
  
  
  async onLogin(email,password){
    try {
      const user = await this.authSvc.login(email.value, password.value);
    
      if (user ) {
        const isVerified = this.authSvc.isEmailVerified(user);
        this.redirectUser(isVerified);
        console.log('verified->', isVerified);
      }
    } catch (error) {
      console.log('Error->', error);
    }
  
  }
  async onLoginGoogle() {
    try {
      const user = await this.authSvc.loginGoogle();
      if (user) {
        const isVerified = this.authSvc.isEmailVerified(user);
        this.reedirectUser(isVerified);
        console.log('verified->',isVerified);

      }
    } catch (error) {
      console.log('Error->', error);
    }
  }
  private redirectUser(isVerified: boolean): void {
    if (isVerified) {
      this.router.navigate(['pelis']);
    } else {
      this.router.navigate(['verify-email']);
    }
  }
  private reedirectUser(isVerified: boolean): void {
    if (isVerified) {
      this.router.navigate(['admin']);
    } else {
      this.router.navigate(['verify-email']);
    }
  }




}