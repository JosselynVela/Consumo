import { Component } from '@angular/core';
import { from, Observable } from 'rxjs';
import { User } from '../shared/user.interface';
import {PeliService} from './../services/peli.service';



@Component({
  selector: 'app-verify-email',
  templateUrl: './verify-email.page.html',
  styleUrls: ['./verify-email.page.scss'],
})
export class VerifyEmailPage {

  user$: Observable<User> = this.authSvc.afauth.user;

  constructor(private authSvc: PeliService) { }

  
  async onSendEmail(): Promise<void> {
    try {
      await this.authSvc. sendVerificationEmail();
    } catch (error) {
      console.log('Error->', error);
    }
  }

  ngOnDestroy(): void {
    this.authSvc.logout();
  }

}
