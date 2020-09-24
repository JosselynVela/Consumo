import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { from, Observable,of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IPelis } from '../model/IPelis.interface';
import { User } from '../shared/user.interface';
import { AngularFireAuth } from '@angular/fire/auth';
import { switchMap } from 'rxjs/operators';
import * as firebase from 'firebase';
import { AngularFirestore, AngularFirestoreDocument } from '@angular/fire/firestore';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})

@Injectable({
  providedIn: 'root'
})
export class PeliService {
  private url: string = '';
  private apiKey: string = '26726106';
  
  public user$: Observable<User>;

  constructor(private http: HttpClient,public afauth:AngularFireAuth,private afs:AngularFirestore, private router:Router ) { 
    this.user$ = this.afauth.authState.pipe(
      switchMap((user) => {
        if (user) {
          return this.afs.doc<User>(`users/${user.uid}`).valueChanges();
        }
        return of(null);
      })
    );
  }
  

  searchMovies(title: string, type: string) {
    this.url = `https://www.omdbapi.com/?s=${encodeURI(title)}&type=${type}&apikey=${this.apiKey}`;
    console.log(this.url);
    return this.http.get<IPelis>(this.url).pipe(map(results => results['Search']));
  }

  getDetails(id: string) {
    return this.http.get<IPelis>(`https://www.omdbapi.com/?i=${id}&plot=full&apikey=${this.apiKey}`);
  }

  
  async resetPassword(email: string):Promise<void>{
    try {
      return this.afauth.sendPasswordResetEmail(email);
    } catch (error) {
      console.log('Error->', error);
    }
  }
  async loginGoogle():Promise<User>{
    try {
      const { user } = await this.afauth.signInWithPopup(new firebase.auth.GoogleAuthProvider());
      this.updateUserData(user);
      return user;
    } catch (error) {
      console.log('Error->', error);
    }
  }
  async register(email: string, password: string):Promise<User>{
    try {
      const { user } = await this.afauth.createUserWithEmailAndPassword(email, password);
      await this.sendVerificationEmail;
      return user;
    } catch (error) {
      console.log('Error->', error);
    }
  }
  async login(email: string, password: string): Promise<User> {
    try {

      const { user } = await this.afauth.signInWithEmailAndPassword(email, password);
      this.updateUserData(user);
      return user;
    } catch (error) {
      console.log('Error->', error);
    }
  }

  async sendVerificationEmail():Promise<void>{
    try {
      return (await this.afauth.currentUser).sendEmailVerification();
    } catch (error) {
      console.log('Error->', error);
    }
  }
  isEmailVerified(user:User): boolean{
    return user.emailVerified === true ? true : false; 
  }

  
  async logout():Promise<void>{
    try {
      await this.afauth.signOut();
    } catch (error) {
      console.log('Error->', error);
    }
  }
  private updateUserData(user: User) {
    const userRef: AngularFirestoreDocument<User> = this.afs.doc(`users/${user.uid}`);

    const data: User = {
      uid: user.uid,
      email: user.email,
      emailVerified: user.emailVerified,
      displayName: user.displayName,
    };

    return userRef.set(data, { merge: true });
  }

}
