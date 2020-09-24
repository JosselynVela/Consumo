import { Component, OnInit } from '@angular/core';
import { PeliService } from 'src/app/services/peli.service';
import {IPelis} from '../model/iPelis.interface';
import { TaskI } from '../model/task.interface';
import { TodoService } from '../services/todo.service';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.page.html',
  styleUrls: ['./admin.page.scss'],
})
export class AdminPage implements OnInit {
  todos: TaskI[];

  constructor(public authservice:PeliService, private adminService:TodoService) { }

  ngOnInit() {
    this.adminService.getTodos().subscribe(res=>{
      console.log('Teee', res);
      this.todos=res;});

  }

}
