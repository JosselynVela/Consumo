import { from } from 'rxjs';
import {PeliService} from './../services/peli.service';
import {Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {

  constructor(private authSvc:PeliService,private router:Router) { }

  ngOnInit() {
  }

  async onRegister(email,password){
    try {
      const user = await this.authSvc.register(email.value, password.value);
      if (user) {
        const isVerified = this.authSvc.isEmailVerified(user);
        this.redirectUser(isVerified);
        console.log('User->', user);
      }
    } catch (error) {
      console.log('Error', error);
    }
    console.log('Email',email);
    console.log('Pass',password);

  }
  private redirectUser(isVerified: boolean): void {
    if (isVerified) {
      this.router.navigate(['admin']);
    } else {
      this.router.navigate(['verify-email']);
    }
  }

}
