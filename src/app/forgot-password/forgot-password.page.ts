import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PeliService } from './../services/peli.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.page.html',
  styleUrls: ['./forgot-password.page.scss'],
})
export class ForgotPasswordPage  {

  constructor(private authSvc: PeliService, private router: Router) { }


 async onResetPassword(email){
    try {
      await this.authSvc.resetPassword(email.value);
      this.router.navigate(['/login']);
    } catch (error) {
      console.log('Error->', error);
    }
  }
  }


